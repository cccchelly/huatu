package com.alex.code.foundation.ui.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.utils.um.UMEventId;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

@Route(path = "/foundation/Pay")
public class PayActivity extends BaseMvpActivity<PayView, PayPresenter> implements PayView{
    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tv_price)
    TextView      mTvPrice;
    @BindView(R.id.radioGroup)
    RadioGroup    mRadioGroup;
    @BindView(R.id.rb_zhifubao)
    RadioButton   mRbZhifubao;
    @BindView(R.id.rb_weixin)
    RadioButton   mRbWeixin;
    @BindView(R.id.rb_bank)
    RadioButton   mRbBank;
    @BindView(R.id.tv_pay)
    TextView      mTvPay;

    @Autowired
    public String orderNum;
    @Autowired
    public String totalPrice;
    @Inject
    ToastInstance mToastInstance;

    private String mPayWay = "ALIPAY";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);

        mToolbar.setTitle("确认支付")
                .setLeftBackListener(this::finish);

        mTvPrice.setText("￥"+totalPrice);

        initListener();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_zhifubao:
                    mPayWay = "ALIPAY";
                    break;
                case R.id.rb_weixin:
                    mPayWay = "WXPAY";
                    break;
                case R.id.rb_bank:
                    mPayWay = "UNIONPAY";
                    break;
            }
        });
    }

    @OnClick(R.id.tv_pay)
    public void onViewClicked() {
        Tracker.getDefaultTracker().trackEvent(UMEventId.PAY_ORDER);
        switch (mPayWay) {
            case "ALIPAY":
                getPresenter().performAliPay(orderNum,mPayWay,this);
                break;
            case "WXPAY":
                getPresenter().performWechatPay(orderNum,mPayWay);
                break;
            case "UNIONPAY":
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (TextUtils.equals(event.getMsg(), EventCons.WX_PAY_SUCCESS)) {
            mToastInstance.showToast("支付成功");
            ARouter.getInstance().build("/foundation/paySuccess")
                    .withString("orderNum",orderNum)
                    .navigation();
            finish();

        } else if (TextUtils.equals(event.getMsg(), EventCons.WX_PAY_FAIL)) {
            mToastInstance.showToast("支付失败");
        }

    }

    @Override
    public void onAliPaySuccess() {
        ARouter.getInstance().build("/foundation/paySuccess")
                .withString("orderNum",orderNum)
                .navigation();
        finish();
    }
}
