package com.alex.code.foundation.ui.favorite;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.GoodsTypeBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

interface FavoriteView extends BaseMvpView{

    void onSuccess(List<FavoriteBean.Goods> data);

    void showDeleteSuccess();

    void showCategorySuccess(List<GoodsTypeBean.ListEntity> data);
}
