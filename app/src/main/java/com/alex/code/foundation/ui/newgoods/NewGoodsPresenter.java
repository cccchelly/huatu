package com.alex.code.foundation.ui.newgoods;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

class NewGoodsPresenter extends BaseMvpPresenter<NewGoodsView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public NewGoodsPresenter() {
    }

    public void getNewGoods(String sort, String page_index,
                            String page_size, String low_par,
                            String tall_par,  String json){

        mAppDataManager.getNewGoods(sort,page_index,page_size,low_par,tall_par,json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HotGoodsBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<HotGoodsBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }

    public void getSearchFilterList() {
        mAppDataManager.getSearchFilterList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<SearchFilterBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<SearchFilterBean> response) {

                        getView().showSearchFilter(response.getData().getSearch());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
