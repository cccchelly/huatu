package com.alex.code.foundation.main.category;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.CategoreGroupBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public interface CategoryView extends BaseMvpView {

    void showCategoryData(List<CategoreGroupBean> data);

}
