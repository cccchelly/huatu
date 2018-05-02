package com.alex.code.foundation.main.mine;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.UserInfo;
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

public class MinePresenter extends BaseMvpPresenter<MineView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public MinePresenter() {
    }

    public void getUserInfo() {
        getView().showLoading("加载中...");
        mAppDataManager.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<UserInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<UserInfo> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
