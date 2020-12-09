package com.zyinux.zson.token.helper;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.reader.JsonReader;

/**
 * TokenHelper
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class TokenHelper {

    public static Object findTheTokenStringData(JsonReader jsonReader) {
        StringBuffer sb = new StringBuffer();
        char next = ' ';
        while (jsonReader.hasNext() && (next = jsonReader.next()) != CharKey.KEY_KEY) {
            sb.append(next);
        }

        if (next != CharKey.KEY_KEY) {
            throw new ZsonException("解析错误，在第"+jsonReader.position()+"处预期有一个'"+CharKey.KEY_KEY+"'，但是不存在");
        }
        return sb.toString();
    }
}
