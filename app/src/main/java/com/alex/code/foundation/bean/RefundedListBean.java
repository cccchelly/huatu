package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/25.
 */

public class RefundedListBean {


    /**
     * data : [{"id":20,"order_no":"2017112443128","item_order_no":"151149069166341000","buyer_id":301,"receiver_mobile":"18800001111","receiver_name":"李白","shop_id":52,"order_money":"20.00","pay_money":"20.00","order_status":-1,"create_time":"1970-01-01 08:33:37","shipping_money":"0.00","shop_name":"银川商铺","shop_avatar":null,"goods_total_num":2,"goods_list":[{"order_goods_id":20,"goods_id":415,"goods_name":"测试","sku_id":1608,"sku_name":"颜色：红色","price":"10.00","num":"2","goods_money":"20.00","buyer_id":301,"goods_picture":0,"refund_time":"2017-11-24 18:05:48","refund_require_money":"20.00","refund_real_money":"0.00","refund_reason":"缺货","refund_content":"000","refund_status":1,"picture":""}]},{"id":18,"order_no":"2017112481237","item_order_no":"151149067446871000","buyer_id":301,"receiver_mobile":"18800001111","receiver_name":"李白","shop_id":54,"order_money":"11.00","pay_money":"11.00","order_status":-1,"create_time":"1970-01-01 08:33:37","shipping_money":"0.00","shop_name":"银川食品","shop_avatar":null,"goods_total_num":1,"goods_list":[{"order_goods_id":18,"goods_id":414,"goods_name":"小米7","sku_id":1541,"sku_name":"颜色：蓝色;大小：中;材质：青铜","price":"11.00","num":"1","goods_money":"11.00","buyer_id":301,"goods_picture":1924,"refund_time":"2017-11-24 18:08:05","refund_require_money":"11.00","refund_real_money":"0.00","refund_reason":"不想要了","refund_content":"222","refund_status":1,"picture":"upload/goods/fbf9dafb47b58b7fd1c8cb1027c415973.jpg"}]}]
     * total_count : 2
     * page_count : 1
     * debug_uid : 301
     * debug_way : app
     * session : []
     * requieLogin : false
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
         * id : 20
         * order_no : 2017112443128
         * item_order_no : 151149069166341000
         * buyer_id : 301
         * receiver_mobile : 18800001111
         * receiver_name : 李白
         * shop_id : 52
         * order_money : 20.00
         * pay_money : 20.00
         * refund_money:
         * order_status : -1
         * create_time : 1970-01-01 08:33:37
         * shipping_money : 0.00
         * shop_name : 银川商铺
         * shop_avatar : null
         * goods_total_num : 2
         * goods_list : [{"order_goods_id":20,"goods_id":415,"goods_name":"测试","sku_id":1608,"sku_name":"颜色：红色","price":"10.00","num":"2","goods_money":"20.00","buyer_id":301,"goods_picture":0,"refund_time":"2017-11-24 18:05:48","refund_require_money":"20.00","refund_real_money":"0.00","refund_reason":"缺货","refund_content":"000","refund_status":1,"picture":""}]
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
        private String                refund_money;
        private int                   order_status;
        private String                create_time;
        private String                shipping_money;
        private String                shop_name;
        private Object                shop_avatar;
        private int                   goods_total_num;
        private List<GoodsListEntity> goods_list;

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

        public String getRefund_money() {
            return refund_money;
        }

        public void setRefund_money(String refund_money) {
            this.refund_money = refund_money;
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

        public Object getShop_avatar() {
            return shop_avatar;
        }

        public void setShop_avatar(Object shop_avatar) {
            this.shop_avatar = shop_avatar;
        }

        public int getGoods_total_num() {
            return goods_total_num;
        }

        public void setGoods_total_num(int goods_total_num) {
            this.goods_total_num = goods_total_num;
        }

        public List<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListEntity {
            /**
             * order_goods_id : 20
             * goods_id : 415
             * goods_name : 测试
             * sku_id : 1608
             * sku_name : 颜色：红色
             * price : 10.00
             * num : 2
             * goods_money : 20.00
             * buyer_id : 301
             * goods_picture : 0
             * refund_time : 2017-11-24 18:05:48
             * refund_require_money : 20.00
             * refund_real_money : 0.00
             * refund_reason : 缺货
             * refund_content : 000
             * refund_status : 1
             * picture :
             */

            private int order_goods_id;
            private int    goods_id;
            private String goods_name;
            private int    sku_id;
            private String sku_name;
            private String price;
            private String num;
            private String goods_money;
            private int    buyer_id;
            private int    goods_picture;
            private String refund_time;
            private String refund_require_money;
            private String refund_real_money;
            private String refund_reason;
            private String refund_content;
            private int    refund_status;
            private String picture;

            public int getOrder_goods_id() {
                return order_goods_id;
            }

            public void setOrder_goods_id(int order_goods_id) {
                this.order_goods_id = order_goods_id;
            }

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

            public String getRefund_time() {
                return refund_time;
            }

            public void setRefund_time(String refund_time) {
                this.refund_time = refund_time;
            }

            public String getRefund_require_money() {
                return refund_require_money;
            }

            public void setRefund_require_money(String refund_require_money) {
                this.refund_require_money = refund_require_money;
            }

            public String getRefund_real_money() {
                return refund_real_money;
            }

            public void setRefund_real_money(String refund_real_money) {
                this.refund_real_money = refund_real_money;
            }

            public String getRefund_reason() {
                return refund_reason;
            }

            public void setRefund_reason(String refund_reason) {
                this.refund_reason = refund_reason;
            }

            public String getRefund_content() {
                return refund_content;
            }

            public void setRefund_content(String refund_content) {
                this.refund_content = refund_content;
            }

            public int getRefund_status() {
                return refund_status;
            }

            public void setRefund_status(int refund_status) {
                this.refund_status = refund_status;
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
