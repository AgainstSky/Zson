package com.zyinux.zson.parser.token;

import com.sun.istack.internal.NotNull;
import com.zyinux.zson.MagicConstant;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenTarget;
import com.zyinux.zson.utils.ListUtils;

import java.lang.reflect.Type;
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
public class MapTokenTypeParser extends AbstractTokenTargetParser<Map<String, Object>> {


    @Override
    public Map<String, Object> parserTokenType(TokenTarget tokenObject) {

        if (tokenObject.getToken() != Token.TOKEN_OBJECT) {
            throw new ZsonException("解析错误，预期是一个JsonObject,然而得到的是一个:" + tokenObject.getToken());
        }
        return parserTokenObject(tokenObject);
    }

    @Override
    protected void parserTokenObjectFiled(Type type,Object targetObject, TokenTarget key, TokenTarget value) {
        Map<String,Object> map= (Map<String, Object>) targetObject;
        map.put(key.getContent().toString(), parserTokenValue(value));
    }

    @Override
    protected Map<String, Object> generatorTargetObjectFactory(int s, Type type) {
        return new HashMap<>(s);
    }

    /**
     * 解析K:V 中的 V
     *
     * @param v 这里不能为空,不考虑做空指针判断
     * @return
     */
    private Object parserTokenValue(@NotNull TokenTarget v) {

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
    private Object parserTokenArray(@NotNull TokenTarget array) {
        List<TokenTarget> childTokens = array.getChildToken();
        if (ListUtils.isEmpty(childTokens)) {
            return null;
        }

        int S = childTokens.size();
        if ((S + 1) % 2 != 0) {
            throw new ZsonException("JsonArray解析失败，" + array);
        }
        List<Object> result = new ArrayList<>(childTokens.size());
        TokenTarget v = null, comma = null;
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
    private void checkArrayItemCorrect(TokenTarget v, TokenTarget comma, boolean lastLoop) {
        //不是最后一个循环的时候不能为空，且必须是 Token.TOKEN_SEP_COMMA
        if (!lastLoop && comma == null) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是不存在");
        }
        if (comma != null && comma.getToken() != Token.TOKEN_SEP_COMMA) {
            throw new ZsonException("解析错误，预期有个 `  ,   `，但是存在一个:" + comma);
        }
    }
}
