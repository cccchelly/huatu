package com.alex.code.foundation.ui.address;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
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
 * Date: 2017/10/16.
 */

class NewAddressPresenter extends BaseMvpPresenter<NewAddressView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public NewAddressPresenter() {
    }


    public void postAddress(String consigner, String mobile, String province, String city,
                            String district, String address, String is_default){

        mAppDataManager.postAddress(consigner,mobile,province,city,district,address,is_default)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse response) {

                        VLog.d("response: "+response.getCode());
                        getView().onSuccess();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateAddress(String id, String consigner, String mobile, String province, String city,
                            String district, String address, String is_default){

        mAppDataManager.updateAddress(id,consigner,mobile,province,city,district,address,is_default)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse response) {

                        VLog.d("response: "+response.getCode());
                        getView().onSuccess();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
