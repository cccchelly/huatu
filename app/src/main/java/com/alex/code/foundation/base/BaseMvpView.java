package com.alex.code.foundation.base;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface BaseMvpView extends MvpView {

    void showLoading(String msg);

    void dismissLoading();
}
