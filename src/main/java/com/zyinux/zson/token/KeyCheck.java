package com.zyinux.zson.token;

import com.zyinux.zson.CharKey;

/**
 * KeyCheck
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class KeyCheck {

    /**
     * 判断字符是否为需要跳过的字符
     * 比如空格或者制表符等这些显然是不需要处理的（或者当上一个token是{@link Token.TOKEN_KEY}时，因为这代表是字符串里面的值）
     * @param next
     * @return
     */
    public static boolean isContinueKey(char next) {
        if (next == ' ' || next == '\t' || next == '\n') {
            return true;
        }
        return false;
    }

    public static boolean isEscape(char next) {
        return next == CharKey.KEY_ESCAPE;
    }

    /**
     * 一个值的结尾符号，正确的情况下，只有  , ]  }  来结束，不应该存在其他值
     * @param next
     * @return
     */
    public static boolean isValueEndKey(char next) {
        return next==CharKey.KEY_SEP_COMMA||next==CharKey.KEY_ARRAY_END||next==CharKey.KEY_OBJECT_END;
    }

    /**
     * 是不是一个boolean类型的false
     * @param value
     * @return
     */
    public static boolean isFalseKey(String value) {
        return "false".equals(value);
    }

    /**
     * 是不是一个 null
     * @param value
     * @return
     */
    public static boolean isNullKey(String value) {
        return "null".equals(value);
    }

    /**
     * 是不是一个boolean类型的true
     * @param value
     * @return
     */
    public static boolean isTrueKey(String value) {
        return "true".equals(value);
    }

    public static boolean isSpaceKey(char next) {
        return next == CharKey.KEY_SPACE;
    }
}
