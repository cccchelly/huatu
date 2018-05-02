package com.alex.code.foundation.ui.refunded;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.RefundedGridAdapter;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.RefundedListBean;
import com.alex.code.foundation.view.CustomToolBar;
import com.alex.code.foundation.view.GridViewForScrollView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/25.
 */

@Route(path = "/foundation/refunded")
public class RefundedActivity extends BaseMvpActivity<RefundedView, RefundedPresenter> implements RefundedView{
    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private RefundedAdapter mRefundedAdapter;
    private int page_index = 1;
    private int page_size = 10;
    private int mTotalCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refunded;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("退款/售后")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        getPresenter().getRefundedList(page_index+"",page_size+"");
    }

    private void initRecyclerView() {
        mRefundedAdapter = new RefundedAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
//        mRecyclerview.setAdapter(mRefundedAdapter);
        mRefundedAdapter.bindToRecyclerView(mRecyclerview);
        mRefundedAdapter.setEmptyView(R.layout.recyclerview_empty_view);

        mRefundedAdapter.setOnItemClickListener((adapter, view, position) -> {
            RefundedListBean.DataEntity dataEntity = mRefundedAdapter.getData().get(position);
            String order_no = dataEntity.getOrder_no();
            ARouter.getInstance().build("/foundation/orderDetail")
                    .withString("order_no",order_no)
                    .navigation();
        });

        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //                refreshlayout.finishLoadmore(2000);
                page_index++;
                if (page_index > mTotalCount) {
                    mRefreshLayout.finishLoadmore(2000);
                    return;
                }
                getPresenter().getRefundedList(page_index+"",page_size+"");
                //                refreshlayout.autoLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //                refreshlayout.finishRefresh(2000);
                mRefundedAdapter.getData().clear();
                page_index = 1;
                getPresenter().getRefundedList(page_index+"",page_size+"");
            }
        });
    }

    @Override
    public void onSuccess(RefundedListBean refundedListBean) {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        mTotalCount = refundedListBean.getTotal_count();
        mRefundedAdapter.addData(refundedListBean.getData());
    }

    class RefundedAdapter extends BaseQuickAdapter<RefundedListBean.DataEntity, BaseViewHolder> {

        public RefundedAdapter() {
            super(R.layout.item_refunded);
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundedListBean.DataEntity item) {

            List<RefundedListBean.DataEntity.GoodsListEntity> goods_list = item.getGoods_list();

            helper.setText(R.id.tv_shop_name,item.getShop_name())
                    .setText(R.id.tv_sum_num,"共"+item.getGoods_total_num()+"件商品");

            TextView tvSumPrice = helper.getView(R.id.tv_sum_price);
            String str = "合计: "+ item.getRefund_money() + "（含运费"+item.getShipping_money()+"）";
            int fstart = str.indexOf(item.getRefund_money());
            int fend = fstart + item.getRefund_money().length();
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(new AbsoluteSizeSpan(16,true), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvSumPrice.setText(style);

            GridViewForScrollView gridView = helper.getView(R.id.gridView);
            RefundedGridAdapter refundedGridAdapter = new RefundedGridAdapter(RefundedActivity.this,goods_list);
            gridView.setAdapter(refundedGridAdapter);
            gridView.setOnItemClickListener((parent, view, position, id) ->
                    ARouter.getInstance().build("/foundation/orderDetail")
                    .withString("order_no",item.getOrder_no())
                    .navigation());
        }
    }
}
