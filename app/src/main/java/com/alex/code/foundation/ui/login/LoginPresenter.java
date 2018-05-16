package com.alex.code.foundation.ui.login;

import android.util.Log;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.LoginInfo;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.ui.login.module.AuthCredentials;
import com.alex.code.foundation.utils.VLog;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BaseMvpPresenter<LoginView> {

    private AuthCredentials mFakeAuthCredentials = new AuthCredentials("abc", "123");

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public LoginPresenter() {
    }

    public void login(AuthCredentials authCredentials) {
        getView().showLoading();

//        addDisposable(Observable.just(authCredentials)
//                .map(credentials -> {
//                    try {
//                        // Simulate network delay
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return TextUtils.equals(credentials.getUsername(), mFakeAuthCredentials.getUsername())
//                            && TextUtils.equals(credentials.getPassword(), mFakeAuthCredentials.getPassword());
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(isAccessed -> {
//                    if (isAccessed) {
//                        getView().loginSuccessful();
//                    } else {
//                        getView().showError();
//                    }
//                }));

    }


    public void login(String username, String password) {
        getView().showLoading();
        Log.i("==login==","name="+username+",pass="+password);
        mAppDataManager.login(username,password)
//                .delay(4000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<LoginInfo> response) {
                        getView().loginSuccessful();
                        LoginInfo userInfo = response.getData();
                        mAppDataManager.setToken(userInfo.getToken());
                        VLog.d("login---- token: %s",userInfo.getToken());

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError();
                    }

                    @Override
                    protected void onDataFailure(BaseResponse<LoginInfo> response) {
                        super.onDataFailure(response);
                        getView().showError();
                    }
                });
    }

    public void oauthLogin(String type, String payload) {
        getView().showLoading();
        mAppDataManager.oauthLogin(type,payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<LoginInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<LoginInfo> response) {
                        getView().loginSuccessful();
                        LoginInfo userInfo = response.getData();
                        mAppDataManager.setToken(userInfo.getToken());
                        VLog.d("oauthLogin----- token: %s",userInfo.getToken());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().showError();
                    }

                    @Override
                    protected void onDataFailure(BaseResponse<LoginInfo> response) {
                        super.onDataFailure(response);
                        getView().showError();
                    }
                });
    }
}
