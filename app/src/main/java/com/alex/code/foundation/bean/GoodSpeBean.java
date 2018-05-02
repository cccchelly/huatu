package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

public class GoodSpeBean {

    private List<SpecListEntity> spec_list;
    private List<SkuListEntity> sku_list;
    private String introduction;

    public List<SpecListEntity> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<SpecListEntity> spec_list) {
        this.spec_list = spec_list;
    }

    public List<SkuListEntity> getSku_list() {
        return sku_list;
    }

    public void setSku_list(List<SkuListEntity> sku_list) {
        this.sku_list = sku_list;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public static class SpecListEntity {
        /**
         * spec_name : 颜色
         * spec_id : 9
         * value : [{"spec_value_name":"红","spec_name":"颜色","spec_id":9,"spec_value_id":10,"spec_show_type":1,"spec_value_data":""}]
         */

        private String spec_name;
        private int               spec_id;
        private List<ValueEntity> value;

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
            this.spec_id = spec_id;
        }

        public List<ValueEntity> getValue() {
            return value;
        }

        public void setValue(List<ValueEntity> value) {
            this.value = value;
        }

        public static class ValueEntity {
            /**
             * spec_value_name : 红
             * spec_name : 颜色
             * spec_id : 9
             * spec_value_id : 10
             * spec_show_type : 1
             * spec_value_data :
             */

            private String spec_value_name;
            private String spec_name;
            private int    spec_id;
            private int    spec_value_id;
            private int    spec_show_type;
            private String spec_value_data;

            public String getSpec_value_name() {
                return spec_value_name;
            }

            public void setSpec_value_name(String spec_value_name) {
                this.spec_value_name = spec_value_name;
            }

            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public int getSpec_value_id() {
                return spec_value_id;
            }

            public void setSpec_value_id(int spec_value_id) {
                this.spec_value_id = spec_value_id;
            }

            public int getSpec_show_type() {
                return spec_show_type;
            }

            public void setSpec_show_type(int spec_show_type) {
                this.spec_show_type = spec_show_type;
            }

            public String getSpec_value_data() {
                return spec_value_data;
            }

            public void setSpec_value_data(String spec_value_data) {
                this.spec_value_data = spec_value_data;
            }
        }
    }

    public static class SkuListEntity {
        /**
         * id : 1496
         * goods_id : 407
         * sku_name :
         * attr_value_items :
         * attr_value_items_format :
         * market_price : 0.00
         * price : 1000.00
         * promote_price : 1000.00
         * cost_price : 0.00
         * stock : 1000
         * picture : 0
         * code :
         * QRcode :
         * create_date : 2017-10-11 15:17:34
         * update_date : null
         * member_price : 1000
         */

        private int id;
        private int    goods_id;
        private String sku_name;
        private String attr_value_items;
        private String attr_value_items_format;
        private String market_price;
        private String price;
        private String promote_price;
        private String cost_price;
        private int    stock;
        private String    picture;
        private String code;
        private String QRcode;
        private String create_date;
        private Object update_date;
        private double    member_price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getAttr_value_items() {
            return attr_value_items;
        }

        public void setAttr_value_items(String attr_value_items) {
            this.attr_value_items = attr_value_items;
        }

        public String getAttr_value_items_format() {
            return attr_value_items_format;
        }

        public void setAttr_value_items_format(String attr_value_items_format) {
            this.attr_value_items_format = attr_value_items_format;
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

        public String getPromote_price() {
            return promote_price;
        }

        public void setPromote_price(String promote_price) {
            this.promote_price = promote_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getQRcode() {
            return QRcode;
        }

        public void setQRcode(String QRcode) {
            this.QRcode = QRcode;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public Object getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(Object update_date) {
            this.update_date = update_date;
        }

        public double getMember_price() {
            return member_price;
        }

        public void setMember_price(double member_price) {
            this.member_price = member_price;
        }
    }
}
