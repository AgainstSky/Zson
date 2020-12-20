package com.zyinux.zson.parser;

import com.zyinux.zson.log.Log;
import com.zyinux.zson.parser.json.JsonParser;
import com.zyinux.zson.parser.json.SimpleJsonParser;
import com.zyinux.zson.parser.token.MapTokenTypeParser;
import com.zyinux.zson.parser.token.TokenTypeParser;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.reader.StringJsonReader;
import com.zyinux.zson.token.TokenFormat;
import com.zyinux.zson.token.TokenType;

import java.util.*;

/**
 * ParserHashMap
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public class ParserHashMap {

    private Stack<Character> keyStack = new Stack<Character>();

    JsonParser jsonParser=new SimpleJsonParser();

    TokenTypeParser<Map<String,Object>> tokenParser= new MapTokenTypeParser();

    public Map<String, Object> parser(String json) {
        TokenType tokenType = jsonParser.parser(json);
        TokenFormat format=new TokenFormat();


        String fs = format.format(tokenType);
        Log.debug(fs);

        return tokenParser.parserTokenType(tokenType);
    }


    private Queue<TokenType> parserJsonToTokenTypeList(String json) {
        Queue<TokenType> queue = new ArrayDeque<>();
        JsonReader jsonReader = new StringJsonReader(json);

        StringBuffer sb = new StringBuffer();


        return queue;
    }


}
