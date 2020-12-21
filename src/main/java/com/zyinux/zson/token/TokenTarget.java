package com.zyinux.zson.token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TokenType
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/9
 */
@Data
@AllArgsConstructor
public class TokenTarget {

    /**
     * 创建初始化了childToken的TokenType，只有Object和Array需要
     * @param token
     * @return
     */
    public static TokenTarget createForNeedChildToken(Token token){
        TokenTarget t=new TokenTarget(token);
        t.setChildToken(new ArrayList<>());
        return t;
    }

    Token token;

    /**
     * 理论上来说，只有TokenObject和TokenArray里才需要
     */
    List<TokenTarget> childToken;

    Object content;

    public TokenTarget(Token token) {
        this.token = token;
    }



    public TokenTarget(Token token, Object content) {
        this.token = token;
        this.content = content;
    }

    public TokenTarget() {

    }

    public void add(TokenTarget tokenTarget) {
        childToken.add(tokenTarget);
    }

    @Override
    public String toString() {
        return "{" +
                "" + token +
                ":" + childToken +
                '}';
    }
}
