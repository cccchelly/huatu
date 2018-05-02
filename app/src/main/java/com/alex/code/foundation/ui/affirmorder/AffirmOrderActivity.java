package com.alex.code.foundation.ui.affirmorder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.AddressBean;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.CreateOrderBean;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dth
 * Des:
 * Date: 2017/11/14.
 */

@Route(path = "/foundation/affirmOrder")
public class AffirmOrderActivity extends BaseMvpActivity<AffirmOrderView, AffirmOrderPresenter> implements AffirmOrderView{

    @BindView(R.id.toolbar)
    CustomToolBar  mToolbar;
    @BindView(R.id.tv_total_price)
    TextView       mTvTotalPrice;
    @BindView(R.id.tv_commit)
    TextView       mTvCommit;
    @BindView(R.id.iv_right)
    ImageView      mIvRight;
    @BindView(R.id.tv_consignee)
    TextView       mTvConsignee;
    @BindView(R.id.tv_phone)
    TextView       mTvPhone;
    @BindView(R.id.tv_address)
    TextView       mTvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout mRlAddress;
    @BindView(R.id.recyclerview)
    RecyclerView   mRecyclerview;
    @BindView(R.id.tv_express)
    TextView       mTvExpress;
    @BindView(R.id.tv_activity)
    TextView       mTvActivity;
    @BindView(R.id.et_remark)
    EditText       mEtRemark;

    @Autowired
    public AffirmOrderBean affirmOrderBean;
    @Autowired
    public Double totalPrice;
    @Autowired
    public int is_cart;
    @Autowired
    public String cart_ids;

    @Inject
    ToastInstance mToastInstance;


    private AffirmOrderAdapter mAffirmOrderAdapter;
    ArrayList<AffirmOrderBean.DataEntity.ListEntity> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_affirm_order;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("确认订单")
                .setLeftBackListener(this::finish);
        ARouter.getInstance().inject(this);

        initRecyclerView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("确认订单");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("确认订单");
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

    private void initRecyclerView() {
        mAffirmOrderAdapter = new AffirmOrderAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
        mRecyclerview.setAdapter(mAffirmOrderAdapter);
    }

    private void initData() {

        mData.clear();
//        VLog.d("totalPrice: "+totalPrice + " getShipping_money: "+affirmOrderBean.getShipping_money() +"  affirmOrderBean: "+affirmOrderBean.getAddress() + "data: "+affirmOrderBean.getData());
        if (affirmOrderBean != null) {

            AffirmOrderBean.AddressEntity address = affirmOrderBean.getAddress();
            if (TextUtils.isEmpty(address.getConsigner())) {
                mTvConsignee.setText("请选择收货地址");
            } else {

                mTvConsignee.setText(address.getConsigner());
                mTvPhone.setText(address.getMobile());
                String addressDetail = address.getProvince() + address.getCity() + address.getDistrict() +"  " +address.getAddress();
                mTvAddress.setText(addressDetail);
            }
            mTvExpress.setText(affirmOrderBean.getShipping_money()+"");
            mTvActivity.setText(affirmOrderBean.getCoupon()+"元");
            VLog.d("totalPrice: "+totalPrice +"  affirmOrderBean.getGoods_money(): "+affirmOrderBean.getGoods_money());
            totalPrice = affirmOrderBean.getGoods_money() + affirmOrderBean.getShipping_money();
            CommonUtils.setTwoTextColor("总价: "+"￥"+totalPrice,"￥"+totalPrice, Color.RED,mTvTotalPrice);

            List<AffirmOrderBean.DataEntity> shopData = affirmOrderBean.getData();
            for (AffirmOrderBean.DataEntity goodsData : shopData) {
                mData.addAll(goodsData.getList());
            }

            mAffirmOrderAdapter.replaceData(mData);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<AddressBean.DataEntity> event) {

        if (TextUtils.equals(event.getMsg(), EventCons.ADDRESS_SELECT)) {
            AddressBean.DataEntity data = event.getData();
            String addressDetail = data.getProvince() + data.getCity() + data.getDistrict() +"  " +data.getAddress();
            mTvConsignee.setText(data.getConsigner());
            mTvPhone.setText(data.getMobile());
            mTvAddress.setText(addressDetail);
        }

    }

    @OnClick({R.id.tv_commit, R.id.rl_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                if (TextUtils.isEmpty(mTvPhone.getText().toString())  || TextUtils.isEmpty(mTvAddress.getText().toString()) || TextUtils.isEmpty(mTvConsignee.getText().toString())) {
                    mToastInstance.showToast("请选择收货地址");
                    return;
                }
                if (is_cart == 1) {//购物车购买

                    getPresenter().createOrder(is_cart+"",mTvPhone.getText().toString(),mTvAddress.getText().toString(),mTvConsignee.getText().toString(),cart_ids
                            ,"","","","",affirmOrderBean.getShipping_money()+"",mEtRemark.getText().toString(),affirmOrderBean.getGoods_money()+"");

                } else {//直接购买
                    AffirmOrderBean.DataEntity dataEntity = affirmOrderBean.getData().get(0);
                    AffirmOrderBean.DataEntity.ListEntity listEntity = dataEntity.getList().get(0);
                    getPresenter().createOrder(is_cart+"",mTvPhone.getText().toString(),mTvAddress.getText().toString(),mTvConsignee.getText().toString()
                            ,"",listEntity.getShop_id()+"",listEntity.getGoods_id()+"",listEntity.getNum()+"",listEntity.getSku_id()+"",affirmOrderBean.getShipping_money()+""
                            ,mEtRemark.getText().toString(),affirmOrderBean.getGoods_money()+"");
                }
                break;
            case R.id.rl_address:
                ARouter.getInstance().build("/foundation/address")
                        .withInt("action",100)
                        .navigation();
                break;
        }
    }

    @Override
    public void onSuccess(CreateOrderBean createOrderBean) {
        ARouter.getInstance().build("/foundation/Pay")
                .withString("orderNum",createOrderBean.getOrder_no())
                .withString("totalPrice",createOrderBean.getPay_money())
                .navigation();
        finish();
    }

    class AffirmOrderAdapter extends BaseQuickAdapter<AffirmOrderBean.DataEntity.ListEntity, BaseViewHolder> {

        public AffirmOrderAdapter() {
            super(R.layout.item_affirm_order);
        }

        @Override
        protected void convert(BaseViewHolder helper, AffirmOrderBean.DataEntity.ListEntity item) {

            int position = helper.getAdapterPosition();
            if (position == 0 || !TextUtils.equals(mData.get(position).getShop_name(), mData.get(position - 1).getShop_name())) {
                helper.setGone(R.id.tv_shop_name, true)
                        .setText(R.id.tv_shop_name, item.getShop_name());
            } else {
                helper.setGone(R.id.tv_shop_name, false)
                        .setText(R.id.tv_shop_name, item.getShop_name());
            }

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getPic_cover_mid());
            helper.setText(R.id.tv_goods_des, item.getIntroduction())
                    .setText(R.id.tv_attr, item.getSpec_name())
                    .setText(R.id.tv_price, " 单价: "+item.getPrice())
                    .setText(R.id.tv_num, "数量: "+item.getNum());
        }
    }

}
