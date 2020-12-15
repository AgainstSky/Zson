package com.zyinux.zson;

import java.util.Map;

/**
 * Main
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/8
 */
public class Main {

    static String json="{\"Title\":\"订单信息模板\",\"Content\":{\"orderId\":\"DB202012075929933433\",\"bizofferName\":\"大M无Kefu测试2020-12-06-001\",\"gameName\":\"天涯明月刀\",\"gameAreaName\":\"青龙乱舞\",\"gameServerName\":true,\"bizofferTypeName\":\"游戏帐号\",\"payPrice\":1050,\"orderStatus\":\"交易成功\"},\"ClassName\":\"fsOrderInfo\"}";

    public static void main(String[] args) {
        Zson zson=new Zson();
        Map<String, Object> stringObjectMap = zson.parserJson(json);


    }
}
