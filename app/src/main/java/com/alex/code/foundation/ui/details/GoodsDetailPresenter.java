package com.alex.code.foundation.ui.details;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.AtteGoodsBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.ToastInstance;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/26.
 */

class GoodsDetailPresenter extends BaseMvpPresenter<GoodsDetailView>{

    @Inject
    AppDataManager mAppDataManager;
    @Inject
    ToastInstance mToastInstance;

    @Inject
    public GoodsDetailPresenter() {
    }

    public void getGoodSpec(String goods_id) {
        mAppDataManager.getGoodSpec(goods_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<GoodSpeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<GoodSpeBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void addShopCart(String goods_id, String goods_num, String sku_id) {
        mAppDataManager.addShopCart(goods_id,goods_num,sku_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().showAddShopCart();
                    }
                });
    }

    public void confirmOrderList(String is_cart, String cart_ids, String goods_id, String goods_num,String sku_id) {
        mAppDataManager.confirmOrderList(is_cart, cart_ids, goods_id, goods_num,sku_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<AffirmOrderBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<AffirmOrderBean> response) {

                        getView().showAffirmOrder(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateGoodsFocus(String id, String is_fav) {
        mAppDataManager.updateGoodsFocus(id, is_fav)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<AtteGoodsBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<AtteGoodsBean> response) {
                        getView().updateAtteGoods(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
