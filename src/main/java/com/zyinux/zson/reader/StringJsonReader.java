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

    private char backCache;

    private volatile boolean isBack;

    public StringJsonReader(String jsonStr) {
        super(jsonStr);
        reader = new StringReader(jsonStr);

    }

    @Override
    public char next() throws ZsonException {
        if (isBack) {
            pos++;
            isBack = false;
            return backCache;
        }
        try {
            if (pos >= length()) {
                throw new ZsonException("读取到末尾");
            }
            char read = (char) reader.read();
            pos++;
            backCache = read;
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

    @Override
    public void backOne() {
        if (pos == 0) {
            throw new ZsonException("回退字符失败，当前为第一位无法回退");
        }
        pos--;
        isBack = true;
    }
}
