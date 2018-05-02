package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/23.
 */

public class FootPrintBean {


    /**
     * history : [{"id":46,"updated_at":"2017-11-22 18:53:26","goods_id":404,"goods_name":"锤子手机","shop_id":54,"category_id":329,"goods_type":1,"market_price":"2500.00","price":"2400.00","promotion_price":"2400.00","cost_price":"2000.00","sales":54,"pic_cover_micro":"upload/goods/a2ec32e6368f0c1c962d569d830cb4dc4.jpg","pic_cover_mid":"upload/goods/a2ec32e6368f0c1c962d569d830cb4dc2.jpg","pic_cover_small":"upload/goods/a2ec32e6368f0c1c962d569d830cb4dc3.jpg"},{"id":8,"updated_at":"2017-11-22 17:59:42","goods_id":409,"goods_name":"新手套装","shop_id":52,"category_id":323,"goods_type":1,"market_price":"0.00","price":"100.00","promotion_price":"100.00","cost_price":"100.00","sales":200,"pic_cover_micro":"upload/goods/e718c76adcc310e285d80147dc932c554.jpg","pic_cover_mid":"upload/goods/e718c76adcc310e285d80147dc932c552.jpg","pic_cover_small":"upload/goods/e718c76adcc310e285d80147dc932c553.jpg"}]
     * debug_uid : 301
     * debug_way : app
     * session : []
     * requieLogin : false
     */

    private boolean requieLogin;
    private List<HistoryEntity> history;

    public boolean isRequieLogin() {
        return requieLogin;
    }

    public void setRequieLogin(boolean requieLogin) {
        this.requieLogin = requieLogin;
    }

    public List<HistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryEntity> history) {
        this.history = history;
    }

    public static class HistoryEntity {
        /**
         * id : 46
         * updated_at : 2017-11-22 18:53:26
         * goods_id : 404
         * goods_name : 锤子手机
         * shop_id : 54
         * category_id : 329
         * goods_type : 1
         * market_price : 2500.00
         * price : 2400.00
         * promotion_price : 2400.00
         * cost_price : 2000.00
         * sales : 54
         * pic_cover_micro : upload/goods/a2ec32e6368f0c1c962d569d830cb4dc4.jpg
         * pic_cover_mid : upload/goods/a2ec32e6368f0c1c962d569d830cb4dc2.jpg
         * pic_cover_small : upload/goods/a2ec32e6368f0c1c962d569d830cb4dc3.jpg
         */

        private int id;
        private String updated_at;
        private String    goods_id;
        private String goods_name;
        private int    shop_id;
        private int    category_id;
        private int    goods_type;
        private String market_price;
        private String price;
        private String promotion_price;
        private String cost_price;
        private int    sales;
        private String pic_cover_micro;
        private String pic_cover_mid;
        private String pic_cover_small;
        private boolean isSelected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
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

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
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

        public String getPic_cover_small() {
            return pic_cover_small;
        }

        public void setPic_cover_small(String pic_cover_small) {
            this.pic_cover_small = pic_cover_small;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
