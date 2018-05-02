package com.alex.code.foundation.ui.details;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.AtteGoodsBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.bean.ShopCartBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/26.
 */

interface GoodsDetailView extends BaseMvpView{

    void onSuccess(GoodSpeBean goodSpeBean);

    void onOrderSuccess(ShopCartBean shopCartBean);

    void showAffirmOrder(AffirmOrderBean affirmOrderBean);

    void showAddShopCart();

    void updateAtteGoods(AtteGoodsBean atteGoodsBean);
}
