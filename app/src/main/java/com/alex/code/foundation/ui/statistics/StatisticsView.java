package com.alex.code.foundation.ui.statistics;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.StatisticsBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/18.
 */

interface StatisticsView extends BaseMvpView{

    void onSuccess(StatisticsBean statisticsBean);
}
