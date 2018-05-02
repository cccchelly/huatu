package com.alex.code.foundation.ui.safety.changephone;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/7.
 */

public class ChangePhonePresenter extends BaseMvpPresenter<ChangePhoneView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ChangePhonePresenter() {
    }

    public void updatePhone(String code, String phnoe) {

        mAppDataManager.updatePhone(code, phnoe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().onSuccess();
                    }
                });
    }
}
