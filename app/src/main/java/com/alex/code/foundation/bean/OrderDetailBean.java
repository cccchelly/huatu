package com.alex.code.foundation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/9.
 */

public class OrderDetailBean {


    /**
     * id : 1
     * order_no : 2017110976730
     * item_order_no : 151019618020331000
     * buyer_id : 299
     * receiver_mobile : 18728323933
     * receiver_name : zyx
     * shop_id : 0
     * order_money : 3000.00
     * pay_money : 3000.00
     * order_status : 1
     * create_time : 2017-11-09 10:56:20
     * shipping_money : 1.00
     * receiver_address : 成都
     * pay_time : 1970-01-01 00:00:00
     * consign_time : 1970-01-01 00:00:00
     * head_img : test/img/avatar.png
     * shop_name : null
     * shop_avatar : null
     * goods_list : [{"goods_id":402,"goods_name":"小米","sku_id":0,"sku_name":"","price":"1000.00","num":"3","goods_money":"3000.00","buyer_id":299,"goods_picture":0,"picture":""}]
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
    private int                   order_status;
    private String                create_time;
    private String                shipping_money;
    private String                receiver_address;
    private String                pay_time;
    private String                consign_time;
    private String                head_img;
    private String                shop_name;
    private String                shop_avatar;
    private String                shop_qq;
    private List<GoodsListEntity> goods_list;
    private ExpressEntity express;

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

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getConsign_time() {
        return consign_time;
    }

    public void setConsign_time(String consign_time) {
        this.consign_time = consign_time;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

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

    public List<GoodsListEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public ExpressEntity getExpress() {
        return express;
    }

    public void setExpress(ExpressEntity express) {
        this.express = express;
    }

    public String getShop_qq() {
        return shop_qq;
    }

    public void setShop_qq(String shop_qq) {
        this.shop_qq = shop_qq;
    }

    public static class GoodsListEntity implements Parcelable{
        /**
         * goods_id : 402
         * order_goods_id : 1
         * goods_name : 小米
         * sku_id : 0
         * sku_name :
         * price : 1000.00
         * num : 3
         * goods_money : 3000.00
         * buyer_id : 299
         * goods_picture : 0
         * picture :
         * is_evaluate : 0
         * refund_status: 1
         */

        private int goods_id;
        private int order_goods_id;
        private String goods_name;
        private int    sku_id;
        private String sku_name;
        private String price;
        private String num;
        private String goods_money;
        private int    buyer_id;
        private int    goods_picture;
        private String picture;
        private int is_evaluate;
        private int refund_status;

        protected GoodsListEntity(Parcel in) {
            goods_id = in.readInt();
            order_goods_id = in.readInt();
            goods_name = in.readString();
            sku_id = in.readInt();
            sku_name = in.readString();
            price = in.readString();
            num = in.readString();
            goods_money = in.readString();
            buyer_id = in.readInt();
            goods_picture = in.readInt();
            picture = in.readString();
            is_evaluate = in.readInt();
            refund_status = in.readInt();
        }

        public static final Creator<GoodsListEntity> CREATOR = new Creator<GoodsListEntity>() {
            @Override
            public GoodsListEntity createFromParcel(Parcel in) {
                return new GoodsListEntity(in);
            }

            @Override
            public GoodsListEntity[] newArray(int size) {
                return new GoodsListEntity[size];
            }
        };

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }


        public int getOrder_goods_id() {
            return order_goods_id;
        }

        public void setOrder_goods_id(int order_goods_id) {
            this.order_goods_id = order_goods_id;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getIs_evaluate() {
            return is_evaluate;
        }

        public void setIs_evaluate(int is_evaluate) {
            this.is_evaluate = is_evaluate;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(goods_id);
            dest.writeInt(order_goods_id);
            dest.writeString(goods_name);
            dest.writeInt(sku_id);
            dest.writeString(sku_name);
            dest.writeString(price);
            dest.writeString(num);
            dest.writeString(goods_money);
            dest.writeInt(buyer_id);
            dest.writeInt(goods_picture);
            dest.writeString(picture);
            dest.writeInt(is_evaluate);
            dest.writeInt(refund_status);
        }
    }

}
