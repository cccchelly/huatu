package com.alex.code.foundation.ui.userinfo;

import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.bean.UploadPicBean;
import com.alex.code.foundation.data.AppDataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

class UserInfoPresenter extends BaseMvpPresenter<UserInfoView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public UserInfoPresenter() {

    }

    public void getProfileInfo() {
        mAppDataManager.getProfileInfo()
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

    public void updateProfileInfo(String name, String sex, String birthday) {
        mAppDataManager.updateProfileInfo(name,sex,birthday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ProfileInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ProfileInfo> response) {
                        ProfileInfo.UserinfoEntity userinfo = response.getData().getUserinfo();
                        getView().showUpdateData(userinfo);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void uploadPic(List<MultipartBody.Part> partList) {
        getView().showLoading("图片上传中...");
        mAppDataManager.uploadPic(partList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<UploadPicBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<UploadPicBean> response) {

                        getView().onPicSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateHead(String head) {
        mAppDataManager.updateHead(head)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        Toast.makeText(App.getAppContext(), "更换头像成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
