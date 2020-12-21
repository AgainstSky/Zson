package com.zyinux.zson;

import com.zyinux.zson.parser.Parser;
import com.zyinux.zson.parser.ParserHashMap;
import com.zyinux.zson.parser.ParserToBean;

import java.util.Map;

/**
 * Zson
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public class Zson {

    public Map<String, Object> parserJson(String json) {
        ParserHashMap parser = new ParserHashMap();

        return parser.parser(json);
    }

    public <T> T parserJson(String json, Class<T> classOfT) {
        Parser<T> parser=new ParserToBean<>(classOfT);

        return parser.parser(json);
    }

}
