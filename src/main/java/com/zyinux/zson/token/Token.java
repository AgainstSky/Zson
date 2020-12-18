package com.zyinux.zson.token;

import com.zyinux.zson.CharKey;
import lombok.Getter;

/**
 * Token
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
@Getter
public enum Token {
    TOKEN_OBJECT_BEGIN(CharKey.KEY_OBJECT_BEGIN),
    TOKEN_OBJECT_END(CharKey.KEY_OBJECT_END),
    TOKEN_ARRAY_BEGIN(CharKey.KEY_ARRAY_BEGIN),
    TOKEN_ARRAY_END(CharKey.KEY_ARRAY_END),
    TOKEN_KEY(CharKey.KEY_KEY),
    TOKEN_ESCAPE(CharKey.KEY_ESCAPE),
    TOKEN_SEP_COLON(CharKey.KEY_SEP_COLON),
    TOKEN_SEP_COMMA(CharKey.KEY_SEP_COMMA),
    TOKEN_STRING,
    TOKEN_NUMBER,
    TOKEN_BOOL,
    TOKEN_OBJECT,
    TOKEN_NULL,
    TOKEN_ARRAY;

    /**
     * token对应的具体的字符串(如果有)
     */
    private char key;

    Token(char key) {
        this.key = key;
    }

    Token() {
    }
}
