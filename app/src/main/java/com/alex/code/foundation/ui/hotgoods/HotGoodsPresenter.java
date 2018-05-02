package com.alex.code.foundation.ui.hotgoods;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

class HotGoodsPresenter extends BaseMvpPresenter<HotGoodsView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public HotGoodsPresenter() {
    }

    public void getHotGoods(String sort,String page_index,String page_size) {
        mAppDataManager.getHotGoods(sort,page_index,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<HotGoodsBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<HotGoodsBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
