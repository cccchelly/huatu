package com.alex.code.foundation.ui.forget;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.ForgetPWBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/29.
 */

class ForgetPWPresenter extends BaseMvpPresenter<ForgetPWView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ForgetPWPresenter() {
    }

    public void findPassword(String mobile,String token, String password){
        mAppDataManager.findPassword(mobile,token,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ForgetPWBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ForgetPWBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
