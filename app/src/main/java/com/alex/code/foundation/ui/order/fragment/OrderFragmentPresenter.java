package com.alex.code.foundation.ui.order.fragment;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.OrderBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

class OrderFragmentPresenter extends BaseMvpPresenter<OrderFragmentView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public OrderFragmentPresenter() {
    }

    public void getOrderList(String order_type,String page_index, String page_size) {
        mAppDataManager.getOrderList(order_type,page_index,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<OrderBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<OrderBean> response) {

                        getView().onSuccess(response.getData().getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void remindDelivery(String order_no) {
        mAppDataManager.remindDelivery(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showRemindDelivery();
                    }
                });
    }

    public void extendReceive(String order_no) {
        mAppDataManager.extendReceive(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showExtendReceive();
                    }
                });
    }

    public void confirmReceive(String order_no) {

        mAppDataManager.confirmReceive(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showConfirmReceive();
                    }
                });
    }

    public void cancelOrder(String order_no) {

        mAppDataManager.cancelOrder(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showCancelOrder();
                    }
                });
    }
}
