package com.alex.code.foundation.ui.flashsale;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.FlashSaleBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/20.
 */

interface FlashSaleView extends BaseMvpView{

    void onSuccess(FlashSaleBean flashSaleBean);
}
