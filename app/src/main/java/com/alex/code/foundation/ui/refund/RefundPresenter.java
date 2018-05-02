package com.alex.code.foundation.ui.refund;

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
 * Date: 2017/11/21.
 */

class RefundPresenter extends BaseMvpPresenter<RefundView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public RefundPresenter() {
    }

    public void postRefund(String reason,String phone, String money,
                           String content, String order_no,
                           String order_goods_id, String refund_num) {

        mAppDataManager.postRefund(reason,phone,money,content,order_no,order_goods_id,refund_num)
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

    public void postOrderRefund(String reason,String phone, String money,
                           String content, String order_no) {

        mAppDataManager.postOrderRefund(reason,phone,money,content,order_no)
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
