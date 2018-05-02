package com.alex.code.foundation.ui.orderdetail;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ExpressEntity;
import com.alex.code.foundation.bean.LogisticsBean;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

@Route(path = "/foundation/orderDetail")
public class OrderDetailActivity extends BaseMvpActivity<OrderDetailView, OrderDetailPresenter> implements OrderDetailView {

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tv_order_status)
    TextView      mTvOrderStatus;
    @BindView(R.id.iv_logistics)
    ImageView     mIvLogistics;
    @BindView(R.id.tv_logistics)
    TextView      mTvLogistics;
    @BindView(R.id.tv_date)
    TextView      mTvDate;
    @BindView(R.id.tv_consignee)
    TextView      mTvConsignee;
    @BindView(R.id.tv_address)
    TextView      mTvAddress;
    @BindView(R.id.recyclerview)
    RecyclerView  mRecyclerview;
    @BindView(R.id.tv_order_num)
    TextView     mTvOrderNum;
    @BindView(R.id.tv_create_time)
    TextView     mTvCreateTime;
    @BindView(R.id.tv_pay_time)
    TextView     mTvPayTime;
    @BindView(R.id.tv_send_time)
    TextView     mTvSendTime;
    @BindView(R.id.tv_shop_name)
    TextView     mTvShopName;
    @BindView(R.id.tv_sum_price)
    TextView     mTvSumPrice;
    @BindView(R.id.tv_refund)
    TextView     mTvRefund;
    @BindView(R.id.tv_pay)
    TextView     mTvPay;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;

    @Autowired
    public String order_no;
    @Inject
    ToastInstance mToastInstance;


    private OrderDetailAdapter mOrderDetailAdapter;
    private OrderDetailBean    mOrderDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);

        mToolbar.setTitle("订单详情")
                .setLeftBackListener(this::finish);
        initRecyclerView();
        getPresenter().getOrderDetail(order_no);
//        getPresenter().getLogisticsInfo("shunfeng","289125631549","四川成都","四川成都");
    }

    private void initRecyclerView() {

        mOrderDetailAdapter = new OrderDetailAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
        mRecyclerview.setAdapter(mOrderDetailAdapter);
        mOrderDetailAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderDetailBean.GoodsListEntity goodsListEntity = mOrderDetailAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsListEntity.getGoods_id()+"")
                    .navigation();
        });
        mOrderDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderDetailBean.GoodsListEntity goodsListEntity = mOrderDetailAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_btn_1:
                    CommonUtils.openQQStranger(OrderDetailActivity.this,mOrderDetailBean.getShop_qq());
                    break;
                case R.id.tv_btn_2:
                    ARouter.getInstance().build("/foundation/refund")
                            .withString("phone", mOrderDetailBean.getReceiver_mobile())
                            .withString("order_no", order_no)
                            .withInt("type", 1)
                            .withParcelable("goodsBean", goodsListEntity)
                            .navigation();
                    break;
            }
        });
    }

    @Override
    public void onSuccess(OrderDetailBean orderDetailBean) {

        mOrderDetailBean = orderDetailBean;
        ExpressEntity express = orderDetailBean.getExpress();
        if (express != null) {

            String express_code = express.getExpress_code();
            String express_no = express.getExpress_no();
            String from_area = express.getFrom_area();
            String to_area = express.getTo_area();

            getPresenter().getLogisticsInfo(express_code,express_no,from_area,to_area);

        }
        switch (orderDetailBean.getOrder_status()) {
            case 1://待付款
                mTvOrderStatus.setText("未付款订单");
                mTvRefund.setText("取消订单");
                mTvPay.setText("立即支付");
                break;
            case 2://待发货
                mTvOrderStatus.setText("等待卖家发货");
                mTvRefund.setText("申请退款");
                mTvPay.setText("提醒卖家发货");
                break;
            case 3://待收货
                mTvOrderStatus.setText("卖家已发货");
                mTvRefund.setText("申请退款");
                mTvPay.setText("确认收货");
                break;
            case 4://待评价
                mTvOrderStatus.setText("待评价");
                mTvRefund.setText("申请退款");
                mTvPay.setText("去评价");
                break;
            case -1://退款中
                mTvOrderStatus.setText("退款中");
                mLlBottom.setVisibility(View.GONE);
//                mTvRefund.setVisibility(View.GONE);
//                mTvPay.setVisibility(View.GONE);
                break;
            case -2://退款完成
                mTvOrderStatus.setText("退款完成");
                mLlBottom.setVisibility(View.GONE);
//                mTvRefund.setVisibility(View.GONE);
//                mTvPay.setVisibility(View.GONE);
                break;
            case 5://已收货
                break;
            case 6://交易成功
                mTvOrderStatus.setText("交易成功");
                mLlBottom.setVisibility(View.GONE);
                break;
            case 7://已关闭
                break;

        }

        mTvConsignee.setText(orderDetailBean.getReceiver_name() + "    " + orderDetailBean.getReceiver_mobile());//收货人 + 电话
        mTvAddress.setText(orderDetailBean.getReceiver_address());//收货地址
        mTvShopName.setText(orderDetailBean.getShop_name());
        String str = "合计: ￥" + orderDetailBean.getPay_money();
        CommonUtils.setTwoTextColor(str, orderDetailBean.getPay_money(), Color.RED, mTvSumPrice);
        mOrderDetailAdapter.replaceData(orderDetailBean.getGoods_list());

        mTvOrderNum.setText("订单编号：" + orderDetailBean.getOrder_no());
        mTvCreateTime.setText("创建时间：" + orderDetailBean.getCreate_time());
        mTvPayTime.setText("付款时间：" + orderDetailBean.getPay_time());
        mTvSendTime.setText("发货时间：" + orderDetailBean.getConsign_time());
    }

    @Override
    public void showRemindDelivery() {
        mToastInstance.showToast("提醒发货成功");
    }

    @Override
    public void showExtendReceive() {
        mToastInstance.showToast("延长收货成功");
    }

    @Override
    public void showConfirmReceive() {
        mToastInstance.showToast("确认收货成功");
        getPresenter().getOrderDetail(order_no);
    }

    @Override
    public void showCancelOrder() {
        mToastInstance.showToast("取消订单成功");
    }

    @Override
    public void showLogisticsInfo(LogisticsBean logisticsBean) {
        List<LogisticsBean.DataEntity> data = logisticsBean.getData();
        if (data != null && data.size() != 0) {
            LogisticsBean.DataEntity dataEntity = data.get(0);
            mTvLogistics.setText(dataEntity.getContext());
            mTvDate.setText(dataEntity.getTime());
        } else {
            mTvLogistics.setText("未查询到物流信息");
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(date);
            mTvDate.setText(format);
        }
    }

    @OnClick({R.id.rl_logistics, R.id.tv_address,R.id.tv_refund,R.id.tv_pay})
    public void onViewClicked(View view) {
        if(mOrderDetailBean == null)return;
        switch (view.getId()) {
            case R.id.rl_logistics://物流信息
                if (mOrderDetailBean.getExpress() != null) {
                    ARouter.getInstance().build("/foundation/logistics")
                            .withParcelable("expressEntity",mOrderDetailBean.getExpress())
                            .navigation();
                }
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_refund:
                switch (mOrderDetailBean.getOrder_status()) {
                    case 1://待付款
                        //取消订单
                        getPresenter().cancelOrder(order_no);
                        break;
                    case 2://待发货
                        //申请退款
                    case 3://待收货
                        //申请退款
                    case 4://待评价
                        //申请退款
                        ARouter.getInstance().build("/foundation/refund")
                                .withString("phone", mOrderDetailBean.getReceiver_mobile())
                                .withString("order_no", order_no)
                                .withString("refundMoney", mOrderDetailBean.getPay_money())
                                .withInt("type", 2)
                                .navigation();
                        break;
                    case -1://退款中
                        break;
                }
                break;
            case R.id.tv_pay:
                switch (mOrderDetailBean.getOrder_status()) {
                    case 1://待付款
                        //立即支付
                        ARouter.getInstance().build("/foundation/Pay")
                                .withString("orderNum",order_no)
                                .withString("totalPrice",mOrderDetailBean.getPay_money())
                                .navigation();
                        break;
                    case 2://待发货
                        //提醒卖家发货
                        getPresenter().remindDelivery(order_no);
                        break;
                    case 3://待收货
                        //确认收货
                        getPresenter().confirmReceive(order_no);
                        break;
                    case 4://待评价
                        //去评价
                        ARouter.getInstance().build("/foundation/waitComment")
                                .withString("order_no",order_no)
                                .navigation();
                        break;
                    case -1://退款中
                        break;
                    case -2://退款完成
                        break;
                }
                break;
        }
    }

    class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailBean.GoodsListEntity, BaseViewHolder> {


        public OrderDetailAdapter() {
            super(R.layout.item_order_detail);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDetailBean.GoodsListEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            TextView tvRefund = helper.getView(R.id.tv_btn_2);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getPicture());
            helper.setText(R.id.tv_goods_des, item.getGoods_name())
                    .setText(R.id.tv_attr, item.getSku_name())
                    .setText(R.id.tv_price, "单价: " + item.getPrice())
                    .setText(R.id.tv_num, "数量: " + item.getNum());

            switch (mOrderDetailBean.getOrder_status()) {
                case 1://待付款
                    helper.setGone(R.id.ll_container, false);
                    break;
                case 2://待发货
                case 3://待收货
                case 4://待评价
                    helper.setGone(R.id.ll_container, true)
                            .addOnClickListener(R.id.tv_btn_1)
                            .addOnClickListener(R.id.tv_btn_2);


                    break;
                case -1://退款中
                    helper.setGone(R.id.ll_container, true)
                            .addOnClickListener(R.id.tv_btn_1)
                            .addOnClickListener(R.id.tv_btn_2);
                    //退款状态[-3退款申请不通过,-2退款关闭,-1拒绝退款,1买家申请退款,2等待买家退货,3等待卖家确认收货,4等待卖家确认退款,5退款成功]
                    switch (item.getRefund_status()) {
                        case -3:
                            tvRefund.setText("退款申请不通过");
                            tvRefund.setEnabled(true);
                            break;
                        case -2:
                            tvRefund.setText("退款关闭");
                            tvRefund.setEnabled(false);
                            break;
                        case -1:
                            tvRefund.setText("拒绝退款");
                            tvRefund.setEnabled(true);
                            break;
                        case 1:
                            tvRefund.setText("买家申请退款");
                            tvRefund.setEnabled(false);
                            break;
                        case 2:
                            tvRefund.setText("等待买家退货");
                            tvRefund.setEnabled(false);
                            break;
                        case 3:
                            tvRefund.setText("等待卖家确认收货");
                            tvRefund.setEnabled(false);
                            break;
                        case 4:
                            tvRefund.setText("等待卖家确认退款");
                            tvRefund.setEnabled(false);
                            break;
                        case 5:
                            tvRefund.setText("退款成功");
                            tvRefund.setEnabled(false);
                            break;
                        default:
                            tvRefund.setText("我要退款");
                            tvRefund.setEnabled(true);
                    }
                    break;
            }
        }
    }

}
