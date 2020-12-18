package com.zyinux.zson.parser.json;

import com.zyinux.zson.token.TokenType;

/**
 * JsonParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/18
 */
public interface JsonParser {

    /**
     * 将json解析成TokenType
     * @param json
     * @return
     */
    TokenType parser(String json);
}
