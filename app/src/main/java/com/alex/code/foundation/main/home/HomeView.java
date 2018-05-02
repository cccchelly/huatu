package com.alex.code.foundation.main.home;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.HomeBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public interface HomeView extends BaseMvpView {

    void onSuccess(HomeBean homeBean);
}
