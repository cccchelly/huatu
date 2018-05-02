package com.alex.code.foundation.main.shop;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.CartShopBean;
import com.alex.code.foundation.bean.GoodSpeBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public interface ShopView extends BaseMvpView {

    void onSuccess(List<CartShopBean> data);

    void showChangeNum(List<CartShopBean> data , int groupPosition, int childPosition);

    void showOrderDetail(AffirmOrderBean affirmOrderBean);

    void showGoodSpec(GoodSpeBean goodSpeBean);

    void showChangeSpe(List<CartShopBean> data , int groupPosition, int childPosition);

}
