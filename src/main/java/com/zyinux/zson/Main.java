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

    static String json="{\n" +
            "    \"ResultCode\": 200,\n" +
            "    \"Message\": \"\",\n" +
            "    \"CharSet\": \"utf-8\",\n" +
            "    \"Data\": {\n" +
            "        \"SellerReasonCnfs\": [\n" +
            "            \"其它\",\n" +
            "false,\n" +
            "123.453,\n" +
            "{\n" +
            "\"test\":\"hehe\"\n" +
            "},[123,123]\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) {
        Zson zson=new Zson();

        Map<String, Object> stringObjectMap = zson.parserJson(json);


    }
}
