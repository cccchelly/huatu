package com.alex.code.foundation.ui.flashsale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.FlashSaleBean;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
import com.alex.code.foundation.view.SnapUpCountDownTimerView;
import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/20.
 */

@Route(path = "/foundation/flashSale")
public class FlashSaleActicity extends BaseMvpActivity<FlashSaleView, FlashSalePresenter> implements FlashSaleView{

    @BindView(R.id.toolbar)
    CustomToolBar            mToolbar;
    @BindView(R.id.tab_recyclerview)
    RecyclerView             mTabRecyclerview;
    @BindView(R.id.banner)
    Banner                   mBanner;
    @BindView(R.id.tv_status)
    TextView                 mTvStatus;
    @BindView(R.id.timerView)
    SnapUpCountDownTimerView mTimerView;
    @BindView(R.id.recyclerview)
    RecyclerView             mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout       mRefreshLayout;
    private TabFlashAdapter mTabFlashAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int mType = 3;
    private int mTotalCount;
    private boolean isFirst = true;
    private FlashSaleAdapter mFlashSaleAdapter;
    private String start_time = "";
    private String end_time = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash_sale;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("限时抢购")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        initListener();
        getPresenter().getFlashSaleList(start_time,end_time,pageIndex+"",pageSize+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("限时抢购");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("限时抢购");
    }

    private void initListener() {
        mTabFlashAdapter.setOnItemClickListener((adapter, view, position) -> {
            FlashSaleBean.TimeListEntity timeListEntity = mTabFlashAdapter.getData().get(position);
            start_time = timeListEntity.getStart_time();
            end_time = timeListEntity.getEnd_time();
            mType = timeListEntity.getType();
            mTabFlashAdapter.refreshItem(position);

            pageIndex = 1;
            mFlashSaleAdapter.getData().clear();
            getPresenter().getFlashSaleList(start_time,end_time,pageIndex+"",pageSize+"");
        });

        mFlashSaleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FlashSaleBean.GoodsEntity goodsEntity = mFlashSaleAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_commit:
                    ARouter.getInstance().build("/foundation/goodsDetail")
                            .withString("goodsId",goodsEntity.getGoods_id())
                            .navigation();
                    break;
            }
        });
    }


    private void initRecyclerView() {
        mTabFlashAdapter = new TabFlashAdapter();
        mTabRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mTabRecyclerview.setAdapter(mTabFlashAdapter);

        mFlashSaleAdapter = new FlashSaleAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
//        mRecyclerview.setAdapter(mFlashSaleAdapter);
        mFlashSaleAdapter.bindToRecyclerView(mRecyclerview);
        mFlashSaleAdapter.setEmptyView(R.layout.recyclerview_empty_view);


        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                if (pageIndex > mTotalCount) {
                    mRefreshLayout.finishLoadmore(2000);
                    return;
                }
                getPresenter().getFlashSaleList(start_time,end_time,pageIndex+"",pageSize+"");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mFlashSaleAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getFlashSaleList(start_time,end_time,pageIndex+"",pageSize+"");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimerView != null) {
            mTimerView.stop();
        }
    }

    @Override
    public void onSuccess(FlashSaleBean flashSaleBean) {

        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        List<FlashSaleBean.GoodsEntity> goods = flashSaleBean.getGoods();
        List<FlashSaleBean.PlatformEntity> platform = flashSaleBean.getPlatform();
        List<FlashSaleBean.TimeListEntity> time_list = flashSaleBean.getTime_list();
        long su_time = flashSaleBean.getSu_time();
        mTotalCount = flashSaleBean.getTotal_count();
        mBanner.setImages(platform).setImageLoader(new FlashSaleLoder()).start();

        if (su_time == 0 && mType == 1) {
            mTvStatus.setText("已结束");
            mTimerView.setVisibility(View.GONE);
        } else if (mType == 4) {
            mTvStatus.setText("即将开始");
            mTimerView.setVisibility(View.GONE);
        } else {
            if (mType == 2) {
                mTvStatus.setText("本场还剩");
            } else if (mType == 3) {
                mTvStatus.setText("本场还剩");
            }
            mTimerView.setVisibility(View.VISIBLE);
            int hours = (int) ((su_time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            int minutes = (int) ((su_time % (1000 * 60 * 60)) / (1000 * 60));
            int seconds = (int) ((su_time % (1000 * 60)) / 1000);
            mTimerView.setTime(hours,minutes,seconds);
            mTimerView.start();
        }

        mFlashSaleAdapter.addData(goods);
        if (isFirst) {
            mTabFlashAdapter.replaceData(time_list);
            for (int i = 0; i < time_list.size(); i++) {
                FlashSaleBean.TimeListEntity timeListEntity = time_list.get(i);
                if (timeListEntity.getType() == 3) {
                    mTabFlashAdapter.refreshItem(i);
                }
            }

            isFirst = false;
        }

    }

    class FlashSaleLoder extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            FlashSaleBean.PlatformEntity platform = (FlashSaleBean.PlatformEntity) path;
            SimpleDraweeView sdv = (SimpleDraweeView) imageView;
            sdv.setImageURI(AppConstants.PIC_BASE_URL + platform.getAdv_image());
        }

        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }

    class TabFlashAdapter extends BaseQuickAdapter<FlashSaleBean.TimeListEntity, BaseViewHolder> {

        private int selectedPos = -1;
        private int oldPos = -1;

        public void refreshItem(int position) {
            if (selectedPos != -1) {
                oldPos  = selectedPos;
            }
            selectedPos = position;
            if (oldPos != -1) {
                notifyItemChanged(oldPos);
            }
            notifyItemChanged(selectedPos);
        }

        public TabFlashAdapter() {
            super(R.layout.item_flash_sale_tab);
        }

        @Override
        protected void convert(BaseViewHolder helper, FlashSaleBean.TimeListEntity item) {

            int position = helper.getAdapterPosition();

            if(selectedPos == position) {
                //相同设置高亮
                helper.setBackgroundRes(R.id.ll_container, R.drawable.bg_flash_sale_tab)
                        .setTextColor(R.id.tv_time, Color.WHITE)
                        .setTextColor(R.id.tv_sale_status, Color.WHITE);
            } else {
                //不同设置
                helper.setBackgroundRes(R.id.ll_container,0)
                        .setTextColor(R.id.tv_time, Color.BLACK)
                        .setTextColor(R.id.tv_sale_status, ContextCompat.getColor(FlashSaleActicity.this,R.color.text_333333));
            }

            String start_time = item.getStart_time();
            helper.setText(R.id.tv_time, start_time.substring(start_time.length() - 8, start_time.length() - 3))
                    .setText(R.id.tv_sale_status, item.getValue());
        }
    }

    class FlashSaleAdapter extends BaseQuickAdapter<FlashSaleBean.GoodsEntity, BaseViewHolder> {

        public FlashSaleAdapter() {
            super(R.layout.item_flash_sale_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, FlashSaleBean.GoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            TextView tvCommit = helper.getView(R.id.tv_commit);
            TextView tvOldPrice = helper.getView(R.id.tv_old_price);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());

            helper.setText(R.id.tv_goods_des, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPromotion_price())
                    .setText(R.id.tv_old_price, "原价: ￥" + item.getPrice())
                    .setText(R.id.tv_oos, "仅剩: " + item.getStock() + "件");

            if (mType == 2 || mType == 3) {
                if (item.getStock() > 0) {
                    tvCommit.setEnabled(true);
                    tvCommit.setText("马上抢");
                    tvCommit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tvCommit.setEnabled(false);
                    tvCommit.setText("已抢光");
                    tvCommit.setTextColor(getResources().getColor(R.color.text_666666));
                }
            } else if (mType == 1) {
                tvCommit.setEnabled(false);
                tvCommit.setText("已结束");
                tvCommit.setTextColor(getResources().getColor(R.color.text_666666));
            } else if (mType == 4) {
                tvCommit.setEnabled(false);
                tvCommit.setText("未开始");
                tvCommit.setTextColor(getResources().getColor(R.color.text_666666));
            }
            helper.addOnClickListener(R.id.tv_commit);
        }
    }
}
