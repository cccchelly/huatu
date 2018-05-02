package com.alex.code.foundation.ui.shopsearch;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.SecondSearchBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

interface ShopSearchView extends BaseMvpView{

    void onSuceess(List<SecondSearchBean.ShopEntity> data, int totalCount);
}
