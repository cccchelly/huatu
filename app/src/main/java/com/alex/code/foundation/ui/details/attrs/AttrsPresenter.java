package com.alex.code.foundation.ui.details.attrs;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.GoodsDetailBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

class AttrsPresenter extends BaseMvpPresenter<AttrsView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public AttrsPresenter() {
    }

    public void getGoodsDetail(String id) {
        getView().showLoading("加载中...");
        mAppDataManager.getGoodsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<GoodsDetailBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<GoodsDetailBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }
}
