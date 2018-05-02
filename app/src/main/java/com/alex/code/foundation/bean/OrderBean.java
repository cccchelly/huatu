package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

public class OrderBean {


    /**
     * data : [{"id":2,"order_no":"2017110924535","item_order_no":"151019646369821000","buyer_id":299,"receiver_mobile":"18728323933","receiver_name":"zyx","shop_id":1,"order_money":"0.00","pay_money":"0.00","order_status":1,"create_time":"2017-11-09 11:01:03","shipping_money":"0.00","shop_name":"官方直营店","shop_avatar":"","goods_list":[{"goods_id":411,"goods_name":"大宝剑","sku_id":0,"sku_name":"","price":"1.00","num":"2","goods_money":"2.00","buyer_id":299,"goods_picture":0,"picture":""},{"goods_id":403,"goods_name":"华为荣耀","sku_id":1551,"sku_name":"黄 60*60 ","price":"2000.00","num":"1","goods_money":"2000.00","buyer_id":299,"goods_picture":0,"picture":""}]},{"id":1,"order_no":"2017110976730","item_order_no":"151019618020331000","buyer_id":299,"receiver_mobile":"18728323933","receiver_name":"zyx","shop_id":1,"order_money":"3000.00","pay_money":"3000.00","order_status":1,"create_time":"2017-11-09 10:56:20","shipping_money":"1.00","shop_name":"官方直营店","shop_avatar":"","goods_list":[{"goods_id":402,"goods_name":"小米","sku_id":0,"sku_name":"","price":"1000.00","num":"3","goods_money":"3000.00","buyer_id":299,"goods_picture":0,"picture":""}]}]
     * total_count : 2
     * page_count : 1
     */

    private int total_count;
    private int              page_count;
    private List<DataEntity> data;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 2
         * order_no : 2017110924535
         * item_order_no : 151019646369821000
         * buyer_id : 299
         * receiver_mobile : 18728323933
         * receiver_name : zyx
         * shop_id : 1
         * order_money : 0.00
         * pay_money : 0.00
         * order_status : 1
         * create_time : 2017-11-09 11:01:03
         * shipping_money : 0.00
         * shop_name : 官方直营店
         * shop_avatar :
         * express:null
         * goods_list : [{"goods_id":411,"goods_name":"大宝剑","sku_id":0,"sku_name":"","price":"1.00","num":"2","goods_money":"2.00","buyer_id":299,"goods_picture":0,"picture":""},{"goods_id":403,"goods_name":"华为荣耀","sku_id":1551,"sku_name":"黄 60*60 ","price":"2000.00","num":"1","goods_money":"2000.00","buyer_id":299,"goods_picture":0,"picture":""}]
         */

        private int id;
        private String                order_no;
        private String                item_order_no;
        private int                   buyer_id;
        private String                receiver_mobile;
        private String                receiver_name;
        private int                   shop_id;
        private String                order_money;
        private String                pay_money;
        private int                   order_status;
        private String                create_time;
        private String                shipping_money;
        private String                shop_name;
        private String                shop_avatar;
        private String                goods_total_num;
        private List<GoodsListEntity> goods_list;
        private ExpressEntity express;

        public String getGoods_total_num() {
            return goods_total_num;
        }

        public void setGoods_total_num(String goods_total_num) {
            this.goods_total_num = goods_total_num;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getItem_order_no() {
            return item_order_no;
        }

        public void setItem_order_no(String item_order_no) {
            this.item_order_no = item_order_no;
        }

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getReceiver_mobile() {
            return receiver_mobile;
        }

        public void setReceiver_mobile(String receiver_mobile) {
            this.receiver_mobile = receiver_mobile;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getShipping_money() {
            return shipping_money;
        }

        public void setShipping_money(String shipping_money) {
            this.shipping_money = shipping_money;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_avatar() {
            return shop_avatar;
        }

        public void setShop_avatar(String shop_avatar) {
            this.shop_avatar = shop_avatar;
        }

        public List<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public ExpressEntity getExpress() {
            return express;
        }

        public void setExpress(ExpressEntity express) {
            this.express = express;
        }

        public static class GoodsListEntity {
            /**
             * goods_id : 411
             * goods_name : 大宝剑
             * sku_id : 0
             * sku_name :
             * price : 1.00
             * num : 2
             * goods_money : 2.00
             * buyer_id : 299
             * goods_picture : 0
             * picture :
             */

            private int goods_id;
            private String goods_name;
            private int    sku_id;
            private String sku_name;
            private String price;
            private String num;
            private String goods_money;
            private int    buyer_id;
            private int    goods_picture;
            private String picture;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getSku_id() {
                return sku_id;
            }

            public void setSku_id(int sku_id) {
                this.sku_id = sku_id;
            }

            public String getSku_name() {
                return sku_name;
            }

            public void setSku_name(String sku_name) {
                this.sku_name = sku_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getGoods_money() {
                return goods_money;
            }

            public void setGoods_money(String goods_money) {
                this.goods_money = goods_money;
            }

            public int getBuyer_id() {
                return buyer_id;
            }

            public void setBuyer_id(int buyer_id) {
                this.buyer_id = buyer_id;
            }

            public int getGoods_picture() {
                return goods_picture;
            }

            public void setGoods_picture(int goods_picture) {
                this.goods_picture = goods_picture;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
