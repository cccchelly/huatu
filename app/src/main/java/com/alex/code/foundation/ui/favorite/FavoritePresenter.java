package com.alex.code.foundation.ui.favorite;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.GoodsTypeBean;
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

class FavoritePresenter extends BaseMvpPresenter<FavoriteView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public FavoritePresenter() {

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

    public void getGoodsList(String id) {
        mAppDataManager.getGoodsList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<FavoriteBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<FavoriteBean> response) {

                        List<FavoriteBean.Goods> list = response.getData().getList();
                        getView().onSuccess(list);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void getGoodsCategory() {
        mAppDataManager.getGoodsCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<GoodsTypeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<GoodsTypeBean> response) {
                        List<GoodsTypeBean.ListEntity> list = response.getData().getList();
                        getView().showCategorySuccess(list);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }
}
