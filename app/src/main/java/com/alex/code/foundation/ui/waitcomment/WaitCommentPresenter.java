package com.alex.code.foundation.ui.waitcomment;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/20.
 */

class WaitCommentPresenter extends BaseMvpPresenter<WaitCommentView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public WaitCommentPresenter() {
    }

    public void getOrderDetail(String order_num) {
        getView().showLoading("加载中...");
        mAppDataManager.getOrderDetail(order_num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<OrderDetailBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
