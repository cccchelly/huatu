package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/22.
 */

public class AlipayBean {


    /**
     * sign : alipay_sdk=alipay-sdk-php-20161101&app_id=2017020905594136&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%22151134197388271000%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%222400.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fjht.meishifulu.cn%2Fapp%2FOrderpay%2FalipayNotify&sign_type=RSA2&timestamp=2017-11-22+17%3A29%3A18&version=1.0&sign=MaijNyeS1zsbGY7MKtkA90LGae6rMBToRIBt07vZAFyfnA3Swf%2FGaBVgvV78ZV6jC5VsIwQ4l29MA5bjU08Bv7P8YxlTvyMvYBqoK114q0JvvF2KJYC5XeSBdME3CKNVFvBq3WDX8BfSF7dK3IyJ2VkBUBF93luDPXMa5jyzAVm%2BXw136%2BaADH68XKbFyOU6T0iI3vBOBTt9DMbHeyWfJfagv7kf84QcyWN%2BmuHh7k6a7MyMeopfRJUPKN%2FRRYkTpzotOs2l7Ciy5Wi855O6rW4IJy8fQ1RtXbGO8ZhtI%2B0Y7w1%2FqmT1VRFmmlRP9GKXlPG3my%2Bz%2FgzognrIGFQZrA%3D%3D
     * debug_uid : 304
     * debug_way : mobile
     * session : []
     * requieLogin : false
     */

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
