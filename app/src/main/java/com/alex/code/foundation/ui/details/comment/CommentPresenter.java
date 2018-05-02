package com.alex.code.foundation.ui.details.comment;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.CommentBean;
import com.alex.code.foundation.bean.CommentTypeBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

class CommentPresenter extends BaseMvpPresenter<CommentView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public CommentPresenter() {
    }

    public void getGoodsComment(String id, String type, String pageIndex, String pageSize) {

        mAppDataManager.getGoodsComment(id,type,pageIndex,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<CommentBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<CommentBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void getGoodsCommentType(String id) {
        mAppDataManager.getGoodsCommentType(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<CommentTypeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<CommentTypeBean> response) {
                        getView().showCommentType(response.getData().getEval_count());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

}
