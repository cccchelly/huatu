package com.alex.code.foundation.ui.shopsearch;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.SecondSearchBean;
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

class ShopSearchPresenter extends BaseMvpPresenter<ShopSearchView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ShopSearchPresenter() {
    }

    public void getSecondSearchList(String ser_type,String condition,
                                    String sort, String page_index, String page_size,String low_par,
                                    String tall_par,String json,String cate){

        mAppDataManager.getSecondSearchList(ser_type, condition, sort, page_index, page_size,low_par,tall_par,json,cate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<SecondSearchBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<SecondSearchBean> response) {

                        getView().onSuceess(response.getData().getShop(),response.getData().getTotal_count());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
