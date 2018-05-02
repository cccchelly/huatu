package com.alex.code.foundation.ui.pay;

import android.app.Activity;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.AlipayBean;
import com.alex.code.foundation.bean.PayResult;
import com.alex.code.foundation.bean.WxpayInfo;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

class PayPresenter extends BaseMvpPresenter<PayView> {

    @Inject
    AppDataManager mAppDataManager;
    @Inject
    ToastInstance mToastInstance;

    private IWXAPI msgApi;


    @Inject
    public PayPresenter() {
        msgApi = WXAPIFactory.createWXAPI(App.getAppContext(), null);
        msgApi.registerApp(AppConstants.WX_APPID);
    }

//            "appId":"wxfe9ec39bdab4bc87",
//            "partnerId":"1440684402",
//            "prepayId":"wx20171115103326fe3723fe8d0711277113",
//            "packageValue":"Sign=WXPay",
//            "nonceStr":"hy4ct181mxcm2xjkkr833dz9liypi46x",
//            "timeStamp":"1510713202",
//            "Sign":"6C1A10A25430638E9A23383D73D9A1BC"


    public void performWechatPay(String order_no, String payment) {
        getView().showLoading("支付中...");
        mAppDataManager.performWechatPay(order_no,payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<WxpayInfo>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<WxpayInfo> response) {
                        WxpayInfo wxpayInfo = response.getData();
                        PayReq req = new PayReq();
                        req.appId = wxpayInfo.getAppid();
                        req.partnerId = wxpayInfo.getPartnerid();
                        req.prepayId = wxpayInfo.getPrepayid();
                        req.nonceStr = wxpayInfo.getNoncestr();
                        req.timeStamp = wxpayInfo.getTimestamp();
                        req.packageValue = wxpayInfo.getPackagee();
                        req.sign = wxpayInfo.getSign();
                        msgApi.sendReq(req);

                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }

    public void performAliPay(String order_no, String payment, Activity activity){
        mAppDataManager.performAliPay(order_no,payment)
                .map(new Function<BaseResponse<AlipayBean>, PayResult>() {
                    @Override
                    public PayResult apply(@NonNull BaseResponse<AlipayBean> stringBaseResponse) throws Exception {
                        String order = stringBaseResponse.getData().getSign();
                        VLog.d("original data %s", order);
                        PayTask payTask = new PayTask(activity);
                        VLog.i("Alipay Sdk version: %s", payTask.getVersion());
                        Map<String, String> result = payTask.payV2(order, true);

                        return new PayResult(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayResult>() {
                    @Override
                    public void accept(PayResult payResult) throws Exception {

                        VLog.d("payResult.getResultStatus(): "+payResult.getResultStatus());

                        switch (payResult.getResultStatus()) {
                            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                            case "9000":
                                // 先检查服务器是否到账，然后刷新数据
//                                checkAlipayState(result.getResult());
                                mToastInstance.showToast("支付成功");
                                getView().onAliPaySuccess();
                                //刷新列表和余额
                                break;

                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            case "8000":
//                                getUiInterface().showRechargeProcessing();
                                //刷新列表和余额
//                                loadRechargeMap();
                                break;

                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            case "6001":
//                                getUiInterface().showPayCancelled();
                                mToastInstance.showToast("支付取消");
                                break;

                            case "6002":
                                mToastInstance.showToast("网络异常");
//                                getUiInterface().showNetworkException();
                                break;
                            default:
                                mToastInstance.showToast("支付失败");
//                                getUiInterface().showRechargeFailed(payResult.getResultStatus(),
//                                        result.getMemo());
                                break;
                        }
                    }
                },throwable -> {

                });
    }
}
