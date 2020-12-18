package com.zyinux.zson.parser.token;

import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenType;

import java.util.HashMap;
import java.util.Map;

/**
 * TokenTypeParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/14
 */
public class MapTokenTypeParser implements TokenTypeParser<Map<String,Object>>{

    @Override
    public Map<String, Object> parserTokenType(TokenType tokenObject) {
        Map<String, Object> result = new HashMap<>();

        TokenType current=tokenObject;
        while (current!=null){
            if (current.getToken()== Token.TOKEN_STRING){

            }
        }


        return result;
    }
}
