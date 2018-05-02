package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

public class FavoriteBean {


    /**
     * page : 1
     * list : [{"id":65,"goods_id":402,"goods_name":"sss","shop_id":0,"category_id":323,"goods_type":1,"price":"100.00","keywords":"","pic_cover_micro":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":66,"goods_id":403,"goods_name":"sss","shop_id":0,"category_id":323,"goods_type":1,"price":"100.00","keywords":"","pic_cover_micro":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":67,"goods_id":404,"goods_name":"sss","shop_id":0,"category_id":323,"goods_type":1,"price":"100.00","keywords":"","pic_cover_micro":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":68,"goods_id":405,"goods_name":"sss","shop_id":0,"category_id":323,"goods_type":1,"price":"100.00","keywords":"","pic_cover_micro":"upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":69,"goods_id":407,"goods_name":"测试商品","shop_id":0,"category_id":323,"goods_type":1,"price":"1000.00","keywords":"色色","pic_cover_micro":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg","pic_cover_mid":"upload/goods/e718c76adcc310e285d80147dc932c552.jpg"},{"id":70,"goods_id":408,"goods_name":"测试商品-副本","shop_id":0,"category_id":323,"goods_type":1,"price":"1000.00","keywords":"色色","pic_cover_micro":"upload/goods/bea0252521c548e72afb17c009d9c715.jpg","pic_cover_mid":"upload/goods/e718c76adcc310e285d80147dc932c552.jpg"}]
     * page_count : 1
     * total_count : 6
     */

    private String page;
    private int         page_count;
    private int         total_count;
    private List<Goods> list;

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

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    public static class Goods {
        /**
         * id : 65
         * goods_id : 402
         * goods_name : sss
         * shop_id : 0
         * category_id : 323
         * goods_type : 1
         * price : 100.00
         * keywords :
         * pic_cover_micro : upload/goods/ebb7249ac3000d4a0d7da07a5b471488.jpg
         * pic_cover_mid : upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg
         */

        private String id;
        private String goods_id;
        private String goods_name;
        private String shop_id;
        private String category_id;
        private String goods_type;
        private String price;
        private String keywords;
        private String pic_cover_micro;
        private String pic_cover_mid;
        private String introduction;
        private boolean isSelected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getPic_cover_micro() {
            return pic_cover_micro;
        }

        public void setPic_cover_micro(String pic_cover_micro) {
            this.pic_cover_micro = pic_cover_micro;
        }

        public String getPic_cover_mid() {
            return pic_cover_mid;
        }

        public void setPic_cover_mid(String pic_cover_mid) {
            this.pic_cover_mid = pic_cover_mid;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
