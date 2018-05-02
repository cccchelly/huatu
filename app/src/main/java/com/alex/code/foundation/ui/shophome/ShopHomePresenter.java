package com.alex.code.foundation.ui.shophome;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.HomePageBean;
import com.alex.code.foundation.bean.ShopFocusBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

class ShopHomePresenter extends BaseMvpPresenter<ShopHomeView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ShopHomePresenter() {
    }

    public void getShopHomePage(String shop_id) {
        getView().showLoading("加载中...");
        mAppDataManager.getShopHomePage(shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HomePageBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<HomePageBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateShopFocus(String shop_id, String favo_type){
        mAppDataManager.updateShopFocus(shop_id,favo_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopFocusBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ShopFocusBean> response) {
                        getView().updateFocus(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
