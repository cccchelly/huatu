package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

public class CartShopBean {

    /**
     * shop_id : 1
     * shop_name : 官方直营店
     * list : [{"id":3,"buyer_id":299,"shop_id":1,"spec_name":"颜色:红色;大小:60cm*60cm","goods_id":402,"shop_name":"官方直营店","num":3,"goods_name":"苹果","price":"100.00","introduction":"","pic_cover_micro":"upload/goods/9a540c9c41bd26b26803554ede5044e34.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":6,"buyer_id":299,"shop_id":1,"spec_name":"颜色:红色;大小:60cm*60cm","goods_id":403,"shop_name":"官方直营店","num":1,"goods_name":"华为","price":"100.00","introduction":"","pic_cover_micro":"upload/goods/9a540c9c41bd26b26803554ede5044e34.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"},{"id":7,"buyer_id":299,"shop_id":1,"spec_name":"颜色:红色;大小:60cm*60cm","goods_id":404,"shop_name":"官方直营店","num":1,"goods_name":"锤子","price":"100.00","introduction":"","pic_cover_micro":"upload/goods/9a540c9c41bd26b26803554ede5044e34.jpg","pic_cover_mid":"upload/goods/9a540c9c41bd26b26803554ede5044e32.jpg"}]
     */

    private   int                 shop_id;
    private   String              shop_name;
    private   List<CartGoodsBean> list;
    protected boolean             isChoosed;
    private   boolean             isEdtor;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<CartGoodsBean> getList() {
        return list;
    }

    public void setList(List<CartGoodsBean> list) {
        this.list = list;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isEdtor() {
        return isEdtor;
    }

    public void setEdtor(boolean edtor) {
        isEdtor = edtor;
    }

}
