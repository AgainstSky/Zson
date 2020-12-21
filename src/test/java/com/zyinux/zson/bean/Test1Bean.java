package com.zyinux.zson.bean;

import lombok.Data;

import java.util.ArrayList;

/**
 * Test1Bean
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
@Data
public class Test1Bean {

    private String statusCode;
    public String name;
    public ArrayList<String> child;
    private Data data;

    @lombok.Data
    public static class Data{
        private int pageIndex;
        private int pageSize;
        private int totalPage;
        private int totalCount;
    }
}
