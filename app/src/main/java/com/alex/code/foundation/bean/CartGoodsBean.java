package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/2.
 */

public class CartGoodsBean {

    /**
     * id : 3
     * buyer_id : 299
     * shop_id : 1
     * spec_name : 颜色:红色;大小:60cm*60cm
     * goods_id : 402
     * shop_name : 官方直营店
     * num : 3
     * goods_name : 苹果
     * price : 100.00
     * introduction :
     * pic_cover_micro : upload/goods/9a540c9c41bd26b26803554ede5044e34.jpg
     * pic_cover_mid : upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg
     */

    private String id;
    private String    buyer_id;
    private String    shop_id;
    private String spec_name;
    private String    goods_id;
    private String shop_name;
    private int    num;
    private String goods_name;
    private double price;
    private String introduction;
    private String pic_cover_micro;
    private String pic_cover_mid;

    protected boolean isChoosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
