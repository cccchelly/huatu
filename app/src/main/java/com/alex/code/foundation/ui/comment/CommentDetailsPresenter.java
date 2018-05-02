package com.alex.code.foundation.ui.comment;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.CommentDetailBean;
import com.alex.code.foundation.bean.ReplyBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/1.
 */

class CommentDetailsPresenter extends BaseMvpPresenter<CommentDetailsView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public CommentDetailsPresenter() {
    }

    public void getCommentDetail(String id) {
        getView().showLoading("加载中...");
        mAppDataManager.getCommentDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<CommentDetailBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<CommentDetailBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void getReplyList(String id, String content) {
        mAppDataManager.getReplyList(id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ReplyBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ReplyBean> response) {

                        getView().showReplyList(response.getData().getReply());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
