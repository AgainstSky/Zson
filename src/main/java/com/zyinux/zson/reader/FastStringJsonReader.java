package com.zyinux.zson.reader;

import com.zyinux.zson.exception.ZsonException;
import com.zyinux.zson.parser.token.AbstractTokenTargetParser;

/**
 * FastStringJsonReader
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
public class FastStringJsonReader extends AbstractJsonReader<String> {

    public FastStringJsonReader(String jsonStr) {
        super(jsonStr);
    }

    @Override
    public char next() throws ZsonException {
        char c = reader.charAt(pos);
        pos++;
        return c;
    }

    @Override
    public int length() {
        return reader.length();
    }

    @Override
    public void backOne() {
        pos--;
    }
}
