package com.alex.code.foundation.ui.paysuccess;

import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.PaySuccessBean;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/8.
 */

@Route(path = "/foundation/paySuccess")
public class PaySuccessActivity extends BaseMvpActivity<PaySuccessView, PaySuccessPresenter> implements PaySuccessView{
    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tv_pay_way)
    TextView      mTvPayWay;
    @BindView(R.id.tv_pay_money)
    TextView      mTvPayMoney;
    @BindView(R.id.tv_discount)
    TextView      mTvDiscount;
    @BindView(R.id.tv_go_to_order)
    TextView      mTvGoToOrder;

    @Autowired
    public String orderNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        mToolbar.setTitle("订单支付成功")
                .setLeftBackListener(this::finish);

        getPresenter().getPaySuccess(orderNum);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("订单支付成功");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("订单支付成功");
    }

    @OnClick(R.id.tv_go_to_order)
    public void onViewClicked() {
        ARouter.getInstance().build("/foundation/order")
                .withInt("type",2)
                .navigation();
        finish();
    }

    @Override
    public void onSuccess(PaySuccessBean paySuccessBean) {
        if(paySuccessBean == null)return;

        switch (paySuccessBean.getPayment_type()) {
            case "ALIPAY":
                mTvPayWay.setText("支付宝");
                break;
            case "WXPAY":
                mTvPayWay.setText("微信支付");
                break;
            case "UNIONPAY":
                mTvPayWay.setText("银联支付");
                break;
            default:
                mTvPayWay.setText(paySuccessBean.getPayment_type());
        }
        mTvPayMoney.setText(paySuccessBean.getPay_money());
        mTvDiscount.setText(paySuccessBean.getCoupon_money());
    }
}
