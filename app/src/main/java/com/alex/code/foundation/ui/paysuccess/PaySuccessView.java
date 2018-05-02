package com.alex.code.foundation.ui.paysuccess;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.PaySuccessBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/8.
 */

interface PaySuccessView extends BaseMvpView{

    void onSuccess(PaySuccessBean paySuccessBean);
}
