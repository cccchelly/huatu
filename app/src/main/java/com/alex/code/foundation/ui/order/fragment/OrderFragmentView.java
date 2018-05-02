package com.alex.code.foundation.ui.order.fragment;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.OrderBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

interface OrderFragmentView extends BaseMvpView{

    void onSuccess(List<OrderBean.DataEntity> data);

    void showRemindDelivery();

    void showExtendReceive();

    void showConfirmReceive();

    void showCancelOrder();
}
