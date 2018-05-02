package com.alex.code.foundation.ui.secondsearch;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.bean.SecondSearchBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/13.
 */

interface SecondSearchView extends BaseMvpView{

    void onSuccess(List<SecondSearchBean.GoodsEntity> data, int totalCount);

    void showSearchFilter(List<SearchFilterBean.SearchEntity> filterData);
}
