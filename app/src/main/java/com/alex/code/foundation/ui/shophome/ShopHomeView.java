package com.alex.code.foundation.ui.shophome;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.HomePageBean;
import com.alex.code.foundation.bean.ShopFocusBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

interface ShopHomeView extends BaseMvpView{

    void onSuccess(HomePageBean homePageBean);

    void updateFocus(ShopFocusBean shopFocusBean);
}
