package com.zyinux.zson.token;

import com.zyinux.zson.CharKey;

/**
 * TokenFormat
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class TokenFormat {

    //每一层用两个空格分隔开
    public final static String SPACE_KEY = "**";

    /**
     * 测试情况下的输出辅助，方便看清楚数据，不是正常情况下的格式化json
     *
     * @param tokenTarget
     * @return
     */
    public String formatForDebug(TokenTarget tokenTarget) {
        StringBuilder sb = new StringBuilder();
        if (isObjectStyleToken(tokenTarget)) {
            sb.append("{\n");
            for (TokenTarget type : tokenTarget.getChildToken()) {
                sb.append(formatForDebug(type));
            }
            sb.append("}\n");
        } else {
            if (tokenTarget.getContent() == null) {
                sb.append(tokenTarget.getToken().getKey()).append("\n");
            } else {
                sb.append(tokenTarget.getToken()).append("@ ").append(tokenTarget.getContent()).append("\n");
            }

        }

        return sb.toString();
    }

    public String format(TokenTarget tokenTarget) {
        return formatTokenObject(tokenTarget, "", 0, false);
    }

    /**
     * 解析一个token对象
     *
     * @param tokenObject
     * @param deep        当前解析的对象深度
     * @return
     */
    private String formatTokenObject(TokenTarget tokenObject, String placeholder, int deep, boolean needBreak) {
        StringBuilder sb = new StringBuilder();

        String childPlaceholder = buildChildDeepSpace(placeholder);

        if (needBreak) {
            sb.append("\n").append(placeholder);
        }

        sb
                .append(CharKey.KEY_OBJECT_BEGIN)
                .append("\n")
                .append(placeholder);

        if (tokenObject.getChildToken() != null) {
            for (TokenTarget child : tokenObject.getChildToken()) {
                switch (child.getToken()) {
                    case TOKEN_STRING:
                        sb.append(CharKey.KEY_KEY).append(child.getContent()).append(CharKey.KEY_KEY);
                        break;
                    case TOKEN_SEP_COLON:
                        sb.append(" ").append(CharKey.KEY_SEP_COLON).append(" ");
                        break;
                    case TOKEN_SEP_COMMA:
                        sb.append(CharKey.KEY_SEP_COMMA).append("\n").append(childPlaceholder);
                        break;
                    case TOKEN_BOOL:
                        sb.append(child.getContent());
                        break;
                    case TOKEN_NULL:
                        sb.append("null");
                        break;
                    case TOKEN_ARRAY:
                        sb.append(formatTokenArray(child, childPlaceholder.concat(SPACE_KEY), deep + 1));
                        break;
                    case TOKEN_NUMBER:
                        sb.append(child.getContent());
                        break;
                    case TOKEN_OBJECT:
                        sb.append(formatTokenObject(child, childPlaceholder.concat(SPACE_KEY), deep + 1, false));
                        break;
                    default:
                        break;
                }
            }
        }
        sb.append("\n");
        if (placeholder.length() <= 2) {
            sb.append(placeholder);
        } else {
            sb.append(placeholder, 0, placeholder.length() - 2);
        }
        sb.append(CharKey.KEY_OBJECT_END);
        return sb.toString();
    }

    private String formatTokenArray(TokenTarget tokenObject, String placeholder, int deep) {
        StringBuilder sb = new StringBuilder();

        String childPlaceholder = buildChildDeepSpace(placeholder);

        sb
                .append(CharKey.KEY_ARRAY_BEGIN)
                .append("\n")
                .append(placeholder);

        if (tokenObject.getChildToken() != null) {
            for (TokenTarget child : tokenObject.getChildToken()) {
                switch (child.getToken()) {
                    case TOKEN_STRING:
                        sb.append(CharKey.KEY_KEY).append(child.getContent()).append(CharKey.KEY_KEY);
                        break;
                    case TOKEN_SEP_COLON:
                        sb.append(" ").append(CharKey.KEY_SEP_COLON).append(" ");
                        break;
                    case TOKEN_SEP_COMMA:
                        sb.append(CharKey.KEY_SEP_COMMA).append("\n").append(placeholder);
                        break;
                    case TOKEN_BOOL:
                        sb.append(child.getContent());
                        break;
                    case TOKEN_NULL:
                        sb.append("null");
                        break;
                    case TOKEN_ARRAY:
                        sb.append(formatTokenArray(child, childPlaceholder, deep + 1));
                        break;
                    case TOKEN_NUMBER:
                        sb.append(child.getContent());
                        break;
                    case TOKEN_OBJECT:
                        sb.append(formatTokenObject(child, childPlaceholder, deep + 1, false));
                        break;
                    default:
                        break;
                }
            }
        }
        sb.append("\n").append(placeholder, 0, placeholder.length() - 2).append(CharKey.KEY_ARRAY_END);
        return sb.toString();
    }

    private String buildCurrentDeepSpace(String placeholder) {
        StringBuilder sb = new StringBuilder();
        sb.append(placeholder).append(SPACE_KEY);
        return sb.toString();
    }

    private String buildChildDeepSpace(String currPlaceholder) {
        StringBuilder sb = new StringBuilder();
        sb.append(currPlaceholder).append(SPACE_KEY);
        return sb.toString();
    }

    public boolean isObjectStyleToken(TokenTarget tokenTarget) {
        Token token = tokenTarget.getToken();
        return token == Token.TOKEN_OBJECT
                || token == Token.TOKEN_ARRAY;
    }
}
