package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

public class ShopBean {


    /**
     * page : 1
     * list : [{"shop_id":0,"shop_name":"官方直营店","shop_type":1,"shop_group_id":1,"shop_company_name":"成都天使有家","province_id":4,"city_id":14,"shop_address":"","shop_state":1,"shop_close_info":"","shop_logo":"template/shop/default/public/images/temp_14653489106946.jpg","shop_avatar":"template/shop/default/public/images/temp_14653489102725.jpg","shop_keywords":"成都天使有家","shop_description":"成都天使有家绝对牛逼！","shop_collect":42,"shop_stamp":null,"shop_printdesc":null,"id":34,"uid":299,"fav_id":0,"fav_type":"shop","fav_time":"2017-10-23 16:09:32","goods_name":"","goods_image":"","log_price":"0.00","log_msg":""}]
     * page_count : 1
     * total_count : 1
     */

    private String page;
    private int        page_count;
    private int        total_count;
    private List<Shop> list;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<Shop> getList() {
        return list;
    }

    public void setList(List<Shop> list) {
        this.list = list;
    }

    public static class Shop {
        /**
         * shop_id : 0
         * shop_name : 官方直营店
         * shop_type : 1
         * shop_group_id : 1
         * shop_company_name : 成都天使有家
         * province_id : 4
         * city_id : 14
         * shop_address :
         * shop_state : 1
         * shop_close_info :
         * shop_logo : template/shop/default/public/images/temp_14653489106946.jpg
         * shop_avatar : template/shop/default/public/images/temp_14653489102725.jpg
         * shop_keywords : 成都天使有家
         * shop_description : 成都天使有家绝对牛逼！
         * shop_collect : 42
         * shop_stamp : null
         * shop_printdesc : null
         * id : 34
         * uid : 299
         * fav_id : 0
         * fav_type : shop
         * fav_time : 2017-10-23 16:09:32
         * goods_name :
         * goods_image :
         * log_price : 0.00
         * log_msg :
         */

        private String shop_id;
        private String shop_name;
        private int    shop_type;
        private int    shop_group_id;
        private String shop_company_name;
        private int    province_id;
        private int    city_id;
        private String shop_address;
        private int    shop_state;
        private String shop_close_info;
        private String shop_logo;
        private String shop_avatar;
        private String shop_keywords;
        private String shop_description;
        private int    shop_collect;
        private Object shop_stamp;
        private Object shop_printdesc;
        private int    id;
        private int    uid;
        private int    fav_id;
        private String fav_type;
        private String fav_time;
        private String goods_name;
        private String goods_image;
        private String log_price;
        private String log_msg;
        private boolean isSelected;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getShop_type() {
            return shop_type;
        }

        public void setShop_type(int shop_type) {
            this.shop_type = shop_type;
        }

        public int getShop_group_id() {
            return shop_group_id;
        }

        public void setShop_group_id(int shop_group_id) {
            this.shop_group_id = shop_group_id;
        }

        public String getShop_company_name() {
            return shop_company_name;
        }

        public void setShop_company_name(String shop_company_name) {
            this.shop_company_name = shop_company_name;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public int getShop_state() {
            return shop_state;
        }

        public void setShop_state(int shop_state) {
            this.shop_state = shop_state;
        }

        public String getShop_close_info() {
            return shop_close_info;
        }

        public void setShop_close_info(String shop_close_info) {
            this.shop_close_info = shop_close_info;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getShop_avatar() {
            return shop_avatar;
        }

        public void setShop_avatar(String shop_avatar) {
            this.shop_avatar = shop_avatar;
        }

        public String getShop_keywords() {
            return shop_keywords;
        }

        public void setShop_keywords(String shop_keywords) {
            this.shop_keywords = shop_keywords;
        }

        public String getShop_description() {
            return shop_description;
        }

        public void setShop_description(String shop_description) {
            this.shop_description = shop_description;
        }

        public int getShop_collect() {
            return shop_collect;
        }

        public void setShop_collect(int shop_collect) {
            this.shop_collect = shop_collect;
        }

        public Object getShop_stamp() {
            return shop_stamp;
        }

        public void setShop_stamp(Object shop_stamp) {
            this.shop_stamp = shop_stamp;
        }

        public Object getShop_printdesc() {
            return shop_printdesc;
        }

        public void setShop_printdesc(Object shop_printdesc) {
            this.shop_printdesc = shop_printdesc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getFav_id() {
            return fav_id;
        }

        public void setFav_id(int fav_id) {
            this.fav_id = fav_id;
        }

        public String getFav_type() {
            return fav_type;
        }

        public void setFav_type(String fav_type) {
            this.fav_type = fav_type;
        }

        public String getFav_time() {
            return fav_time;
        }

        public void setFav_time(String fav_time) {
            this.fav_time = fav_time;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getLog_price() {
            return log_price;
        }

        public void setLog_price(String log_price) {
            this.log_price = log_price;
        }

        public String getLog_msg() {
            return log_msg;
        }

        public void setLog_msg(String log_msg) {
            this.log_msg = log_msg;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
