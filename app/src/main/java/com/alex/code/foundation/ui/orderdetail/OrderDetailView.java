package com.alex.code.foundation.ui.orderdetail;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.LogisticsBean;
import com.alex.code.foundation.bean.OrderDetailBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

interface OrderDetailView extends BaseMvpView{

    void onSuccess(OrderDetailBean orderDetailBean);

    void showRemindDelivery();

    void showExtendReceive();

    void showConfirmReceive();

    void showCancelOrder();

    void showLogisticsInfo(LogisticsBean logisticsBean);
}
