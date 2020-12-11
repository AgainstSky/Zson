package com.zyinux.zson.parser;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.reader.StringJsonReader;
import com.zyinux.zson.token.KeyCheck;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenFormat;
import com.zyinux.zson.token.TokenType;
import com.zyinux.zson.token.helper.TokenHelper;

import java.io.CharArrayReader;
import java.io.StringReader;
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

    public Map<String, Object> parser(String json) {
        Map<String, Object> result = new HashMap<String, Object>();

//        Queue<TokenType> queue = parserJsonToTokenTypeList(json);
//        System.out.println(queue);

        TokenType tokenType = parserJsonToTokenObject(json);
        System.out.println(new TokenFormat().formatForDebug(tokenType));
        return result;
    }

    private Queue<TokenType> parserJsonToTokenTypeList(String json) {
        Queue<TokenType> queue = new ArrayDeque<>();
        JsonReader jsonReader = new StringJsonReader(json);

        StringBuffer sb = new StringBuffer();


        return queue;
    }

    private TokenType parserJsonToTokenObject(String json) {
        JsonReader jsonReader = new StringJsonReader(json);
        jsonReader.next();
        return TokenHelper.parseForTokenObject(jsonReader);
    }
}
