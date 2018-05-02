package com.alex.code.foundation.utils.um;

/**
 * Created by dth
 * Des: 上报自定义事件id
 * Date: 2017/9/27.
 */

public interface UMEventId {

    String FLASH_SALE = "flash_sale";//限时抢购事件
    String HOT_GOODS = "hot_goods";//热卖商品事件
    String NEW_GOODS = "new_goods";//新品上市事件
    String MY_ORDER = "my_order";//我的订单事件
    String PAY_ORDER = "pay_order";//付款
    String GOODS_BUY = "goods_buy";//商品详情立即购买
    String GOODS_JOIN_SHOPCART = "goods_join_shopcart";//商品详情加入购物车
}
