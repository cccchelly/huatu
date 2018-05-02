package com.alex.code.foundation.ui.userinfo.change;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/20.
 */

class ChangeNamePresenter extends BaseMvpPresenter<ChangeNameView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ChangeNamePresenter() {
    }

    public void updateProfileInfo(String name, String sex, String brithday) {
        mAppDataManager.updateProfileInfo(name,sex,brithday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ProfileInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ProfileInfo> response) {
                        ProfileInfo.UserinfoEntity userinfo = response.getData().getUserinfo();
                        getView().onSuccess(userinfo);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
