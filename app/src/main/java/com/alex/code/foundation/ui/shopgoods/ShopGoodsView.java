package com.alex.code.foundation.ui.shopgoods;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.TotalShopGoodsBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

interface ShopGoodsView extends BaseMvpView{

    void onSuccess(TotalShopGoodsBean totalShopGoodsBean);
}
