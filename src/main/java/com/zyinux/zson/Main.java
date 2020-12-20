package com.zyinux.zson;

import com.zyinux.zson.log.Log;

import java.util.List;
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
            "            123,\n" +
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

        Map<String, Object> map = zson.parserJson(json);
        Map<String,Object> data = (Map<String, Object>) map.get("Data");
        List list= (List) data.get("SellerReasonCnfs");
        String s = (String) list.get(0);
        s.equalsIgnoreCase()
        Log.info(s);
    }
}
