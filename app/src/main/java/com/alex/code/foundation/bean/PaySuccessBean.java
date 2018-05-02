package com.alex.code.foundation.bean;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/8.
 */

public class PaySuccessBean {

    /**
     * "payment_type":"WXPAY",
     * "pay_money":"1420.00",
     * "coupon_money":"0.00"
     */

    private String payment_type;
    private String pay_money;
    private String coupon_money;

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }
}
