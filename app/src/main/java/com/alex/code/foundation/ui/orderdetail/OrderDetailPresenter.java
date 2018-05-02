package com.alex.code.foundation.ui.orderdetail;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.MD5;
import com.alex.code.foundation.utils.VLog;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

class OrderDetailPresenter extends BaseMvpPresenter<OrderDetailView>{

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public OrderDetailPresenter() {
    }

    public void getOrderDetail(String order_num) {
        mAppDataManager.getOrderDetail(order_num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<OrderDetailBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<OrderDetailBean> response) {

                        getView().onSuccess(response.getData());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void remindDelivery(String order_no) {
        mAppDataManager.remindDelivery(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showRemindDelivery();
                    }
                });
    }

    public void extendReceive(String order_no) {
        mAppDataManager.extendReceive(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showExtendReceive();
                    }
                });
    }

    public void confirmReceive(String order_no) {

        mAppDataManager.confirmReceive(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showConfirmReceive();
                    }
                });
    }

    public void cancelOrder(String order_no) {

        mAppDataManager.cancelOrder(order_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>(getView()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {

                        getView().showCancelOrder();
                    }
                });
    }

    public void getLogisticsInfo(String express_code, String express_no, String from_area, String to_area) {
        String url = "http://poll.kuaidi100.com/poll/query.do";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("com",express_code);
        hashMap.put("num",express_no);
        hashMap.put("from",from_area);
        hashMap.put("to",to_area);
        hashMap.put("resultv2","1");

        Gson gson = new Gson();
        String param = gson.toJson(hashMap);
        VLog.d("param: "+param);

//        String param = "{\"com\":\"shunfeng\",\"num\":\"289125631549\",\"from\":\"四川成都\",\"to\":\"四川成都\",\"resultv2\":\"1\"}";
        String customer = "0DC88D0363990780434626CEA2665F3C";
        String key = "HRlsapyy2599";
        //        String sign = CommonUtils.md5Encode(param+key+customer);
        String sign = MD5.encode(param+key+customer);

        VLog.d("sign: "+sign + "---  str:  "+ param+key+customer);
        addDisposable(mAppDataManager.getLogisticsInfo(url,param,sign,customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logisticsBean -> {

                    getView().showLogisticsInfo(logisticsBean);
                },throwable -> {

                }));

    }
}
