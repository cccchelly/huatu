package com.alex.code.foundation.ui.safety.email;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.EmailBean;
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

class EmailPresenter extends BaseMvpPresenter<EmailView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public EmailPresenter() {
    }

    public void bindEmail(String email) {
        mAppDataManager.bindEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<EmailBean>>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse<EmailBean> response) {
                        getView().onSuccess(response.getData());
                    }
                });
    }
}
