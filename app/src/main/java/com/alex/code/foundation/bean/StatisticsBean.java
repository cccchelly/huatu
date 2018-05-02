package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017-12-25.
 */

public class StatisticsBean{


    /**
     * month_list : [{"month":1,"price":0,"order_number":0,"goods_name_list":[]},{"month":2,"price":0,"order_number":0,"goods_name_list":[]},{"month":3,"price":0,"order_number":0,"goods_name_list":[]},{"month":4,"price":0,"order_number":0,"goods_name_list":[]},{"month":5,"price":0,"order_number":0,"goods_name_list":[]},{"month":6,"price":0,"order_number":0,"goods_name_list":[]},{"month":7,"price":0,"order_number":0,"goods_name_list":[]},{"month":8,"price":0,"order_number":0,"goods_name_list":[]},{"month":9,"price":0,"order_number":0,"goods_name_list":[]},{"month":10,"price":1000,"order_number":2,"goods_name_list":[{"goods_name":"绒典羊绒开衫女秋冬半高领拉链绞花纯羊绒加厚针织短款毛衣外套","order_price":"500.00"},{"goods_name":"philosofie/菲洛索菲初秋新款 棉绒 V领男套衫 PM6316150","order_price":"500.00"}]},{"month":11,"price":600,"order_number":1,"goods_name_list":[{"goods_name":"philosofie/菲洛索菲初秋新款 棉绒 V领男套衫 PM6316150","order_price":"600.00"}]},{"month":12,"price":0,"order_number":0,"goods_name_list":[]}]
     * debug_uid : 340
     * debug_way : app
     * requieLogin : false
     */

    private List<MonthListEntity> month_list;

    public List<MonthListEntity> getMonth_list() {
        return month_list;
    }

    public void setMonth_list(List<MonthListEntity> month_list) {
        this.month_list = month_list;
    }

    public static class MonthListEntity {
        /**
         * month : 1
         * price : 0
         * order_number : 0
         * goods_name_list : []
         */

        private int month;
        private int     price;
        private int     order_number;
        private List<GoodsNameEntity> goods_name_list;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getOrder_number() {
            return order_number;
        }

        public void setOrder_number(int order_number) {
            this.order_number = order_number;
        }

        public List<GoodsNameEntity> getGoods_name_list() {
            return goods_name_list;
        }

        public void setGoods_name_list(List<GoodsNameEntity> goods_name_list) {
            this.goods_name_list = goods_name_list;
        }

        public static class GoodsNameEntity{

            /**
             * "goods_name": "绒典羊绒开衫女秋冬半高领拉链绞花纯羊绒加厚针织短款毛衣外套",
             *  "order_price": "500.00"
             */

            private String goods_name;
            private String order_price;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getOrder_price() {
                return order_price;
            }

            public void setOrder_price(String order_price) {
                this.order_price = order_price;
            }
        }
    }
}
