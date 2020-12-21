package com.zyinux.zson.parser.json;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.reader.FastStringJsonReader;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.reader.StringJsonReader;
import com.zyinux.zson.token.KeyCheck;
import com.zyinux.zson.token.TokenTarget;
import com.zyinux.zson.token.helper.TokenHelper;

/**
 * SimpleJsonParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/18
 */
public class SimpleJsonParser implements JsonParser {


    @Override
    public TokenTarget parser(String json) {
        TokenTarget tokenTarget = parserJsonToTokenObject(json);
//        return parser.parserTokenTypeObjectToMap(tokenType);
        return tokenTarget;
    }

    private TokenTarget parserJsonToTokenObject(String json) {
        JsonReader jsonReader = new StringJsonReader(json);

        //先跳过第一个对象开始符前的所有空格字符
        char next;
        while (jsonReader.hasNext() && ((next = jsonReader.next()) != CharKey.KEY_OBJECT_BEGIN)) {
            if (!KeyCheck.isSpaceKey(next)) {
                throw new ZsonException("解析错误，在" + jsonReader.position() + "处存在错误的字符: ` " + next + " ` ");
            }
        }

        //解析
        TokenTarget tokenTarget = TokenHelper.parseForTokenObject(jsonReader);

        //一个json最外层一定就是一个对象，解析完成之后不应该还有除了空格以外的字符在后面
        while (jsonReader.hasNext()) {
            if (!KeyCheck.isSpaceKey(jsonReader.next())) {
                throw new ZsonException("解析错误，在解析完成之后还存在未知字符.");
            }
        }
        return tokenTarget;
    }
}
