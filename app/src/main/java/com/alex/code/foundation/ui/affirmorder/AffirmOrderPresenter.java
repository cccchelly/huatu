package com.alex.code.foundation.ui.affirmorder;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.CreateOrderBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

class AffirmOrderPresenter extends BaseMvpPresenter<AffirmOrderView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public AffirmOrderPresenter() {
    }

    public void createOrder(String is_cart,String phone,String address,String user_name,String cart_ids,
                            String shop_id,String goods_id,String goods_num,String sku_id,
                            String shipping_money,String message,String goods_money){

        getView().showLoading("生成订单中...");
        mAppDataManager.createOrder(is_cart,phone,address,user_name,cart_ids,shop_id,
                goods_id,goods_num,sku_id,shipping_money,message,goods_money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<CreateOrderBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<CreateOrderBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
