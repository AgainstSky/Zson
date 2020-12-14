package com.zyinux.zson.reader;

import com.zyinux.zson.exception.ZsonException;

/**
 * Reader
 *
 * @author zyinux
 * @Desc 解析时和流处理交互的统一接口
 * 这么设计还有一个原因就是我目前并没有想好怎么处理流，而且也不是很清楚性能问题
 * 这样子抽象分离，后期切换不同的流处理器也方便很多
 * @date 2020/12/8
 */
public interface JsonReader {

    /**
     * 不关心具体的实现，只要能返回下一个字符就行了。
     * @return next char
     * @throws ZsonException
     */
    char next() throws ZsonException;

    /**
     * 返回总的字符长度
     * @return length
     */
    int length();

    /**
     * 当前处理的字符位置
     * @return
     */
    int position();

    /**
     * 是否还有下一个字符
     * @return
     */
    boolean hasNext();

    /**
     * 回退一个字符。某些时候需要用到
     */
    void backOne();
}
