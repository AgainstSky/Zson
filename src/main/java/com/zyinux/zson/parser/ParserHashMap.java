package com.zyinux.zson.parser;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.reader.StringJsonReader;
import com.zyinux.zson.token.KeyCheck;
import com.zyinux.zson.token.Token;
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

        Queue<TokenType> queue = parserJsonToTokenTypeList(json);
        System.out.println(queue);
        return result;
    }

    private Queue<TokenType> parserJsonToTokenTypeList(String json) {
        Queue<TokenType> queue = new ArrayDeque<>();
        JsonReader jsonReader = new StringJsonReader(json);

        StringBuffer sb = new StringBuffer();
        while (jsonReader.hasNext()) {
            char next = jsonReader.next();

            if (KeyCheck.isContinueKey(next)) {
                continue;
            }

            switch (next) {
                case CharKey.KEY_OBJECT_BEGIN:
                    queue.add(new TokenType(Token.TOKEN_OBJECT_BEGIN));
                    break;
                case CharKey.KEY_OBJECT_END:
                    queue.add(new TokenType(Token.TOKEN_OBJECT_END));
                    break;
                case CharKey.KEY_ARRAY_BEGIN:
                    queue.add(new TokenType(Token.TOKEN_ARRAY_BEGIN));
                    break;
                case CharKey.KEY_ARRAY_END:
                    queue.add(new TokenType(Token.TOKEN_ARRAY_END));
                    break;
                case CharKey.KEY_ESCAPE:
                    queue.add(new TokenType(Token.TOKEN_ESCAPE));
                    break;
                case CharKey.KEY_SEP_COMMA:
                    queue.add(new TokenType(Token.TOKEN_SEP_COMMA));
                    break;
                case CharKey.KEY_SEP_COLON:
                    queue.add(new TokenType(Token.TOKEN_SEP_COLON));
                    queue.add(TokenHelper.getTheTokenMaybeValue(jsonReader));
                    break;
                case CharKey.KEY_KEY:
                    queue.add(new TokenType(Token.TOKEN_STRING, TokenHelper.findTheTokenStringData(jsonReader)));
                    break;
                default:
                    break;
            }

        }

        return queue;
    }
}
