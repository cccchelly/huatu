package com.alex.code.foundation.base;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.utils.VLog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import retrofit2.HttpException;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/11.
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {

    private BaseMvpView mBaseMvpView;

    public BaseObserver(BaseMvpView baseMvpView) {
        mBaseMvpView = baseMvpView;
    }

    @Override
    public void onNext(@NonNull T response) {

        VLog.d("onNext: %s", response);
        switch (response.getCode()) {
            case BaseResponse.RESULT_CODE_SUCCESS:
                onSuccess(response);
                break;
            case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:
                Log.i("token_err","token_err");
                ARouter.getInstance().build("/foundation/login").withInt("main",1000).navigation();
                break;
            default:
                onDataFailure(response);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

        //异常处理，需要自己实现
        VLog.e("onError: "+e.toString());
        mBaseMvpView.dismissLoading();
        handleError(e,mBaseMvpView);
    }

    @Override
    public void onComplete() {

        mBaseMvpView.dismissLoading();
    }

    public abstract void onSuccess(T response);

    /**
     * 对api返回的错误状态的处理 需要时自己实现
     * @param response
     */
    protected void onDataFailure(T response) {
        String msg = response.getMsg();
        VLog.w("request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
//            mBaseMvpView.showException(response.getMsg());
            Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
//            mBaseMvpView.showException("未知错误");
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, BaseMvpView iBaseView) {
        if (throwable == null) {
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
            return;
        }
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException
                || throwable instanceof UnknownHostException
                || throwable instanceof IOException) {
            Toast.makeText(App.getAppContext(), "网络异常", Toast.LENGTH_SHORT).show();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            Toast.makeText(App.getAppContext(), "数据解析异常", Toast.LENGTH_SHORT).show();
        } else if ((throwable instanceof HttpException)) {
            //自动上报这个异常
            Toast.makeText(App.getAppContext(), "服务器错误"+((HttpException) throwable).code(), Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof NullPointerException) {
            //自动上报这个异常
            Toast.makeText(App.getAppContext(), "客户端异常"+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getAppContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
}
