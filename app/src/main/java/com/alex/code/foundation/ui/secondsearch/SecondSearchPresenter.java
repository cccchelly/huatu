package com.alex.code.foundation.ui.secondsearch;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.bean.SecondSearchBean;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.VLog;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/13.
 */

class SecondSearchPresenter extends BaseMvpPresenter<SecondSearchView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public SecondSearchPresenter() {
    }

    public void getSecondSearchList(String ser_type,String condition,
                                    String sort, String page_index, String page_size,String low_par,
                                    String tall_par,String json,String cate){

        VLog.d("getSecondSearchList: "+"\nser_type: "+ser_type+"\ncondition: "+condition+"\nsort: "+sort+"\njson: "+json);
        mAppDataManager.getSecondSearchList(ser_type, condition, sort, page_index, page_size,low_par,tall_par,json,cate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<SecondSearchBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<SecondSearchBean> response) {

                        getView().onSuccess(response.getData().getGoods(),response.getData().getTotal_count());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void getSearchFilterList(String ser_type,String condition,String cate){
        mAppDataManager.getSearchFilterList(ser_type,condition,cate)
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
