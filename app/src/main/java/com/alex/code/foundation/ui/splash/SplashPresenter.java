package com.alex.code.foundation.ui.splash;

import com.alex.code.foundation.base.BaseMvpPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class SplashPresenter extends BaseMvpPresenter<SplashView> {

    @Inject
    public SplashPresenter() {
    }

    /**
     * 自动登录
     */
    public void autoLogin() {
        addDisposable(Observable.just(true)
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> countDown())
                .subscribe(isLogin -> {
                    if (isLogin) {
                        getView().enterMain();
                    } else {
                        getView().enterLogin();
                    }
                }));

    }

    /**
     * 登录倒计时
     */
    private void countDown() {
        addDisposable(Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                .map(count -> (3 - count))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> getView().timeCountDown(count)));
    }


}
