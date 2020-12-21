package com.zyinux.zson.parser;

import java.lang.reflect.Type;

/**
 * Parser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public interface Parser<T>{

    /**
     * 总体解析器
     * @param json
     * @return
     */
    T parser(String json);
}
