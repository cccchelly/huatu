package com.alex.code.foundation.ui.logistics;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.LogisticsBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

interface LogisticsView extends BaseMvpView{

    void onSuccess(LogisticsBean logisticsBean);
}
