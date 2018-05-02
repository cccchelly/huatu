package com.alex.code.foundation.ui.order.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.OrderBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

public class OrderFragment extends BaseMvpFragment<OrderFragmentView, OrderFragmentPresenter> implements OrderFragmentView{

    public static final String TYPE = "type";
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int mType;
    private OrderAdapter mOrderAdapter;
    private int page_index = 1;
    private int page_size = 10;
    private AlertDialog mTipDialog;

    @Inject
    ToastInstance mToastInstance;
    private int mCanclePosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        mType = bundle.getInt(TYPE);

        initRecyclerView();
        initListener();
        mTipDialog = new AlertDialog.Builder(getActivity()).create();
    }

    @Override
    public void fetchData() {

        getPresenter().getOrderList(mType+"",page_index+"",page_size+"");
    }

    public static OrderFragment newInstance(int Type) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, Type);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    private void initRecyclerView() {

        mOrderAdapter = new OrderAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(getContext())
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
//        mRecyclerview.setAdapter(mOrderAdapter);
        mOrderAdapter.bindToRecyclerView(mRecyclerview);
        mOrderAdapter.setEmptyView(R.layout.recyclerview_empty_view);

        mOrderAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderBean.DataEntity dataEntity = mOrderAdapter.getData().get(position);
            String order_no = dataEntity.getOrder_no();
            ARouter.getInstance().build("/foundation/orderDetail")
                    .withString("order_no",order_no)
                    .navigation();
        });

        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000);
                page_index++;
                getPresenter().getOrderList(mType+"",page_index+"",page_size+"");
//                refreshlayout.autoLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000);
                mOrderAdapter.getData().clear();
                page_index = 1;
                getPresenter().getOrderList(mType+"",page_index+"",page_size+"");
            }
        });
    }

    private void initListener() {

        mOrderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderBean.DataEntity item = (OrderBean.DataEntity) adapter.getData().get(position);
            OrderBean.DataEntity.GoodsListEntity goodsListEntity = item.getGoods_list().get(0);
            int order_status = item.getOrder_status();
            String msg = "";
            switch (view.getId()) {
                case R.id.tv_btn_1:
                    if (order_status == 1) {//gone

                    } else if (order_status == 2) {//gone

                    } else if (order_status == 3) {//延长收货
                        msg = "延长收货";

                        getPresenter().extendReceive(item.getOrder_no());
                    } else {//删除订单
                        msg = "删除订单";
                        mTipDialog.setTitle("删除订单");
                        mTipDialog.setMessage("您确定要删除订单吗？");
                        mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                                (dialog, which) -> {
                                    return;
                                });
                        mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                (dialog, which) -> {
                                    mCanclePosition = position;//记录取消订单的position
                                    getPresenter().cancelOrder(item.getOrder_no());
                                });
                        mTipDialog.show();
                    }
                    break;
                case R.id.tv_btn_2:
                    if (order_status == 1) {//取消订单
                        msg = "取消订单";
//                            DialogPlus.newDialog(getContext())
//                                    .setContentHolder()
                        mTipDialog.setTitle("取消订单");
                        mTipDialog.setMessage("您确定要取消订单吗？");
                        mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                                (dialog, which) -> {
                                    return;
                                });
                        mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                (dialog, which) -> {
                                    mCanclePosition = position;//记录取消订单的position
                                    getPresenter().cancelOrder(item.getOrder_no());
                                });
                        mTipDialog.show();

                    } else if (order_status == 2) {//取消订单
                        msg = "取消订单";
                        mTipDialog.setTitle("取消订单");
                        mTipDialog.setMessage("您确定要取消订单吗？");
                        mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                                (dialog, which) -> {
                                    return;
                                });
                        mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                (dialog, which) -> {
                                    mCanclePosition = position;//记录取消订单的position
                                    getPresenter().cancelOrder(item.getOrder_no());
                                });
                        mTipDialog.show();

                    } else if (order_status == 3) {//查看物流
                        msg = "查看物流";
                        if (item.getExpress() != null) {
                            ARouter.getInstance().build("/foundation/logistics")
                                    .withParcelable("expressEntity",item.getExpress())
                                    .navigation();
                        }

                    } else {//查看物流
                        msg = "查看物流";
                        if (item.getExpress() != null) {
                            ARouter.getInstance().build("/foundation/logistics")
                                    .withParcelable("expressEntity",item.getExpress())
                                    .navigation();
                        }
                    }
                    break;
                case R.id.tv_btn_3:
                    if (order_status == 1) {//去付款
                        msg = "去付款";
                        ARouter.getInstance().build("/foundation/Pay")
                                .withString("orderNum",item.getOrder_no())
                                .withString("totalPrice",item.getPay_money())
                                .navigation();
                    } else if (order_status == 2) {//提醒卖家发货
                        msg = "提醒卖家发货";
                        getPresenter().remindDelivery(item.getOrder_no());

                    } else if (order_status == 3) {//确认收货
                        msg = "确认收货";
                        mTipDialog.setTitle("确认收货");
                        mTipDialog.setMessage("您确定要确认收货吗？商品收到了在确认收货哟~");
                        mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                                (dialog, which) -> {
                                    return;
                                });
                        mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                (dialog, which) -> getPresenter().confirmReceive(item.getOrder_no()));
                        mTipDialog.show();

                    } else if(order_status == 4){//评价
                        msg = "评价";
                        ARouter.getInstance().build("/foundation/waitComment")
                                .withString("order_no",item.getOrder_no())
                                .navigation();
                        break;
                    }
            }

//                Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onSuccess(List<OrderBean.DataEntity> data) {

        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        mOrderAdapter.addData(data);
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
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void showCancelOrder() {

        mToastInstance.showToast("删除订单成功");
        mRefreshLayout.autoRefresh();
//        if (mCanclePosition != -1) {
//            mOrderAdapter.remove(mCanclePosition);
//        }
//        mCanclePosition = -1;
    }

    class OrderAdapter extends BaseQuickAdapter<OrderBean.DataEntity, BaseViewHolder> {

        public OrderAdapter() {
            super(R.layout.item_order);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderBean.DataEntity item) {

            OrderBean.DataEntity.GoodsListEntity goodsListEntity = item.getGoods_list().get(0);
            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + goodsListEntity.getPicture());
            helper.setText(R.id.tv_goods_des,goodsListEntity.getGoods_name())
                    .setText(R.id.tv_shop_name,item.getShop_name())
                    .setText(R.id.tv_attr,goodsListEntity.getSku_name())
                    .setText(R.id.tv_price,"单价: "+goodsListEntity.getPrice())
                    .setText(R.id.tv_num,"数量: "+goodsListEntity.getNum())
                    .setText(R.id.tv_sum_num,"共"+item.getGoods_total_num()+"件商品");

            TextView tvSumPrice = helper.getView(R.id.tv_sum_price);
            String str = "合计: "+ item.getPay_money() + "（含运费"+item.getShipping_money()+"）";
            int fstart = str.indexOf(item.getPay_money());
            int fend = fstart + item.getPay_money().length();
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(new AbsoluteSizeSpan(16,true), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvSumPrice.setText(style);

            switch (item.getOrder_status()) {
                case 1://待付款
                    helper.setText(R.id.tv_status,"未付款订单")
                            .setGone(R.id.tv_btn_2, true)
                            .setText(R.id.tv_btn_2, "取消订单")
                            .setGone(R.id.tv_btn_3, true)
                            .setText(R.id.tv_btn_3, "去付款")
                            .setGone(R.id.tv_btn_1, false);
                    break;
                case 2://待发货
                    helper.setText(R.id.tv_status,"等待卖家发货")
                            .setGone(R.id.tv_btn_2, false)
                            .setText(R.id.tv_btn_2, "取消订单")//这个地方改申请退款
                            .setGone(R.id.tv_btn_3, true)
                            .setText(R.id.tv_btn_3, "提醒卖家发货")
                            .setGone(R.id.tv_btn_1, false);
                    break;
                case 3://待收货
                    helper.setText(R.id.tv_status,"卖家已发货")
                            .setGone(R.id.tv_btn_1, true)
                            .setText(R.id.tv_btn_1, "延长收货")
                            .setGone(R.id.tv_btn_2, true)
                            .setText(R.id.tv_btn_2, "查看物流")
                            .setGone(R.id.tv_btn_3, true)
                            .setText(R.id.tv_btn_3, "确认收货");
                    break;
                case 4://待评价
                    helper.setText(R.id.tv_status,"待评价")
                            .setGone(R.id.tv_btn_1, true)
                            .setText(R.id.tv_btn_1, "删除订单")
                            .setGone(R.id.tv_btn_2, true)
                            .setText(R.id.tv_btn_2, "查看物流")
                            .setGone(R.id.tv_btn_3, true)
                            .setText(R.id.tv_btn_3, "评价");
                    break;
                case -1://退款中
                    helper.setText(R.id.tv_status,"退款中")
                            .setGone(R.id.tv_btn_1, false)
                            .setText(R.id.tv_btn_1, "")
                            .setGone(R.id.tv_btn_2, false)
                            .setText(R.id.tv_btn_2, "")
                            .setGone(R.id.tv_btn_3, false)
                            .setText(R.id.tv_btn_3, "");
                    break;
                case -2://退款完成
                    helper.setText(R.id.tv_status,"退款完成")
                            .setGone(R.id.tv_btn_1, false)
                            .setText(R.id.tv_btn_1, "")
                            .setGone(R.id.tv_btn_2, false)
                            .setText(R.id.tv_btn_2, "")
                            .setGone(R.id.tv_btn_3, false)
                            .setText(R.id.tv_btn_3, "");
                    break;
                case 5://已收货
                    break;
                case 6://交易成功
                    helper.setText(R.id.tv_status,"交易成功")
                            .setGone(R.id.tv_btn_1, true)
                            .setText(R.id.tv_btn_1, "删除订单")
                            .setGone(R.id.tv_btn_2, true)
                            .setText(R.id.tv_btn_2, "查看物流")
                            .setGone(R.id.tv_btn_3, true)
                            .setText(R.id.tv_btn_3, "已评价");
                    break;
                case 7://已关闭
                    break;
            }

            helper.addOnClickListener(R.id.tv_btn_1)
                    .addOnClickListener(R.id.tv_btn_2)
                    .addOnClickListener(R.id.tv_btn_3);
        }
    }

}
