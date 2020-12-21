package com.zyinux.zson.parser.token;

import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.log.Log;
import com.zyinux.zson.ref.ClassType;
import com.zyinux.zson.token.TokenTarget;
import com.zyinux.zson.utils.NormalTypeCheck;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanTokenTypeParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public class BeanTokenTypeParser<T> extends AbstractTokenTargetParser<T> {

    Type outType;

    ClassType classType = new ClassType();

    Map<Type, Map<String, Method>> typeMethodCache = new HashMap<>();

    public BeanTokenTypeParser(Type type) {
        this.outType = type;
    }


    @Override
    public T parserTokenType(TokenTarget tokenObject) {
        return parserTokenObject(tokenObject, outType);

    }

    private Map<String, Method> getPublicSetMethodsMap(Type type) {
        Map<String, Method> publicSetMethodsMap = null;
        String className = type.getTypeName();
        Log.debug(className);
        try {
            Class<?> targetClass = Class.forName(className);
            publicSetMethodsMap = classType.getPublicSetMethodsMap(targetClass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ZsonException("解析错误，无法找到对应的JavaBean:" + className +
                    ",请检查是否存在该类，且是public的");
        }
        return publicSetMethodsMap;
    }


    @Override
    protected void parserTokenObjectFiled(Type type, Object targetObject, TokenTarget key, TokenTarget value) {
        Map<String, Method> methods = getTypePublicSetMethodsInCache(type);
        //如果这个JavaBean不含有任何的setter方法，就直接返回不做解析
        if (methods.isEmpty()) {
            return;
        }

        String keyName = buildTheMethodNameKeyByTokenTargetContent(key.getContent().toString());
        if (methods.containsKey(keyName)) {
            Method method = methods.get(keyName);
            try {
                method.invoke(targetObject, parserTokenTargetForValue(value, method.getParameters()[0].getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Object parserTokenTargetForValue(TokenTarget value, Class<?> type) {
        switch (value.getToken()) {
            case TOKEN_OBJECT:
                return parserTokenObject(value, type);
            case TOKEN_STRING:
                return value.getContent();
            case TOKEN_BOOL:
                return parserTokenBoolean(value, type);
            case TOKEN_ARRAY:
                return null;
            case TOKEN_NUMBER:
                return parserTokenNumber(value, type);
            case TOKEN_NULL:
                return parserTokenNull(value,type);
            default:
                return null;
        }
    }

    private Object parserTokenNull(TokenTarget value, Class<?> type) {
        String name = type.getName();

        //基本类型的时候需要做默认值，其他情况下，直接null返回
        if (NormalTypeCheck.isInt(name)) {
            return 0;
        }
        if (NormalTypeCheck.isFloat(name)) {
            return 0.0f;
        }
        if (NormalTypeCheck.isDouble(name)) {
            return 0.0;
        }
        if (NormalTypeCheck.isBigDecimal(name)) {
            return new BigDecimal(0);
        }
        if (NormalTypeCheck.isShort(name)) {
            return (short) 0;
        }
        if (NormalTypeCheck.isLong(name)) {
            return (long) 0;
        }

        return null;
    }

    private Object parserTokenNumber(TokenTarget value, Class<?> type) {
        String name = type.getName();
        Object content = value.getContent();

        if (content == null) {
            throw new ZsonException("解析错误，预期数字中不应该存在null值," + value);
        }

        String contentStr = content.toString();
        if (NormalTypeCheck.isString(name)) {
            return content;
        }
        if (NormalTypeCheck.isInt(name)) {
            return Integer.valueOf(contentStr);
        }
        if (NormalTypeCheck.isFloat(name)) {
            return Float.valueOf(contentStr);
        }
        if (NormalTypeCheck.isDouble(name)) {
            return Double.valueOf(contentStr);
        }
        if (NormalTypeCheck.isBigDecimal(name)) {
            return new BigDecimal(contentStr);
        }
        if (NormalTypeCheck.isShort(name)) {
            return Short.valueOf(contentStr);
        }
        if (NormalTypeCheck.isLong(name)) {
            return Long.valueOf(contentStr);
        }
        throw new ZsonException("不支持的解析类型,type:" + type+" tokenTarget:"+value);
    }

    private Object parserTokenBoolean(TokenTarget value, Class<?> type) {
        String name = type.getName();
        Object content = value.getContent();
        if (content == null) {
            throw new ZsonException("解析错误，预期Boolean中不应该存在null值," + value);
        }
        if (NormalTypeCheck.isString(name)){
            return String.valueOf(content);
        }
        if (NormalTypeCheck.isBoolean(name)){
            return content;
        }
        //todo::可以考虑加一个int ，float等类型来接受bool值的功能
        throw new ZsonException("不支持的解析类型,type:" + type+" tokenTarget:"+value);
    }

    /**
     * 通过TokenTarget 里的 键 的内容 获得他对应的Setter方法的名字
     *
     * @param content
     * @return
     */
    private String buildTheMethodNameKeyByTokenTargetContent(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        String first = content.substring(0, 1).toUpperCase();
        return "set" + first + content.substring(1, content.length());
    }

    /**
     * 从缓存里拿类的public的set方法信息
     *
     * @return
     */
    private Map<String, Method> getTypePublicSetMethodsInCache(Type type) {
        Map<String, Method> methods;
        if (typeMethodCache.containsKey(type)) {
            methods = typeMethodCache.get(type);
        } else {
            methods = getPublicSetMethodsMap(type);
            typeMethodCache.put(type, methods);
        }
        return methods;
    }

    @Override
    protected Object generatorTargetObjectFactory(int s, Type type) {
        Object target = null;
        try {
            String name = ((Class) type).getName();
            target = Class.forName(name).newInstance();
        } catch (Exception e) {
            throw new ZsonException("解析错误，无法访问JavaBean:" + type.getClass().getName());
        }
        return target;
    }
}
