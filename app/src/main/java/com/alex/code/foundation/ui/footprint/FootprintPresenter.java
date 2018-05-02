package com.alex.code.foundation.ui.footprint;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.FootPrintBean;
import com.alex.code.foundation.data.AppDataManager;

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

class FootprintPresenter extends BaseMvpPresenter<FootpintView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public FootprintPresenter() {

    }

    public void getFootPrint() {
        mAppDataManager.getFootPrint()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<FootPrintBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<FootPrintBean> response) {

                        getView().onSuccess(response.getData().getHistory());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void deleteFootPrint(String... goods_id) {
        mAppDataManager.deleteFootPrint(goods_id)
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
}
