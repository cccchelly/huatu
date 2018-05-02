package com.alex.code.foundation.ui.logistics;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.MD5;
import com.alex.code.foundation.utils.VLog;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

class LogisticsPresenter extends BaseMvpPresenter<LogisticsView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public LogisticsPresenter() {
    }

    public void getLogisticsInfo(String express_code, String express_no, String from_area, String to_area) {
        getView().showLoading("加载中...");
        String url = "http://poll.kuaidi100.com/poll/query.do";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("com",express_code);
        hashMap.put("num",express_no);
        hashMap.put("from",from_area);
        hashMap.put("to",to_area);
        hashMap.put("resultv2","1");

        Gson gson = new Gson();
        String param = gson.toJson(hashMap);

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

                    getView().dismissLoading();
                    getView().onSuccess(logisticsBean);
                },throwable -> {
                    getView().dismissLoading();
                }));

    }

    public void getLogisticsInfo() {
        String url = "http://poll.kuaidi100.com/poll/query.do";
        //        String param = "{\"com\":\"yuantong\",\"num\":\"12345678\",\"from\":\"广东深圳\",\"to\":\"北京朝阳\",\"resultv2\":\"1\"}";
        String param = "{\"com\":\"shunfeng\",\"num\":\"289125631549\",\"from\":\"四川成都\",\"to\":\"四川成都\",\"resultv2\":\"1\"}";
        String customer = "0DC88D0363990780434626CEA2665F3C";
        String key = "HRlsapyy2599";
        //        String sign = CommonUtils.md5Encode(param+key+customer);
        String sign = MD5.encode(param+key+customer);

        VLog.d("sign: "+sign + "---  str:  "+ param+key+customer);
        getView().showLoading("加载中...");
        addDisposable(mAppDataManager.getLogisticsInfo(url,param,sign,customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(logisticsBean -> {

                    getView().dismissLoading();
                    getView().onSuccess(logisticsBean);
                },throwable -> {
                    getView().dismissLoading();
                }));


    }
}
