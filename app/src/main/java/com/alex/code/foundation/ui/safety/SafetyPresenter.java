package com.alex.code.foundation.ui.safety;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/19.
 */

class SafetyPresenter extends BaseMvpPresenter<SafetyView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public SafetyPresenter() {
    }


    public void updatePasswrod(String old_password, String password) {
        mAppDataManager.updatePassword(old_password,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().onSuccess();
                    }
                });
    }

    public void updatePhone(String code, String phnoe) {

        mAppDataManager.updatePhone(code, phnoe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().onSuccess();
                    }
                });
    }

    public void logout() {
        mAppDataManager.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        mAppDataManager.setToken("");
                        getView().onLogoutSuccess();
                    }
                });
    }
}
