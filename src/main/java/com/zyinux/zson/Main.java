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

    static String json="{\"robot\":\"false\",\"object\":{\"name\":false}}";

    public static void main(String[] args) {
        Zson zson=new Zson();
        Map<String, Object> stringObjectMap = zson.parserJson(json);
        assert stringObjectMap!=null;
    }
}
