package com.alex.code.foundation.ui.forget.one;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.ForgetSMSBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/29.
 */

interface ForgetOneView extends BaseMvpView{

    void onSuccess(ForgetSMSBean forgetSMSBean);
}
