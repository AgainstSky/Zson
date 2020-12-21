package com.zyinux.zson.parser.token;

import com.zyinux.zson.MagicConstant;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenTarget;
import com.zyinux.zson.utils.ListUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractTokenTargetParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
public abstract class AbstractTokenTargetParser<T> implements TokenTypeParser<T> {


    protected T parserTokenObject(TokenTarget tokenObject) {
        return parserTokenObject(tokenObject, null);
    }

    /**
     * 解析一个token对象
     *
     * @param tokenObject
     * @return
     */
    protected T parserTokenObject(TokenTarget tokenObject, Type type) {

        List<TokenTarget> childToken = tokenObject.getChildToken();
        if (ListUtils.isEmpty(childToken)) {
            return null;
        }
        //按照设计这里 S(size) = 4n - 1 其中n是键值对的数量，则可得 (S+1) % 4 = 0;
        int S = childToken.size();
        if ((S + 1) % MagicConstant.OBJECT_KEY_VALUE_SIZE != 0) {
            throw new ZsonException("错误的键值对数量，" + tokenObject);
        }
        //所以这里可以求出来共有几个键值对，可以直接得出map的大小
        Object result = generatorTargetObjectFactory((S + 1) / MagicConstant.OBJECT_KEY_VALUE_SIZE,type );

        TokenTarget k = null, colon = null, v = null, comma = null;
        int endConditions = S - (MagicConstant.OBJECT_KEY_VALUE_SIZE - 1);
        for (int i = 0; i <= endConditions; i += MagicConstant.OBJECT_KEY_VALUE_SIZE) {


            k = childToken.get(i);
            colon = childToken.get(i + 1);
            v = childToken.get(i + 2);

            boolean isLastLoop = i == endConditions;
            if (!isLastLoop) {
                comma = childToken.get(i + 3);
            }

            //检查这一个键值对是否合规正确
            checkObjectFieldCorrect(k, colon, v, comma, isLastLoop);

            parserTokenObjectFiled(type,result, k, v);

            //置空防止数据污染下次循环
            k = null;
            colon = null;
            v = null;
            comma = null;
        }


        return (T) result;
    }

    /**
     * 子类去做具体的操作
     *
     * @param targetObject
     * @param key
     * @param value
     */
    protected abstract void parserTokenObjectFiled(Type type,Object targetObject, TokenTarget key, TokenTarget value);

    /**
     * 工厂方法 让子类去处理生成的对象
     *
     * @param s 键值对的数量
     * @return
     */
    protected abstract Object generatorTargetObjectFactory(int s, Type type);

    /**
     * 检查JsonObject里的键值对状态是否正确
     *
     * @param k        key 必须为String类型
     * @param colon    必须是 Token.TOKEN_SEP_COLON 键值对分隔符罢了
     * @param v        value 键值对中的值
     * @param comma
     * @param lastLoop
     */
    protected void checkObjectFieldCorrect(TokenTarget k, TokenTarget colon, TokenTarget v, TokenTarget comma, boolean lastLoop) {
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
