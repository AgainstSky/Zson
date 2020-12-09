package com.zyinux.zson.token.helper;

import com.zyinux.zson.CharKey;
import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.reader.JsonReader;
import com.zyinux.zson.token.KeyCheck;

/**
 * TokenHelper
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class TokenHelper {


    /**
     * 解析接下来的流，直到遇到下一个{@link CharKey.KEY_KEY}，中间所有的内容就是一个完整的TokenString
     * @param jsonReader
     * @return
     */
    public static String findTheTokenStringData(JsonReader jsonReader) {
        StringBuffer sb = new StringBuffer();
        char next = ' ';
        while (jsonReader.hasNext() && (next = jsonReader.next()) != CharKey.KEY_KEY) {
            sb.append(next);
            //这里需要考虑转义字符的问题，其他字符应该没有太过需要处理的，但是当下一个字符是' " '也就是CharKey.KEY_KEY的时候需要特别处理
            //转义字符正常情况下两个搭配在一起使用的，所以直接多拿下一个char存入sb里面。
            //当然如果下一个不存在了，则直接抛出异常
            if (KeyCheck.isEscape(next)){
                if (!jsonReader.hasNext()){
                    throw new ZsonException("解析错误，在第"+jsonReader.position()+"处预期不能结尾，但是json结束了");
                }
                sb.append(jsonReader.next());
            }

        }

        if (next != CharKey.KEY_KEY) {
            throw new ZsonException("解析错误，在第"+jsonReader.position()+"处预期有一个'"+CharKey.KEY_KEY+"'，但是不存在");
        }
        return sb.toString();
    }
}
