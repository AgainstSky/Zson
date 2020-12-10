package com.zyinux.zson.token;

/**
 * TokenFormat
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
public class TokenFormat {

    /**
     * 测试情况下的输出辅助，方便看清楚数据，不是正常情况下的格式化json
     *
     * @param tokenType
     * @return
     */
    public String formatForDebug(TokenType tokenType) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        if (isObjectStyleToken(tokenType)){
            for (TokenType type : tokenType.getChildToken()) {
                sb.append(formatForDebug(type));
            }
        }else {
            if (tokenType.getContent()==null){
                sb.append(tokenType.getToken().getKey()).append("\n");
            }else {
                sb.append(tokenType.getContent()).append("\n");
            }

        }
        sb.append("}\n");
        return sb.toString();
    }

    public boolean isObjectStyleToken(TokenType tokenType) {
        Token token = tokenType.getToken();
        return token == Token.TOKEN_OBJECT
                || token == Token.TOKEN_ARRAY;
    }
}
