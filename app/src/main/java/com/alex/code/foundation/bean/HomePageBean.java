package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

public class HomePageBean {


    /**
     * shop_intro : {"shop_name":"官方直营店","shop_avatar":"template/shop/default/public/images/temp_14653489102725.jpg","shop_banner":"upload/common/1500631921.jpg","show_number":"3000","shop_number":"3000"}
     * notice : [{"notice_message":"手机端首页公告"}]
     * goods : [{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"6000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"6000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"6000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"6000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"5000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"5000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果6s","price":"5000"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果4s","price":"2500"},{"master":"upload/advertising/1497069853.png","goods_name":"苹果4s","price":"2500"}]
     * favorite : 0
     * debug_uid : 301
     * debug_way : mobile
     * session : []
     */

    private ShopIntroEntity shop_intro;
    private int                favorite;
    private List<NoticeEntity> notice;
    private List<GoodsEntity>  goods;

    public ShopIntroEntity getShop_intro() {
        return shop_intro;
    }

    public void setShop_intro(ShopIntroEntity shop_intro) {
        this.shop_intro = shop_intro;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public List<NoticeEntity> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeEntity> notice) {
        this.notice = notice;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public static class ShopIntroEntity {
        /**
         * shop_name : 官方直营店
         * shop_avatar : template/shop/default/public/images/temp_14653489102725.jpg
         * shop_banner : upload/common/1500631921.jpg
         * show_number : 3000
         * shop_number : 3000
         * shop_credit : 5
         * shop_qq :
         */

        private String shop_name;
        private String shop_avatar;
        private String shop_banner;
        private String show_number;
        private String shop_number;
        private int shop_credit;
        private String shop_qq;

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

        public String getShop_banner() {
            return shop_banner;
        }

        public void setShop_banner(String shop_banner) {
            this.shop_banner = shop_banner;
        }

        public String getShow_number() {
            return show_number;
        }

        public void setShow_number(String show_number) {
            this.show_number = show_number;
        }

        public String getShop_number() {
            return shop_number;
        }

        public void setShop_number(String shop_number) {
            this.shop_number = shop_number;
        }

        public int getShop_credit() {
            return shop_credit;
        }

        public void setShop_credit(int shop_credit) {
            this.shop_credit = shop_credit;
        }

        public String getShop_qq() {
            return shop_qq;
        }

        public void setShop_qq(String shop_qq) {
            this.shop_qq = shop_qq;
        }
    }

    public static class NoticeEntity {
        /**
         * notice_message : 手机端首页公告
         */

        private String notice_message;

        public String getNotice_message() {
            return notice_message;
        }

        public void setNotice_message(String notice_message) {
            this.notice_message = notice_message;
        }
    }

    public static class GoodsEntity {
        /**
         * goods_id : 9
         * goods_name : 苹果6s
         * master : upload/advertising/1497069853.png
         * price : 6000
         */

        private int goods_id;
        private String goods_name;
        private String master;
        private String price;

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
