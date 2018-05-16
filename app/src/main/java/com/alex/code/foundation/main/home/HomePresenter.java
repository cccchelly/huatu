package com.alex.code.foundation.main.home;

import android.util.Log;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.HomeBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class HomePresenter extends BaseMvpPresenter<HomeView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public HomePresenter() {
    }

    public void getHomePageData() {
        mAppDataManager.getHomePageData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HomeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<HomeBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
