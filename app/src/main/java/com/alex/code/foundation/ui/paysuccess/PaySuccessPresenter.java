package com.alex.code.foundation.ui.paysuccess;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.PaySuccessBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/8.
 */

class PaySuccessPresenter extends BaseMvpPresenter<PaySuccessView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public PaySuccessPresenter() {
    }

    public void getPaySuccess(String order_no) {
        getView().showLoading("加载中...");
        mAppDataManager.getPaySuccess(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<PaySuccessBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<PaySuccessBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
