package com.alex.code.foundation.ui.register.verification;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.ImageCode;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerificationMvpPresenter extends BaseMvpPresenter<VerificationMvpView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public VerificationMvpPresenter() {
    }

    void fetchVerificationUrl(String phone) {

        getView().showGraphicVerificationCode("http://static.meishifulu.cn/app/captcha/getCode?phone="+phone+"&curTime=" + System.currentTimeMillis());
    }

    void postVerificationCode(String phone, String code) {
        mAppDataManager.getImageCode(phone,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ImageCode>>(getView()) {

                    @Override
                    public void onSuccess(BaseResponse<ImageCode> response) {
                        if (response.getCode() == 0) {
                            getView().verificationSuccessful();
                        } else {
                            getView().verificationFailed();
                        }
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                });

    }
}
