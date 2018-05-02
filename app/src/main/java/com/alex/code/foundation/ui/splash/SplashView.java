package com.alex.code.foundation.ui.splash;

import com.alex.code.foundation.base.BaseMvpView;

public interface SplashView extends BaseMvpView{

    void enterLogin();

    void enterMain();

    void timeCountDown(long time);
}
