package com.alex.code.foundation.ui.refunded;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.RefundedListBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/25.
 */

interface RefundedView extends BaseMvpView{

    void onSuccess(RefundedListBean refundedListBean);
}
