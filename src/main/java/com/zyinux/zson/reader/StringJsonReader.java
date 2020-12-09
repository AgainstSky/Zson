package com.zyinux.zson.reader;

import com.zyinux.zson.exception.ZsonException;

import java.io.IOException;
import java.io.StringReader;

/**
 * StringReaderer
 *
 * @author zyinux
 * @Desc StringReader 实现的JsonReader
 * @date 2020/12/8
 */
public class StringJsonReader extends AbstractJsonReader<StringReader> {

    public StringJsonReader(String jsonStr) {
        super(jsonStr);
        reader = new StringReader(jsonStr);

    }

    @Override
    public char next() throws ZsonException{
        try {
            if (pos>=length()) {
                throw new ZsonException("读取到末尾");
            }
            char read = (char) reader.read();
            pos++;
            return read;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ' ';
    }

    @Override
    public int length() {
        return jsonStr.length();
    }
}
