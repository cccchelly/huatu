package com.alex.code.foundation.ui.safety.changepw;

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
 * Date: 2017/12/7.
 */

public class ChangePassWordPresenter extends BaseMvpPresenter<ChangePassWordView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ChangePassWordPresenter() {
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

}
