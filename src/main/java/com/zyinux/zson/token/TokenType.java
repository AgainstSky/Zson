package com.zyinux.zson.token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
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
public class TokenType {

    /**
     * 创建初始化了childToken的TokenType，只有Object和Array需要
     * @param token
     * @return
     */
    public static TokenType createForNeedChildToken(Token token){
        TokenType t=new TokenType(token);
        t.setChildToken(new ArrayList<>());
        return t;
    }

    Token token;

    /**
     * 理论上来说，只有TokenObject和TokenArray里才需要
     */
    List<TokenType> childToken;

    Object content;

    public TokenType(Token token) {
        this.token = token;
    }



    public TokenType(Token token, Object content) {
        this.token = token;
        this.content = content;
    }

    public TokenType() {

    }

    public void add(TokenType tokenType) {
        childToken.add(tokenType);
    }

    @Override
    public String toString() {
        return "{" +
                "" + token +
                ":" + childToken +
                '}';
    }
}
