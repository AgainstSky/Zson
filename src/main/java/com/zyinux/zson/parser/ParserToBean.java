package com.zyinux.zson.parser;

import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.parser.json.JsonParser;
import com.zyinux.zson.parser.json.SimpleJsonParser;
import com.zyinux.zson.parser.token.BeanTokenTypeParser;
import com.zyinux.zson.parser.token.TokenTypeParser;
import com.zyinux.zson.token.TokenTarget;

import java.lang.reflect.Type;

/**
 * ParserToBean
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public class ParserToBean<T> implements Parser<T>{


    Type type;

    TokenTypeParser<T> tokenTypeParser;

    public ParserToBean(Type type) {
        this.type = type;
        tokenTypeParser=new BeanTokenTypeParser<>(type);
    }

    @Override
    public T parser(String json) {
        if (json==null){
            throw new ZsonException("解析的Json为空");
        }

        JsonParser jsonParser=new SimpleJsonParser();
        TokenTarget tokenObject = jsonParser.parser(json);

        T t = tokenTypeParser.parserTokenType(tokenObject);
        return t;
    }
}
