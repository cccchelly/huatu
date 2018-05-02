package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/18.
 */

public class HotGoodsBean {


    /**
     * platform : [{"adv_title":"手机端商城热卖","adv_url":"#","adv_image":"upload/advertising/1497070853.png","slide_sort":1}]
     * goods : [{"goods_id":"402","goods_name":"原装 hp685墨盒 惠普4625墨盒 5525 3525 HP4615打印机墨盒 黑色","master":"upload/advertising/1497069853.png","price":"149"}]
     * total_count : 1
     * debug_uid : 301
     * debug_way : mobile
     * session : []
     */

    private int total_count;
    private List<PlatformEntity> platform;
    private List<GoodsEntity>    goods;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<PlatformEntity> getPlatform() {
        return platform;
    }

    public void setPlatform(List<PlatformEntity> platform) {
        this.platform = platform;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public static class PlatformEntity {
        /**
         * adv_title : 手机端商城热卖
         * adv_url : #
         * adv_image : upload/advertising/1497070853.png
         * slide_sort : 1
         */

        private String adv_title;
        private String adv_url;
        private String adv_image;
        private int    slide_sort;

        public String getAdv_title() {
            return adv_title;
        }

        public void setAdv_title(String adv_title) {
            this.adv_title = adv_title;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_image() {
            return adv_image;
        }

        public void setAdv_image(String adv_image) {
            this.adv_image = adv_image;
        }

        public int getSlide_sort() {
            return slide_sort;
        }

        public void setSlide_sort(int slide_sort) {
            this.slide_sort = slide_sort;
        }
    }

    public static class GoodsEntity {
        /**
         * goods_id : 402
         * goods_name : 原装 hp685墨盒 惠普4625墨盒 5525 3525 HP4615打印机墨盒 黑色
         * master : upload/advertising/1497069853.png
         * price : 149
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
