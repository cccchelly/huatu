package com.alex.code.foundation.ui.address;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.AddressBean;
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

class AddressPresenter extends BaseMvpPresenter<AddressView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public AddressPresenter() {
    }

    public void getAddressList() {
        mAppDataManager.getAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<AddressBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<AddressBean> response) {
                        getView().onSuccess(response.getData().getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateAddressDefault(String id) {
        mAppDataManager.updateAddressDefault(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        VLog.d("response: "+response.getCode());
                    }
                });
    }

    public void deleteAddress(String id) {
        mAppDataManager.deleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        //删除成功后重新查询一遍数据
                        getAddressList();
                    }
                });
    }
}
