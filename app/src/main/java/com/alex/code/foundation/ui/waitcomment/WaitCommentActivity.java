package com.alex.code.foundation.ui.waitcomment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
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

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/20.
 */

@Route(path = "/foundation/waitComment")
public class WaitCommentActivity extends BaseMvpActivity<WaitCommentView, WaitCommentPresenter> implements WaitCommentView{

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView  mRecyclerview;
    private WaitCommentAdapter mWaitCommentAdapter;

    @Autowired
    public String order_no;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wait_comment;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        mToolbar.setTitle("评价")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        getPresenter().getOrderDetail(order_no);
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

        mWaitCommentAdapter = new WaitCommentAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h1)
                .create());
        mRecyclerview.setAdapter(mWaitCommentAdapter);

        mWaitCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderDetailBean.GoodsListEntity goodsListEntity = mWaitCommentAdapter.getData().get(position);
            if (view.getId() == R.id.tv_comment) {
                ARouter.getInstance().build("/foundation/todoComment")
                        .withString("order_no",order_no)
                        .withParcelable("goodsBean",goodsListEntity)
                        .navigation();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (TextUtils.equals(event.getMsg(), EventCons.COMMENT_REFRESH)) {
            getPresenter().getOrderDetail(order_no);
        }

    }

    @Override
    public void onSuccess(OrderDetailBean orderDetailBean) {
        mWaitCommentAdapter.replaceData(orderDetailBean.getGoods_list());
    }


    class WaitCommentAdapter extends BaseQuickAdapter<OrderDetailBean.GoodsListEntity, BaseViewHolder> {


        public WaitCommentAdapter() {
            super(R.layout.item_wait_comment);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDetailBean.GoodsListEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            TextView tvComment = helper.getView(R.id.tv_comment);
            if (item.getIs_evaluate() == 1) {
                tvComment.setText("已评价");
                tvComment.setEnabled(false);
            } else {
                tvComment.setText("评价");
                tvComment.setEnabled(true);
            }
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getPicture());
            helper.setText(R.id.tv_goods_des, item.getGoods_name())
                    .setText(R.id.tv_attr, item.getSku_name())
                    .setText(R.id.tv_price, "单价: " + item.getPrice())
                    .setText(R.id.tv_num, "数量: " + item.getNum())
                    .addOnClickListener(R.id.tv_comment);
            }
    }
}
