package com.alex.code.foundation.ui.details.attrs;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.GoodsDetailBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

interface AttrsView extends BaseMvpView{

    void onSuccess(GoodsDetailBean goodsDetailBean);
}
