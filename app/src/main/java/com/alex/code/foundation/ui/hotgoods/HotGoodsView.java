package com.alex.code.foundation.ui.hotgoods;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.HotGoodsBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

interface HotGoodsView extends BaseMvpView{

    void onSuccess(HotGoodsBean hotGoodsBean);
}
