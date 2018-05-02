package com.alex.code.foundation.ui.footprint;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.FootPrintBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

interface FootpintView extends BaseMvpView{

    void onSuccess(List<FootPrintBean.HistoryEntity> data);

    void showDeleteSuccess();
}
