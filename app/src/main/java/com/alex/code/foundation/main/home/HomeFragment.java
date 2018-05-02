package com.alex.code.foundation.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.HomeBean;
import com.alex.code.foundation.data.preference.IPreference;
import com.alex.code.foundation.ui.webview.WebviewActivity;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.FrescoImageLoder;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.utils.um.UMEventId;
import com.alex.code.foundation.view.UPMarqueeView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class HomeFragment extends BaseMvpFragment<HomeView, HomePresenter> implements HomeView {

    @BindView(R.id.banner)
    Banner        mBanner;
    @BindView(R.id.tv_flash_sale)
    TextView      mTvFlashSale;
    @BindView(R.id.tv_hot_goods)
    TextView      mTvHotGoods;
    @BindView(R.id.tv_new_goods)
    TextView      mTvNewGoods;
    @BindView(R.id.tv_category)
    TextView      mTvCategory;
    @BindView(R.id.tv_my_order)
    TextView      mTvMyOrder;
    @BindView(R.id.upview)
    UPMarqueeView mUpview;
    @BindView(R.id.rv_flash_sale)
    RecyclerView  mRvFlashSale;
    @BindView(R.id.rv_hot_goods)
    RecyclerView  mRvHotGoods;
    @BindView(R.id.ll_flash_sale)
    LinearLayout  mLlFlashSale;

    @Inject
    IPreference mIPreference;

    List<View> views = new ArrayList<>();
    private FlashSaleAdapter mFlashSaleAdapterr;
    private HotGoodsAdapter  mHotGoodsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {


        initRecyclerView();
        initListener();
    }

    private void initListener() {
        mFlashSaleAdapterr.setOnItemClickListener((adapter, view, position) -> {
            HomeBean.DisGoodsEntity goodsEntity = mFlashSaleAdapterr.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id()+"")
                    .navigation();
        });

        mHotGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomeBean.NowGoodsEntity goodsEntity = mHotGoodsAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id()+"")
                    .navigation();
        });
    }

    private void initRecyclerView() {
        mFlashSaleAdapterr = new FlashSaleAdapter();
        mRvFlashSale.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvFlashSale.setNestedScrollingEnabled(false);
        mRvFlashSale.setAdapter(mFlashSaleAdapterr);

        mHotGoodsAdapter = new HotGoodsAdapter();

        mRvHotGoods.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRvHotGoods.setNestedScrollingEnabled(false);
//        mRvHotGoods.addItemDecoration(ItemDecorations.vertical(getContext())
//                .type(0, R.drawable.divider_decoration_transparent_h1)
//                .create());
        mRvHotGoods.setAdapter(mHotGoodsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isDataInitiated = false;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void fetchData() {

        getPresenter().getHomePageData();

    }


    @OnClick({R.id.tv_search, R.id.iv_msg,R.id.tv_flash_sale, R.id.tv_hot_goods, R.id.tv_new_goods, R.id.tv_category, R.id.tv_my_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                ARouter.getInstance().build("/foundation/homeSearch").navigation();
                break;
            case R.id.iv_msg:
                ARouter.getInstance().build("/foundation/notice").navigation();
                break;
            case R.id.tv_flash_sale://限时抢购
                Tracker.getDefaultTracker().trackEvent(UMEventId.FLASH_SALE);
                ARouter.getInstance().build("/foundation/flashSale").navigation();
                break;
            case R.id.tv_hot_goods://热卖商品
                Tracker.getDefaultTracker().trackEvent(UMEventId.HOT_GOODS);
                ARouter.getInstance().build("/foundation/hotGoods").navigation();
                break;
            case R.id.tv_new_goods://新品
                Tracker.getDefaultTracker().trackEvent(UMEventId.NEW_GOODS);
                ARouter.getInstance().build("/foundation/newGoods").navigation();
                break;
            case R.id.tv_category://分类
                ARouter.getInstance().build("/foundation/todoComment").navigation();
                break;
            case R.id.tv_my_order://我的订单
                Tracker.getDefaultTracker().trackEvent(UMEventId.MY_ORDER);
                if (TextUtils.isEmpty(mIPreference.getToken())) {
                    ARouter.getInstance().build("/foundation/login").withInt("main",100).navigation(getActivity(),100);
                    return;
                }
                ARouter.getInstance().build("/foundation/order")
                        .withInt("type",0)
                        .navigation();
//                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                break;
        }
    }

    @Override
    public void onSuccess(HomeBean homeBean) {

        if(homeBean == null) return;

        List<HomeBean.DisGoodsEntity> dis_goods = homeBean.getDis_goods();
        List<HomeBean.NoticeEntity> notice = homeBean.getNotice();
        List<HomeBean.NowGoodsEntity> now_goods = homeBean.getNow_goods();
        List<HomeBean.PlatformEntity> platform = homeBean.getPlatform();

        if (dis_goods == null || dis_goods.size() == 0) {
            mRvFlashSale.setVisibility(View.GONE);
            mLlFlashSale.setVisibility(View.GONE);
        } else {
            mRvFlashSale.setVisibility(View.VISIBLE);
            mLlFlashSale.setVisibility(View.VISIBLE);
            mFlashSaleAdapterr.replaceData(dis_goods);
        }
        if (now_goods != null) {
            mHotGoodsAdapter.replaceData(now_goods);
        }

/*        List<String> urls = new ArrayList<>();
        for (HomeBean.PlatformEntity platformEntity : platform) {
            urls.add(AppConstants.PIC_BASE_URL + platformEntity.getAdv_image());
        }*/
   //     mBanner.setImages(urls).setImageLoader(new FrescoImageLoder()).start();
        mBanner.setImages(platform).setImageLoader(new HomeLoder()).start();

        views.clear();
        if(notice == null) return;
        for (int i = 0; i < notice.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_broadcast, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(App.getAppContext(), position + "你点击了---" + notice.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(App.getAppContext(), position + "你点击了***" + notice.get(position + 1).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            //进行对控件赋值
            tv1.setText(notice.get(i).getNotice_message());
            if (notice.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(notice.get(i + 1).getNotice_message());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }
            //添加到循环滚动数组里面去
            views.add(moreView);
        }

        mUpview.setViews(views);
        mUpview.setOnItemClickListener(new UPMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
//                Toast.makeText(App.getAppContext(), "你点击了第几个items" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class HomeLoder extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            HomeBean.PlatformEntity platform = (HomeBean.PlatformEntity) path;
            String adv_title = platform.getAdv_title();
            String adv_url = platform.getAdv_url();

            SimpleDraweeView sdv = (SimpleDraweeView) imageView;
            sdv.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(adv_url) && adv_url.startsWith("http")) {//跳web
                    Intent intent = new Intent(getContext(),WebviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",adv_url);
                    bundle.putString("title",adv_title);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                } else{
                    CommonUtils.startNativeActivity(adv_url);
                }
            });
            sdv.setImageURI(AppConstants.PIC_BASE_URL + platform.getAdv_image());
        }

        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }

    class FlashSaleAdapter extends BaseQuickAdapter<HomeBean.DisGoodsEntity, BaseViewHolder> {

        public FlashSaleAdapter() {
            super(R.layout.item_flash_sale);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean.DisGoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            TextView tvOldPrice = helper.getView(R.id.tv_old_price);
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_now_price, "￥"+item.getPromotion_price())
                    .setText(R.id.tv_old_price, "￥"+item.getPrice());
        }
    }

    class HotGoodsAdapter extends BaseQuickAdapter<HomeBean.NowGoodsEntity, BaseViewHolder> {

        public HotGoodsAdapter() {
            super(R.layout.item_hot_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean.NowGoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥"+item.getPrice());
        }
    }
}
