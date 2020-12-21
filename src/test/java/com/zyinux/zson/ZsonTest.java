package com.zyinux.zson;

import com.zyinux.zson.bean.Test1Bean;
import com.zyinux.zson.bean.Test2Bean;
import com.zyinux.zson.log.Log;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ZsonTest
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
class ZsonTest {

    String json = "{\n" +
            "    \"statusCode\":200,\n" +
            "    \"name\":\"zyinux\",\n" +
            "    \"data\":{\n" +
            "        \"pageIndex\":1,\n" +
            "        \"pageSize\":10,\n" +
            "        \"totalPage\":-1,\n" +
            "        \"totalCount\":1\n" +
            "    }\n" +
            "}";

    String json2 = "{\n" +
            "    \"statusCode\":200,\n" +
            "\"status\":true,\n" +
            "    \"data\":{\n" +
            "        \"pageIndex\":1,\n" +
            "        \"pageSize\":10,\n" +
            "        \"totalPage\":1,\n" +
            "        \"totalCount\":1,\n" +
            "        \"content\":\n" +
            "            {\n" +
            "                \"id\":\"imOrder01\",\n" +
            "                \"orderId\":\"orderid001\",\n" +
            "                \"status\":0,\n" +
            "                \"billType\":1,\n" +
            "                \"title\":\"订单标题\",\n" +
            "                \"orderSource\":\"Sy.GameAccount\",\n" +
            "                \"tradingType\":3,\n" +
            "                \"channelId\":2,\n" +
            "                \"bizName\":\"装备\",\n" +
            "                \"chatId\":\"chatId001\",\n" +
            "                \"isOld\":false,\n" +
            "                \"category\":-10.0,\n" +
            "                \"chatType\":\"BuyerAndKefu\",\n" +
            "                \"indexRole\":\"Buyer\",\n" +
            "                \"unReadCount\":5.6,\n" +
            "                \"lastSenderId\":\"userId001\",\n" +
            "                \"lastSenderName\":\"userName001\",\n" +
            "                \"lastSendRole\":\"Buyer\",\n" +
            "                \"lastSendTime\":\"2020.11.16 08:17:02\",\n" +
            "                \"lastMsgType\":\"txt\",\n" +
            "                \"lastMsg\":\"最后一条聊天记录，多个会话时取最新一条...\",\n" +
            "                \"otherId\":\"userId000333\",\n" +
            "                \"otherName\":\"userName00033\",\n" +
            "                \"otherRole\":\"KeFu\"\n" +
            "            }\n" +
            "        \n" +
            "    }\n" +
            "}";

    @Test
    void parserJson() {


        Zson zson = new Zson();
        Map<String, Object> stringObjectMap = zson.parserJson(json);
        System.out.println(stringObjectMap);
//        Test1Bean test1Bean = zson.parserJson(json, Test1Bean.class);
    }

    @Test
    void parserJsonToBean() {


        long st = System.currentTimeMillis();

        Zson zson = new Zson();

        Test2Bean test2Bean = zson.parserJson(json2, Test2Bean.class);
        System.out.println(test2Bean);

        long et = System.currentTimeMillis();
        System.out.println(et - st);
    }
}