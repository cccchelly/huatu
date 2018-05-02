package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/21.
 */

public class FlashSaleBean {


    /**
     * platform : [{"adv_title":"手机端商城热卖","adv_url":"#","adv_image":"upload/advertising/1497070853.png","slide_sort":1}]
     * time_list : [{"start_time":"2017-11-21 08:00:00","end_time":"2017-11-21 10:00:00","value":"已结束","type":1},{"start_time":"2017-11-21 08:00:00","end_time":"2017-11-21 12:00:00","value":"已结束","type":1},{"start_time":"2017-11-21 10:00:00","end_time":"2017-11-21 13:00:00","value":"已结束","type":1},{"start_time":"2017-11-21 11:00:00","end_time":"2017-11-21 12:00:00","value":"已结束","type":1},{"start_time":"2017-11-21 14:00:00","end_time":"2017-11-21 16:00:00","value":"抢购中","type":2},{"start_time":"2017-11-21 14:00:00","end_time":"2017-11-21 16:00:00","value":"抢购中","type":2}]
     * su_time : 3122
     * total_count : 1
     * goods : [{"goods_name":"苹果x","price":"9000.00","promotion_price":"9000.00","stock":4000,"master":"upload/advertising/1497068837.png"},{"goods_name":"新手套装","price":"100.00","promotion_price":"100.00","stock":1000,"master":"upload/advertising/1497069175.png"}]
     * debug_uid : 301
     * debug_way : mobile
     * session : []
     */

    private long su_time;
    private int                  total_count;
    private List<PlatformEntity> platform;
    private List<TimeListEntity> time_list;
    private List<GoodsEntity>    goods;

    public long getSu_time() {
        return su_time;
    }

    public void setSu_time(long su_time) {
        this.su_time = su_time;
    }

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

    public List<TimeListEntity> getTime_list() {
        return time_list;
    }

    public void setTime_list(List<TimeListEntity> time_list) {
        this.time_list = time_list;
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

    public static class TimeListEntity {
        /**
         * start_time : 2017-11-21 08:00:00
         * end_time : 2017-11-21 10:00:00
         * value : 已结束
         * type : 1
         */

        private String start_time;
        private String end_time;
        private String value;
        private int    type;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class GoodsEntity {
        /**
         * goods_id : 412
         * goods_name : 苹果x
         * price : 9000.00
         * promotion_price : 9000.00
         * stock : 4000
         * master : upload/advertising/1497068837.png
         */

        private String goods_id;
        private String goods_name;
        private String price;
        private String promotion_price;
        private int    stock;
        private String master;

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

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }
    }
}
