package com.alex.code.foundation.ui.focus;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.ShopBean;
import com.alex.code.foundation.bean.ShopTypeBean;
import com.alex.code.foundation.data.AppDataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

class FocusPresenter extends BaseMvpPresenter<FocusView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public FocusPresenter() {

    }

    public void getShopList(String id) {
       mAppDataManager.getShopList(id)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new BaseObserver<BaseResponse<ShopBean>>(getView()) {
                   @Override
                   public void onSuccess(BaseResponse<ShopBean> response) {
                       List<ShopBean.Shop> shopList = response.getData().getList();

                       getView().onSuccess(shopList);
                   }

                   @Override
                   public void onSubscribe(@NonNull Disposable d) {

                       addDisposable(d);
                   }
               });
    }

    public void deleteFavoriteList(String type, String... id) {

        mAppDataManager.deleteFavoriteList(type,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<FavoriteBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<FavoriteBean> response) {
                        getView().showDeleteSuccess();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }

    public void getShopCategory() {

        mAppDataManager.getShopGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopTypeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ShopTypeBean> response) {

                        getView().showCategorySuccess(response.getData().getList());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }
}
