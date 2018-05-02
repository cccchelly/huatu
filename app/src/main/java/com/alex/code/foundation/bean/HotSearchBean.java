package com.alex.code.foundation.bean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

public class HotSearchBean {


    private List<SearchEntity> goods;
    private List<SearchEntity>  shop;
    private List<SearchEntity> brand;

    public List<SearchEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<SearchEntity> goods) {
        this.goods = goods;
    }

    public List<SearchEntity> getShop() {
        return shop;
    }

    public void setShop(List<SearchEntity> shop) {
        this.shop = shop;
    }

    public List<SearchEntity> getBrand() {
        return brand;
    }

    public void setBrand(List<SearchEntity> brand) {
        this.brand = brand;
    }



    public static class SearchEntity {
        /**
         * shop_name : 苹果专卖店4
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
