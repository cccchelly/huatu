package com.alex.code.foundation.ui.flashsale;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.FlashSaleBean;
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

class FlashSalePresenter extends BaseMvpPresenter<FlashSaleView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public FlashSalePresenter() {
    }

    public void getFlashSaleList(String start_time,String end_time,String page_index,String page_size) {
        mAppDataManager.getFlashSaleList(start_time,end_time,page_index,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<FlashSaleBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<FlashSaleBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
