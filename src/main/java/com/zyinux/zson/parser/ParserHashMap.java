package com.zyinux.zson.parser;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.reader.StringJsonReader;
import com.zyinux.zson.token.KeyCheck;
import com.zyinux.zson.token.Token;
import com.zyinux.zson.token.TokenFormat;
import com.zyinux.zson.token.TokenType;
import com.zyinux.zson.token.helper.TokenHelper;
import com.zyinux.zson.token.helper.TokenTypeParser;

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

        TokenTypeParser parser=new TokenTypeParser();
//        Queue<TokenType> queue = parserJsonToTokenTypeList(json);
//        System.out.println(queue);

        TokenType tokenType = parserJsonToTokenObject(json);
        System.out.println(new TokenFormat().format(tokenType));

//        return parser.parserTokenTypeObjectToMap(tokenType);
        return null;
    }



    private Queue<TokenType> parserJsonToTokenTypeList(String json) {
        Queue<TokenType> queue = new ArrayDeque<>();
        JsonReader jsonReader = new StringJsonReader(json);

        StringBuffer sb = new StringBuffer();


        return queue;
    }

    private TokenType parserJsonToTokenObject(String json) {
        JsonReader jsonReader = new StringJsonReader(json);

        //先跳过第一个对象开始符前的所有空格字符
        char next;
        while (jsonReader.hasNext()&&((next=jsonReader.next())!=CharKey.KEY_OBJECT_BEGIN)){
            if (!KeyCheck.isSpaceKey(next)){
                throw new ZsonException("解析错误，在"+jsonReader.position()+"处存在错误的字符: ` "+next+" ` ");
            }
        }

        //解析
        TokenType tokenType = TokenHelper.parseForTokenObject(jsonReader);

        //一个json最外层一定就是一个对象，解析完成之后不应该还有除了空格以外的字符在后面
        while (jsonReader.hasNext()){
            if (!KeyCheck.isSpaceKey(jsonReader.next())){
                throw new ZsonException("解析错误，在解析完成之后还存在未知字符.");
            }
        }
        return tokenType;
    }
}
