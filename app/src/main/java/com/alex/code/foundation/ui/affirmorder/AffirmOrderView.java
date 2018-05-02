package com.alex.code.foundation.ui.affirmorder;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.CreateOrderBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

interface AffirmOrderView extends BaseMvpView{

    void onSuccess(CreateOrderBean createOrderBean);
}
