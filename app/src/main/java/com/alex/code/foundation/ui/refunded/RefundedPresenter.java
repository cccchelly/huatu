package com.alex.code.foundation.ui.refunded;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.RefundedListBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/25.
 */

class RefundedPresenter extends BaseMvpPresenter<RefundedView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public RefundedPresenter() {
    }

    public void getRefundedList(String page_index,String page_size) {

        mAppDataManager.getRefundedList(page_index,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<RefundedListBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<RefundedListBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
