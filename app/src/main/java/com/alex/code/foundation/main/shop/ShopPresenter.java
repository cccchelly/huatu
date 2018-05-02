package com.alex.code.foundation.main.shop;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.CartShopBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.bean.ShopCartBean;
import com.alex.code.foundation.data.AppDataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class ShopPresenter extends BaseMvpPresenter<ShopView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public ShopPresenter() {
    }

    public void getShopCartList() {
        getView().showLoading("加载中...");
        mAppDataManager.getShopCartList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopCartBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ShopCartBean> response) {

                        List<CartShopBean> data = response.getData().getData();
                        getView().onSuccess(data);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void deleteCartGoods(String... cart_ids) {

        mAppDataManager.deleteCartGoods(cart_ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopCartBean>>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse<ShopCartBean> response) {

                        List<CartShopBean> data = response.getData().getData();
                        getView().onSuccess(data);
                    }
                });
    }

    public void  updateCartNum(String cart_id, String goods_num , int groupPosition, int childPosition) {
        mAppDataManager.updateCartNum(cart_id, goods_num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopCartBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ShopCartBean> response) {

                        List<CartShopBean> data = response.getData().getData();
                        getView().showChangeNum(data,groupPosition,childPosition);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void confirmOrderList(String is_cart, String cart_ids, String goods_id, String goods_num,String sku_id) {

        getView().showLoading("提交订单中...");
        mAppDataManager.confirmOrderList(is_cart, cart_ids, goods_num, goods_num,sku_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<AffirmOrderBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<AffirmOrderBean> response) {

                        getView().showOrderDetail(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void getGoodSpec(String goods_id) {
        mAppDataManager.getGoodSpec(goods_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<GoodSpeBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<GoodSpeBean> response) {

                        getView().showGoodSpec(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void updateGoodsSpe(String cart_id,String sku_id,int groupPosition, int childPosition){
        mAppDataManager.updateGoodsSpe(cart_id, sku_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<ShopCartBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<ShopCartBean> response) {

                        List<CartShopBean> data = response.getData().getData();
                        getView().showChangeSpe(data,groupPosition,childPosition);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
