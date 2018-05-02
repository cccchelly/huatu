package com.alex.code.foundation.ui.homesearch.fragment;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.HotSearchBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

class HomeSearchPresenter2 extends BaseMvpPresenter<HomeSearchView2> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public HomeSearchPresenter2() {
    }

    public void getHotSearchList() {
        mAppDataManager.getHotSearchList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HotSearchBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<HotSearchBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
