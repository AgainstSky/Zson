package com.zyinux.zson.parser;

import com.zyinux.zson.log.Log;
import com.zyinux.zson.parser.json.JsonParser;
import com.zyinux.zson.parser.json.SimpleJsonParser;
import com.zyinux.zson.parser.token.MapTokenTypeParser;
import com.zyinux.zson.parser.token.TokenTypeParser;
import com.zyinux.zson.token.TokenFormat;
import com.zyinux.zson.token.TokenTarget;

import java.util.*;

/**
 * ParserHashMap
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public class ParserHashMap {

    JsonParser jsonParser=new SimpleJsonParser();

    TokenTypeParser<Map<String,Object>> tokenParser= new MapTokenTypeParser();

    public Map<String, Object> parser(String json) {
        TokenTarget tokenTarget = jsonParser.parser(json);
        TokenFormat format=new TokenFormat();


        String fs = format.format(tokenTarget);
        Log.debug(fs);

        return tokenParser.parserTokenType(tokenTarget);
    }
}
