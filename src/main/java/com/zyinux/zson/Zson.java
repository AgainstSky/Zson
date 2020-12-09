package com.zyinux.zson;

import com.zyinux.zson.parser.ParserHashMap;

import java.util.Map;

/**
 * Zson
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public class Zson {

    public Map<String,Object> parserJson(String json){
        ParserHashMap parser=new ParserHashMap();

        return parser.parser(json);
    }

}
