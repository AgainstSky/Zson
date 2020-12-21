package com.zyinux.zson.parser.token;

import com.zyinux.zson.token.TokenTarget;

/**
 * TokenTypeParser
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/18
 */
public interface TokenTypeParser<T> {

    /**
     * 将TokenType解析
     * @return
     */
    T parserTokenType(TokenTarget tokenObject);
}
