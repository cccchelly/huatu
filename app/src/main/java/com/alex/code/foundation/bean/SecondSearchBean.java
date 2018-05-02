package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/13.
 */

public class SecondSearchBean {


    /**
     * ser_type : 1
     * goods : [{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"},{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"},{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"},{"goods_id":8,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"5000"},{"goods_id":8,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"5000"},{"goods_id":8,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"5000"},{"goods_id":6,"goods_name":"苹果4s","master":"upload/advertising/1497069853.png","price":"2500"},{"goods_id":6,"goods_name":"苹果4s","master":"upload/advertising/1497069853.png","price":"2500"}]
     * debug_uid : 301
     * debug_way : mobile
     * total_count : 1
     */

    private String ser_type;
    private List<GoodsEntity> goods;
    private int total_count;
    private List<ShopEntity> shop;

    public List<ShopEntity> getShop() {
        return shop;
    }

    public void setShop(List<ShopEntity> shop) {
        this.shop = shop;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getSer_type() {
        return ser_type;
    }

    public void setSer_type(String ser_type) {
        this.ser_type = ser_type;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public static class GoodsEntity {
        /**
         * goods_id : 9
         * goods_name : 苹果6s
         * master : upload/advertising/1497069853.png
         * price : 6000
         */

        private String goods_id;
        private String goods_name;
        private String master;
        private String price;

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

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class ShopEntity{

        /**
         * goods_intro : [{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"},{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"},{"goods_id":9,"goods_name":"苹果6s","master":"upload/advertising/1497069853.png","price":"6000"}]
         * shop_avatar : upload/advertising/1497069853.png
         * shop_id : 3
         * shop_name : 衣服专卖店3
         * shop_number : 3000
         * show_number : 3000
         */

        private String shop_avatar;
        private String                    shop_id;
        private String                 shop_name;
        private String                 shop_number;
        private String                 show_number;
        private List<GoodsIntroEntity> goods_intro;

        public String getShop_avatar() {
            return shop_avatar;
        }

        public void setShop_avatar(String shop_avatar) {
            this.shop_avatar = shop_avatar;
        }

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

        public String getShop_number() {
            return shop_number;
        }

        public void setShop_number(String shop_number) {
            this.shop_number = shop_number;
        }

        public String getShow_number() {
            return show_number;
        }

        public void setShow_number(String show_number) {
            this.show_number = show_number;
        }

        public List<GoodsIntroEntity> getGoods_intro() {
            return goods_intro;
        }

        public void setGoods_intro(List<GoodsIntroEntity> goods_intro) {
            this.goods_intro = goods_intro;
        }

        public static class GoodsIntroEntity {
            /**
             * goods_id : 9
             * goods_name : 苹果6s
             * master : upload/advertising/1497069853.png
             * price : 6000
             */

            private String goods_id;
            private String goods_name;
            private String master;
            private String price;

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

            public String getMaster() {
                return master;
            }

            public void setMaster(String master) {
                this.master = master;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
