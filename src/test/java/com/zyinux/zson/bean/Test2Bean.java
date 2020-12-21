package com.zyinux.zson.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Test2Bean
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
@Data
@NoArgsConstructor
public class Test2Bean {


    /**
     * statusCode : 200
     * status : true
     * data : {"pageIndex":1,"pageSize":10,"totalPage":1,"totalCount":1,"content":{"id":"imOrder01","orderId":"orderid001","status":0,"billType":1,"title":"订单标题","orderSource":"Sy.GameAccount","tradingType":3,"channelId":2,"bizName":"装备","chatId":"chatId001","isOld":false,"category":-10,"chatType":"BuyerAndKefu","indexRole":"Buyer","unReadCount":5.6,"lastSenderId":"userId001","lastSenderName":"userName001","lastSendRole":"Buyer","lastSendTime":"2020.11.16 08:17:02","lastMsgType":"txt","lastMsg":"最后一条聊天记录，多个会话时取最新一条...","otherId":"userId000333","otherName":"userName00033","otherRole":"KeFu"}}
     */

    private int statusCode;
    private boolean status;
    private DataBean data;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        /**
         * pageIndex : 1
         * pageSize : 10
         * totalPage : 1
         * totalCount : 1
         * content : {"id":"imOrder01","orderId":"orderid001","status":0,"billType":1,"title":"订单标题","orderSource":"Sy.GameAccount","tradingType":3,"channelId":2,"bizName":"装备","chatId":"chatId001","isOld":false,"category":-10,"chatType":"BuyerAndKefu","indexRole":"Buyer","unReadCount":5.6,"lastSenderId":"userId001","lastSenderName":"userName001","lastSendRole":"Buyer","lastSendTime":"2020.11.16 08:17:02","lastMsgType":"txt","lastMsg":"最后一条聊天记录，多个会话时取最新一条...","otherId":"userId000333","otherName":"userName00033","otherRole":"KeFu"}
         */

        private int pageIndex;
        private int pageSize;
        private int totalPage;
        private int totalCount;
        private ContentBean content;

        @NoArgsConstructor
        @Data
        public static class ContentBean {
            /**
             * id : imOrder01
             * orderId : orderid001
             * status : 0
             * billType : 1
             * title : 订单标题
             * orderSource : Sy.GameAccount
             * tradingType : 3
             * channelId : 2
             * bizName : 装备
             * chatId : chatId001
             * isOld : false
             * category : -10.0
             * chatType : BuyerAndKefu
             * indexRole : Buyer
             * unReadCount : 5.6
             * lastSenderId : userId001
             * lastSenderName : userName001
             * lastSendRole : Buyer
             * lastSendTime : 2020.11.16 08:17:02
             * lastMsgType : txt
             * lastMsg : 最后一条聊天记录，多个会话时取最新一条...
             * otherId : userId000333
             * otherName : userName00033
             * otherRole : KeFu
             */

            private String id;
            private String orderId;
            private int status;
            private int billType;
            private String title;
            private String orderSource;
            private int tradingType;
            private int channelId;
            private String bizName;
            private String chatId;
            private boolean isOld;
            private double category;
            private String chatType;
            private String indexRole;
            private double unReadCount;
            private String lastSenderId;
            private String lastSenderName;
            private String lastSendRole;
            private String lastSendTime;
            private String lastMsgType;
            private String lastMsg;
            private String otherId;
            private String otherName;
            private String otherRole;
        }
    }
}
