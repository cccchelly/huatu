package com.alex.code.foundation.ui.register;

import com.alex.code.foundation.base.BaseMvpView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/9.
 */

public interface RegisterView extends BaseMvpView {

    void onSuccess();

    void onFail();

    void showLoading();
}
