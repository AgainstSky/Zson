package com.zyinux.zson.parser.token;

import com.sun.istack.internal.NotNull;
import com.zyinux.zson.MagicConstant;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenType;
import com.zyinux.zson.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TokenTypeParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/14
 */
public class MapTokenTypeParser implements TokenTypeParser<Map<String, Object>> {


    @Override
    public Map<String, Object> parserTokenType(TokenType tokenObject) {

        if (tokenObject.getToken() != Token.TOKEN_OBJECT) {
            throw new ZsonException("解析错误，预期是一个JsonObject,然而得到的是一个:" + tokenObject.getToken());
        }
        return parserTokenObject(tokenObject);
    }


    /**
     * 解析一个token对象
     *
     * @param tokenObject
     * @return
     */
    private Map<String, Object> parserTokenObject(TokenType tokenObject) {

        List<TokenType> childToken = tokenObject.getChildToken();
        if (ListUtils.isEmpty(childToken)) {
            return null;
        }
        //按照设计这里 S(size) = 4n - 1 其中n是键值对的数量，则可得 (S+1) % 4 = 0;
        int S = childToken.size();
        if ((S + 1) % MagicConstant.OBJECT_KEY_VALUE_SIZE != 0) {
            throw new ZsonException("错误的键值对数量，" + tokenObject);
        }
        //所以这里可以求出来共有几个键值对，可以直接得出map的大小
        Map<String, Object> result = new HashMap<>((S + 1) / MagicConstant.OBJECT_KEY_VALUE_SIZE);

        TokenType k = null, colon = null, v = null, comma = null;
        int endConditions = S - (MagicConstant.OBJECT_KEY_VALUE_SIZE - 1);
        for (int i = 0; i <= endConditions; i += MagicConstant.OBJECT_KEY_VALUE_SIZE) {


            k = childToken.get(i);
            colon = childToken.get(i + 1);
            v = childToken.get(i + 2);

            boolean isLastLoop = i == endConditions;
            if (!isLastLoop) {
                comma = childToken.get(i + 3);
            }
            checkObjectFieldCorrect(k, colon, v, comma, isLastLoop);

            result.put(k.getContent().toString(), parserTokenValue(v));

            //置空防止数据污染下次循环
            k = null;
            colon = null;
            v = null;
            comma = null;
        }


        return result;
    }

    /**
     * 解析K:V 中的 V
     *
     * @param v 这里不能为空,不考虑做空指针判断
     * @return
     */
    private Object parserTokenValue(@NotNull TokenType v) {

        switch (v.getToken()) {
            case TOKEN_OBJECT:
                return parserTokenObject(v);
            case TOKEN_STRING:
            case TOKEN_BOOL:
            case TOKEN_NUMBER:
            case TOKEN_NULL:
                return v.getContent();
            case TOKEN_ARRAY:
                return parserTokenArray(v);
            default:
                break;
        }

        throw new ZsonException("解析错误，未知的Json:" + v);
    }

    /**
     * 解析一个数组对象
     *
     * @param array
     * @return
     */
    private Object parserTokenArray(@NotNull TokenType array) {
        List<TokenType> childTokens = array.getChildToken();
        if (ListUtils.isEmpty(childTokens)) {
            return null;
        }

        int S = childTokens.size();
        if ((S + 1) % 2 != 0) {
            throw new ZsonException("JsonArray解析失败，" + array);
        }
        List<Object> result = new ArrayList<>(childTokens.size());
        TokenType v = null, comma = null;
        int endConditions = S - (MagicConstant.ARRAY_VALUE_SIZE - 1);

        //步进为2的循环
        for (int i = 0; i <= S - 1; i += 2) {

            boolean isLastLoop = i == endConditions;
            v = childTokens.get(i);
            if (!isLastLoop) {
                //不是最后一次时，comma都不会为空
                comma = childTokens.get(i + 1);
            }
            checkArrayItemCorrect(v, comma, isLastLoop);
            result.add(parserTokenValue(v));

            //置空防止数据污染
            v = null;
            comma = null;
        }
        return result;
    }

    /**
     * 检查JsonArray
     *
     * @param v
     * @param comma
     * @param lastLoop
     */
    private void checkArrayItemCorrect(TokenType v, TokenType comma, boolean lastLoop) {
        //不是最后一个循环的时候不能为空，且必须是 Token.TOKEN_SEP_COMMA
        if (!lastLoop && comma == null) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是不存在");
        }
        if (comma != null && comma.getToken() != Token.TOKEN_SEP_COMMA) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是存在一个:" + comma);
        }
    }


    /**
     * 检查JsonObject里的键值对状态是否正确
     *
     * @param k        key 必须为String类型
     * @param colon    必须是 Token.TOKEN_SEP_COLON 键值对分隔符罢了
     * @param v        value 键值对中的值
     * @param comma
     * @param lastLoop
     */
    private void checkObjectFieldCorrect(TokenType k, TokenType colon, TokenType v, TokenType comma, boolean lastLoop) {
        //不是最后一个循环的时候不能为空，且必须是 Token.TOKEN_SEP_COMMA
        if (!lastLoop && comma == null) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是不存在");
        }
        if (comma != null && comma.getToken() != Token.TOKEN_SEP_COMMA) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是存在一个:" + comma);
        }
        if (k == null || colon == null || v == null) {
            throw new ZsonException("解析错误，键值对不完善");
        }
        if (k.getToken() != Token.TOKEN_STRING) {
            throw new ZsonException("解析错误，所有的Key必须都是String,但是此处存在:" + k);
        }
        if (colon.getToken() != Token.TOKEN_SEP_COLON) {
            throw new ZsonException("解析错误，预期有个 `  :  `，但是不存在");
        }

    }
}
