package com.alex.code.foundation.ui.refund;

import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomPopupWindow;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/21.
 */

@Route(path = "/foundation/refund")
public class RefundActivity extends BaseMvpActivity<RefundView, RefundPresenter> implements RefundView{

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tv_reason)
    TextView      mTvReason;
    @BindView(R.id.et_phone)
    EditText      mEtPhone;
    @BindView(R.id.et_num)
    EditText      mEtNum;
    @BindView(R.id.et_sum)
    TextView      mEtSum;
    @BindView(R.id.et_refund_des)
    EditText      mEtRefundDes;
    @BindView(R.id.tv_commit)
    TextView      mTvCommit;
    @BindView(R.id.ll_num)
    LinearLayout  mLlNum;

    @Autowired
    public String phone;
    @Autowired
    public OrderDetailBean.GoodsListEntity goodsBean;
    @Autowired
    public String order_no;
    @Autowired
    public String refundMoney;
    @Autowired
    public int type;//1.退单个商品,2.退订单
    private CustomPopupWindow mPopupWindow;

    @Inject
    ToastInstance mToastInstance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("申请退款")
                .setLeftBackListener(this::finish);

        mPopupWindow = CustomPopupWindow.builder()
                .contentView(CustomPopupWindow.inflateView(this, R.layout.popupwindow_refund_reason))
                .isOutsideTouch(true)
                .isWrap(true,true)
                .backgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)))
                .customListener(contentView -> initPop(contentView))
                .build();
        initData();
    }

    private void initPop(View contentView) {
        contentView.findViewById(R.id.tv_oos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvReason.setText("缺货");
                mPopupWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.tv_give_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvReason.setText("不想要了");
                mPopupWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvReason.setText("重新拍");
                mPopupWindow.dismiss();

            }
        });

        contentView.findViewById(R.id.tv_lose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvReason.setText("未收到货");
                mPopupWindow.dismiss();
            }
        });

        contentView.findViewById(R.id.tv_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTvReason.setText("与商家协议退款");
                mPopupWindow.dismiss();
            }
        });
    }

    private void initData() {
        mEtPhone.setText(phone);
        if (type == 1) {

            if (goodsBean != null) {
                mEtSum.setText(goodsBean.getGoods_money());
                mEtNum.setText(goodsBean.getNum());
            }
        } else if (type == 2) {

            mEtSum.setText(refundMoney);
            mLlNum.setVisibility(View.GONE);

        }

        mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (!TextUtils.isEmpty(s)) {


                        String str = s.toString();
                        String str2 = goodsBean.getPrice();
                        String str3 = goodsBean.getNum();

                        int totalNum = Integer.parseInt(str3);
                        int num = Integer.parseInt(str);
                        double  price = Double.parseDouble(str2);

                        if (num <= 0) {
                            num = 1;
                            mToastInstance.showToast("商品数量至少为1个");
                        } else if (num > totalNum) {
                            num = totalNum;
                            mToastInstance.showToast("商品数量不能超过"+num+"个");
                        }


                        mEtSum.setText((num * price) +"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.tv_reason, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reason:

                mPopupWindow.showAsDropDown(mTvReason);

                break;
            case R.id.tv_commit:
                if (type == 1) {
                    String num = mEtNum.getText().toString();
                    int intNum = Integer.parseInt(num);
                    if (intNum <= 0) {
                        mToastInstance.showToast("商品数量至少为1个");
                        return;
                    }
                    if (intNum > Integer.parseInt(goodsBean.getNum())) {
                        mToastInstance.showToast("商品数量不能超过"+goodsBean.getNum()+"个");
                        return;
                    }
                    getPresenter().postRefund(mTvReason.getText().toString(),mEtPhone.getText().toString(),mEtSum.getText().toString(),mEtRefundDes.getText().toString(),order_no,goodsBean.getOrder_goods_id()+"", num);
                } else if (type == 2) {
                    getPresenter().postOrderRefund(mTvReason.getText().toString(),mEtPhone.getText().toString(),mEtSum.getText().toString(),mEtRefundDes.getText().toString(),order_no);
                }
                break;
        }
    }

    @Override
    public void onSuccess() {
        mToastInstance.showToast("申请退款成功");
        finish();
    }
}
