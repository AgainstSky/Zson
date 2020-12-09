package com.zyinux.zson.reader;

/**
 * AbstractReader
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public abstract class AbstractJsonReader<T> implements JsonReader {

    protected int pos=0;

    protected String jsonStr;

    protected T reader;

    public AbstractJsonReader(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Override
    public int position() {
        return pos;
    }

    @Override
    public boolean hasNext() {
        if (pos<jsonStr.length()){
            return true;
        }
        return false;
    }
}
