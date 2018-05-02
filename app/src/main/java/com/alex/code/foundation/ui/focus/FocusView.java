package com.alex.code.foundation.ui.focus;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.ShopBean;
import com.alex.code.foundation.bean.ShopTypeBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

interface FocusView extends BaseMvpView{

    void onSuccess(List<ShopBean.Shop> data);

    void showDeleteSuccess();

    void showCategorySuccess(List<ShopTypeBean.ListEntity> data);
}
