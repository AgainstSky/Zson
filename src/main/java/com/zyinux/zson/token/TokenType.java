package com.zyinux.zson.token;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TokenType
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
@Data
@AllArgsConstructor
public class TokenType {

    Token token;

    Object data;

    public TokenType(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
                "" + token +
                ":" + data +
                '}';
    }
}
