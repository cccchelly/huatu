package com.alex.code.foundation.ui.register;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseObserverErr0;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.RegisterBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/9.
 */

class RegisterPresenter extends BaseMvpPresenter<RegisterView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public RegisterPresenter() {
    }

    public void register(String username,String password,String email,String phone,String code){
        getView().showLoading();
        mAppDataManager.register(username,password,email,phone,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<RegisterBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<RegisterBean> response) {
                        getView().onSuccess();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    protected void onDataFailure(BaseResponse<RegisterBean> response) {
                        super.onDataFailure(response);
                        getView().onFail();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().onFail();
                    }
                });

    }
}
