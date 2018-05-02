package com.alex.code.foundation.ui.shopgoods;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.TotalShopGoodsBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

class ShopGoodsPresenter extends BaseMvpPresenter<ShopGoodsView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ShopGoodsPresenter() {
    }

    public void getTotalShopGoods(String shop_id, String condition, String sort, String page_index, String page_size) {
        mAppDataManager.getTotalShopGoods(shop_id,condition,sort,page_index,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<TotalShopGoodsBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<TotalShopGoodsBean> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
