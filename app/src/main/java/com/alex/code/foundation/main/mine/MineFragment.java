package com.alex.code.foundation.main.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.UserInfo;
import com.alex.code.foundation.utils.um.Tracker;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class MineFragment extends BaseMvpFragment<MineView, MinePresenter> implements MineView {
    @BindView(R.id.tv_safety)
    TextView         mTvSafety;
    @BindView(R.id.tv_address)
    TextView         mTvAddress;
    @BindView(R.id.tv_setting)
    TextView         mTvSetting;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.username)
    TextView         mUsername;
    @BindView(R.id.tv_favorite)
    TextView         mTvFavorite;
    @BindView(R.id.tv_focus)
    TextView         mTvFocus;
    @BindView(R.id.tv_footprint)
    TextView         mTvFootprint;
    @BindView(R.id.tv_charge_msg)
    TextView         mTvChargeMsg;
    @BindView(R.id.tv_get_msg)
    TextView         mTvGetMsg;
    @BindView(R.id.tv_wait_msg)
    TextView         mTvWaitMsg;
    @BindView(R.id.tv_comment_msg)
    TextView         mTvCommentMsg;
    @BindView(R.id.tv_refund_msg)
    TextView         mTvRefundMsg;
    private String mMobile    = "";
    private String mNick_name = "";
    private String mEmail;
    private int mUser_email_bind;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {


    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("个人中心");
        prepareFetchData(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("个人中心");
    }

    @Override
    public void fetchData() {
        getPresenter().getUserInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isDataInitiated = false; //当前页面可见时强制刷新数据
        super.setUserVisibleHint(isVisibleToUser);
    }

    @OnClick({R.id.tv_safety, R.id.tv_address, R.id.tv_setting, R.id.rl_userinfo,
            R.id.ll_wait_pay, R.id.ll_wait_send, R.id.ll_wait_receive, R.id.ll_wait_comment,
            R.id.ll_favorite, R.id.ll_focus, R.id.ll_footprint, R.id.tv_all_order, R.id.ll_refund,
            R.id.tv_statistics,R.id.iv_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_msg:
                ARouter.getInstance().build("/foundation/notice").navigation();
                break;
            case R.id.tv_statistics://统计报表
                ARouter.getInstance().build("/foundation/statistics").navigation();
                break;
            case R.id.tv_safety://账户安全
                ARouter.getInstance().build("/foundation/safety")
                        .withString("mobile", mMobile)
                        .withString("nickName", mNick_name)
                        .withString("email", mEmail)
                        .withInt("emailBind", mUser_email_bind)
                        .navigation();
                break;
            case R.id.tv_address://收货地址
                ARouter.getInstance().build("/foundation/address").navigation();
                break;
            case R.id.tv_setting://设置
                ARouter.getInstance().build("/foundation/setting").navigation();
                break;
            case R.id.rl_userinfo://个人信息
                ARouter.getInstance().build("/foundation/userinfo").navigation();
                break;
            case R.id.ll_favorite://商品收藏
                ARouter.getInstance().build("/foundation/favorite").navigation();
                break;
            case R.id.ll_focus://我的关注
                ARouter.getInstance().build("/foundation/focus").navigation();
                break;
            case R.id.ll_footprint://我的足迹
                ARouter.getInstance().build("/foundation/footprint").navigation();
                break;
            case R.id.ll_wait_pay://待支付
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type", 1)
                        .navigation();
                break;
            case R.id.ll_wait_send://待发货
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type", 2)
                        .navigation();
                break;
            case R.id.ll_wait_receive://待收货
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type", 3)
                        .navigation();
                break;
            case R.id.ll_wait_comment://待评价
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type", 4)
                        .navigation();
                break;
            case R.id.ll_refund://退款售后
                ARouter.getInstance().build("/foundation/refunded").navigation();
                break;
            case R.id.tv_all_order://全部订单 
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type", 0)
                        .navigation();
                break;
            default:
        }
    }

    @Override
    public void onSuccess(UserInfo userInfo) {

        if (userInfo != null && userInfo.getMember_info() != null) {
            UserInfo.MemberInfoEntity member_info = userInfo.getMember_info();
            mMobile = member_info.getMobile();
            mNick_name = member_info.getNick_name();
            mEmail = member_info.getEmail();
            mUser_email_bind = member_info.getUser_email_bind();
            mUsername.setText(mNick_name);
            mSdvHead.setImageURI(AppConstants.PIC_BASE_URL + member_info.getAvatar());

            mTvFavorite.setText(member_info.getFavorites_goods());
            mTvFocus.setText(member_info.getFavorites_shop());
            mTvFootprint.setText(member_info.getHistory());

            if (!TextUtils.equals(member_info.getWait_pay(), "0")) {//待付款
                mTvChargeMsg.setVisibility(View.VISIBLE);
                mTvChargeMsg.setText(member_info.getWait_pay());
            } else {
                mTvChargeMsg.setVisibility(View.GONE);
            }

            if (!TextUtils.equals(member_info.getWait_delivery(), "0")) {//待发货
                mTvGetMsg.setVisibility(View.VISIBLE);
                mTvGetMsg.setText(member_info.getWait_delivery());
            }else {
                mTvGetMsg.setVisibility(View.GONE);
            }

            if (!TextUtils.equals(member_info.getWait_receive(), "0")) {//待收货
                mTvWaitMsg.setVisibility(View.VISIBLE);
                mTvWaitMsg.setText(member_info.getWait_receive());
            }else {
                mTvWaitMsg.setVisibility(View.GONE);
            }

            if (!TextUtils.equals(member_info.getWait_comment(), "0")) {//待评价
                mTvCommentMsg.setVisibility(View.VISIBLE);
                mTvCommentMsg.setText(member_info.getWait_comment());
            }else {
                mTvCommentMsg.setVisibility(View.GONE);
            }

            if (!TextUtils.equals(member_info.getWait_comment(), "0")) {//退款
                mTvRefundMsg.setVisibility(View.VISIBLE);
                mTvRefundMsg.setText(member_info.getRefund_or_sale());
            }else {
                mTvRefundMsg.setVisibility(View.GONE);
            }


        }
    }

}
