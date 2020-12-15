package com.zyinux.zson.token.helper;

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
public class TokenTypeParser {

    public Map<String, Object> parserTokenTypeObjectToMap(TokenType tokenType) {
        Map<String, Object> result = new HashMap<>();

        TokenType current=tokenType;
        while (current!=null){
            if (tokenType.getToken()== Token.TOKEN_STRING){
                
            }
        }


        return result;
    }
}
