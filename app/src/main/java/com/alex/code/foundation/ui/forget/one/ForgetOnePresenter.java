package com.alex.code.foundation.ui.forget.one;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.ForgetSMSBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/29.
 */

class ForgetOnePresenter extends BaseMvpPresenter<ForgetOneView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ForgetOnePresenter() {
    }

    public void findPasswordSMS(String mobile, String code) {
        mAppDataManager.findPasswordSMS(mobile,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ForgetSMSBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ForgetSMSBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
