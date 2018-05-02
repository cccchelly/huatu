package com.alex.code.foundation.ui.newgoods;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.bean.SearchFilterBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

interface NewGoodsView extends BaseMvpView{

    void onSuccess(HotGoodsBean hotGoodsBean);

    void showSearchFilter(List<SearchFilterBean.SearchEntity> filterData);
}
