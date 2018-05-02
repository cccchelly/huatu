package com.alex.code.foundation.ui.statistics;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.StatisticsBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/18.
 */

class StatisticsPresenter extends BaseMvpPresenter<StatisticsView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public StatisticsPresenter() {
    }

    public void getConsumeStatistics(String year) {
        mAppDataManager.getConsumeStatistics(year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<StatisticsBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<StatisticsBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
