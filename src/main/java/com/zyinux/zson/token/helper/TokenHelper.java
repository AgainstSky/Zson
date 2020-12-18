package com.zyinux.zson.token.helper;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.token.KeyCheck;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenType;

/**
 * TokenHelper
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class TokenHelper {

    /**
     * 预期解析出一个json对象
     *
     * @param jsonReader
     * @return
     */
    public static TokenType parseForTokenObject(JsonReader jsonReader) {

        TokenType tokenType = new TokenType(Token.TOKEN_OBJECT);
        boolean findNextKey = false;
        while (jsonReader.hasNext() && !findNextKey) {
            char next = jsonReader.next();

            if (KeyCheck.isContinueKey(next)) {
                continue;
            }
            switch (next) {
                case CharKey.KEY_OBJECT_BEGIN:
                    tokenType.add(parseForTokenObject(jsonReader));
                    break;
                case CharKey.KEY_OBJECT_END:
                    findNextKey = true;
                    break;
                case CharKey.KEY_ARRAY_BEGIN:
                    tokenType.add(parseForTokenArray(jsonReader));
                    break;
                case CharKey.KEY_ARRAY_END:
                    //理论上这个key应该在 parseForTokenArray 里被处理，不会在解析Object里出现
                    //tokenType.add(new TokenType(Token.TOKEN_ARRAY_END));
                    break;
                case CharKey.KEY_ESCAPE:
                    //理论上这个key应该在 findTheTokenStringData 里被处理，不会在解析Object里出现
                    //tokenType.add(new TokenType(Token.TOKEN_ESCAPE));
                    break;
                case CharKey.KEY_SEP_COMMA:
                    tokenType.add(new TokenType(Token.TOKEN_SEP_COMMA));
                    break;
                case CharKey.KEY_SEP_COLON:
                    tokenType.add(new TokenType(Token.TOKEN_SEP_COLON));
                    tokenType.add(TokenHelper.getTheTokenMaybeValue(jsonReader));
                    break;
                case CharKey.KEY_KEY:
                    tokenType.add(TokenHelper.findTheTokenStringData(jsonReader));
                    break;
                default:
                    break;
            }

        }
        if (!findNextKey){
            throw new ZsonException("解析错误，直到结束所有流，也没有遇到预期的 ` } ` 来结束这个json对象 ");
        }
        return tokenType;
    }

    /**
     * 解析一些数组的token
     * @param jsonReader
     * @return
     */
    private static TokenType parseForTokenArray(JsonReader jsonReader) {
        TokenType tokenType = new TokenType(Token.TOKEN_ARRAY);
        boolean findNextKey = false;

        while (jsonReader.hasNext() && !findNextKey) {
            char next = jsonReader.next();

            if (KeyCheck.isContinueKey(next)) {
                continue;
            }
            switch (next) {
                case CharKey.KEY_OBJECT_BEGIN:
                    tokenType.add(parseForTokenObject(jsonReader));
                    break;
                case CharKey.KEY_OBJECT_END:
                    break;
                case CharKey.KEY_ARRAY_BEGIN:
                    tokenType.add(parseForTokenArray(jsonReader));
                    break;
                case CharKey.KEY_ARRAY_END:
                    findNextKey = true;
                    break;
                case CharKey.KEY_ESCAPE:
                    //理论上这个key应该在 findTheTokenStringData 里被处理，不会在解析Object里出现
                    //tokenType.add(new TokenType(Token.TOKEN_ESCAPE));
                    break;
                case CharKey.KEY_SEP_COMMA:
                    tokenType.add(new TokenType(Token.TOKEN_SEP_COMMA));
                    tokenType.add(getTheTokenMaybeValue(jsonReader));
                    break;
                case CharKey.KEY_SEP_COLON:
                    //理论上来说数组里面无论如何都不能存在这个key，这个只能被包括在内部的object里，所以应该抛出异常
                    throw new ZsonException("解析错误，在Json数组里:"+jsonReader.position()+"处不应该存在  ` : ` ");
//                    tokenType.add(new TokenType(Token.TOKEN_SEP_COLON));
//                    tokenType.add(TokenHelper.getTheTokenMaybeValue(jsonReader));
//                    break;
                case CharKey.KEY_KEY:
                    tokenType.add(TokenHelper.findTheTokenStringData(jsonReader));
                    break;
                default:
                    jsonReader.backOne();
                    tokenType.add(getTheTokenMaybeValue(jsonReader));
                    break;
            }

        }
        if (!findNextKey){
            throw new ZsonException("解析错误，直到结束所有流，也没有遇到预期的 ` ] ` 来结束这个json对象 ");
        }
        return tokenType;
    }

    /**
     * 解析接下来的流，直到遇到下一个{@link CharKey.KEY_KEY}，中间所有的内容就是一个完整的TokenString
     *
     * @param jsonReader
     * @return
     */
    public static TokenType findTheTokenStringData(JsonReader jsonReader) {
        StringBuffer sb = new StringBuffer();
        char next = ' ';

        //一直读取到文件末尾或者读到下一个CharKey.KEY_KEY
        while (jsonReader.hasNext() && (next = jsonReader.next()) != CharKey.KEY_KEY) {
            sb.append(next);
            //这里需要考虑转义字符的问题，其他字符应该没有太过需要处理的，但是当下一个字符是' " '也就是CharKey.KEY_KEY的时候需要特别处理
            //转义字符正常情况下两个搭配在一起使用的，所以直接多拿下一个char存入sb里面。
            //当然如果下一个不存在了，则直接抛出异常
            if (KeyCheck.isEscape(next)) {
                if (!jsonReader.hasNext()) {
                    throw new ZsonException("解析错误，在第" + jsonReader.position() + "处预期不能结尾，但是json结束了");
                }
                sb.append(jsonReader.next());
            }

        }

        if (next != CharKey.KEY_KEY) {
            throw new ZsonException("解析错误，在第" + jsonReader.position() + "处预期有一个'" + CharKey.KEY_KEY + "'，但是不存在");
        }
        return new TokenType(Token.TOKEN_STRING, sb.toString());
    }

    /**
     * 获取下一个 token 类型，基本是在遇到{@link com.zyinux.zson.token.Token.TOKEN_SEP_COLON}之后调用
     * 所以大概率是 k:v 中的value
     * 但是也可能是下一个Object或者Array
     *
     * @param jsonReader
     * @return
     */
    public static TokenType getTheTokenMaybeValue(JsonReader jsonReader) {
        if (!jsonReader.hasNext()) {
            throw new ZsonException("解析错误，在第" + jsonReader.position() + "处预期不能结尾，但是json结束了");
        }

        while (jsonReader.hasNext()) {
            char next = jsonReader.next();
            if (KeyCheck.isContinueKey(next)) {
                continue;
            }

            if (next == CharKey.KEY_OBJECT_BEGIN) {
                return parseForTokenObject(jsonReader);
            } else if (next == CharKey.KEY_ARRAY_BEGIN) {
                return parseForTokenArray(jsonReader);
            } else if (next == CharKey.KEY_ARRAY_END){
                return new TokenType(Token.TOKEN_ARRAY_END);
            }if (next == CharKey.KEY_KEY) {
                return findTheTokenStringData(jsonReader);
            } else if (next == 'f' || next == 't') {
                return findTheTokenBooleanData(jsonReader, next);
            } else if (Character.isDigit(next) || next == '+' || next == '-') {
                //当字符是数字的情况下的解析
                return findTheTokenNumberData(jsonReader, next);
            } else if (next == 'n'){
                return findTheTokenNullData(jsonReader,next);
            }else {
                throw new ZsonException("解析错误，" + jsonReader.position() + "存在未知无法解析的字符： ` " + next+" `");
            }
        }
        throw new ZsonException("解析错误，未知错误在:" + jsonReader.position());
    }

    /**
     * 解析空值对象
     * @param jsonReader
     * @param prev
     * @return
     */
    private static TokenType findTheTokenNullData(JsonReader jsonReader, char prev) {
        StringBuilder sb = new StringBuilder();
        sb.append(prev);
        while (jsonReader.hasNext()) {
            char next = jsonReader.next();
            if (KeyCheck.isValueEndKey(next)) {
                break;
            }
            sb.append(next);
        }
        String value = sb.toString().trim();
        TokenType tokenType = new TokenType(Token.TOKEN_NULL);
        if (KeyCheck.isNullKey(value)) {
            tokenType.setContent("null");
        } else {
            throw new ZsonException("解析错误，在" + jsonReader.position() + "处理应是一个 null ，然而是：" + value);
        }

        // 回退一位
        jsonReader.backOne();
        return tokenType;
    }

    private static TokenType findTheTokenNumberData(JsonReader jsonReader, char prev) {
        StringBuilder sb = new StringBuilder();
        sb.append(prev);
        while (jsonReader.hasNext()) {
            char next = jsonReader.next();
            if (KeyCheck.isValueEndKey(next)) {
                break;
            }
//            if (!Character.isDigit(next)){
//
//            }
            sb.append(next);
        }
        String value = sb.toString().trim();
        TokenType tokenType = new TokenType(Token.TOKEN_NUMBER);
        tokenType.setContent(value);
        jsonReader.backOne();
        return tokenType;
    }

    private static TokenType findTheTokenBooleanData(JsonReader jsonReader, char prev) {

        StringBuilder sb = new StringBuilder();
        sb.append(prev);
        while (jsonReader.hasNext()) {
            char next = jsonReader.next();
            if (KeyCheck.isValueEndKey(next)) {
                break;
            }
            sb.append(next);
        }
        String value = sb.toString().trim();
        TokenType tokenType = new TokenType(Token.TOKEN_BOOL);
        if (KeyCheck.isFalseKey(value)) {
            tokenType.setContent(false);
        } else if (KeyCheck.isTrueKey(value)) {
            tokenType.setContent(true);
        } else {
            throw new ZsonException("解析错误，在" + jsonReader.position() + "处理应是一个boolean类型，然而是：" + value);
        }

        // 回退一位
        jsonReader.backOne();
        return tokenType;
    }
}
