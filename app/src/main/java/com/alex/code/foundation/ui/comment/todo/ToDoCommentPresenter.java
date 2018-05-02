package com.alex.code.foundation.ui.comment.todo;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.TodoCommentBean;
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
 * Date: 2017/11/17.
 */

class ToDoCommentPresenter extends BaseMvpPresenter<ToDoCommentView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ToDoCommentPresenter() {
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

    public void postComment(String goods_id, String order_id, String order_goods_id,String content,
                            String anon, String explain_type,
                            String scores, String physical,
                            String image) {

        mAppDataManager.postComment(goods_id,order_id,order_goods_id,content,anon,explain_type,scores,physical,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<TodoCommentBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<TodoCommentBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });

    }
}
