package com.alex.code.foundation.ui.waitcomment;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.OrderDetailBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/20.
 */

interface WaitCommentView extends BaseMvpView{

    void onSuccess(OrderDetailBean orderDetailBean);
}
