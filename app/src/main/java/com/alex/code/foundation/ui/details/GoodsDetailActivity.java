package com.alex.code.foundation.ui.details;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.MyFragmentPagerAdapter;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.AtteGoodsBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.bean.GoodsDetailBean;
import com.alex.code.foundation.bean.ShopCartBean;
import com.alex.code.foundation.ui.details.attrs.AttrsFragment;
import com.alex.code.foundation.ui.details.comment.CommentFragment;
import com.alex.code.foundation.ui.details.goods.GoodsFragment;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.utils.um.UMEventId;
import com.alex.code.foundation.view.NonSwipeViewPager;
import com.alex.code.foundation.view.holder.ShopCartDialog;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.socialize.UMShareAPI;

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
 * Des: 商品详情
 * Date: 2017/10/26.
 */

@Route(path = "/foundation/goodsDetail")
public class GoodsDetailActivity extends BaseMvpActivity<GoodsDetailView, GoodsDetailPresenter> implements GoodsDetailView {


    @BindView(R.id.tabs)
    TabLayout         mTabs;
    @BindView(R.id.toolbar)
    Toolbar           mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout      mAppbar;
    @BindView(R.id.viewpager)
    NonSwipeViewPager mViewpager;
    @BindView(R.id.tv_favorite)
    TextView          mTvFavorite;
    private ShopCartDialog mShopCartDialog;
    private double mTotalPrice;

    @Autowired
    public String goodsId;
    @Inject
    ToastInstance mToastInstance;

    public String mShopId;
    private int mFavorite = -1;
    private String mShop_qq;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        VLog.d("goodsId: "+goodsId);
        mViewpager.setSwipeAble(true);
        setSupportActionBar(mToolbar);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_shoucang);
        drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
        mTvFavorite.setCompoundDrawables(null, drawable, null, null);
        mTvFavorite.setText("收藏");

        mToolbar.setNavigationIcon(R.drawable.ic_black_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        initFragment();
        if (mShopCartDialog == null) {
            mShopCartDialog = new ShopCartDialog(this);
        }

        mShopCartDialog.setConfirmListener((type, totalPrice, goods_id, goods_num, sku_id) -> {

            mTotalPrice = totalPrice;

            if (type == 0) {
                //直接购买
                getPresenter().confirmOrderList(type + "", "", goods_id, goods_num, sku_id);
            } else {
                //加入购物车
                getPresenter().addShopCart(goods_id, goods_num, sku_id);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("商品详情");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("商品详情");
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
        UMShareAPI.get(this).release();

    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GoodsFragment.newInstance(goodsId));
        fragments.add(AttrsFragment.newInstance(goodsId));
        fragments.add(CommentFragment.newInstance(goodsId));

        List<String> titles = new ArrayList<>();
        titles.add("商品");
        titles.add("详情");
        titles.add("评价");

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fragmentManager, fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(3);
        mTabs.setupWithViewPager(mViewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_shop:
                ARouter.getInstance().build("/foundation/main")
                        .withString("type","shop")
                        .navigation();
                break;
            case R.id.action_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTabVisibility(int visibility) {
        if (isFinishing())
            return;
        mTabs.setVisibility(visibility);
        if (visibility == View.GONE) {
            mToolbar.setTitle("商品详情");
            mViewpager.setSwipeAble(false);
        } else {
            mToolbar.setTitle("");
            mViewpager.setSwipeAble(true);
        }
    }

    @OnClick({R.id.tv_kefu, R.id.tv_shop, R.id.tv_favorite, R.id.tv_shopCart, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_kefu:
                CommonUtils.openQQStranger(this,mShop_qq);
                break;
            case R.id.tv_shop:

                VLog.d("mShopId: "+mShopId);
                if (!TextUtils.isEmpty(mShopId)) {
                    ARouter.getInstance().build("/foundation/shopHome")
                            .withString("shopId",mShopId)
                            .navigation();
                }
                break;
            case R.id.tv_favorite:
//                if(mFavorite == -1)return;
                if (mFavorite == 1) {
                    getPresenter().updateGoodsFocus(goodsId,0+"");
                } else {
                    getPresenter().updateGoodsFocus(goodsId,1+"");
                }
                break;
            case R.id.tv_shopCart:
                Tracker.getDefaultTracker().trackEvent(UMEventId.GOODS_JOIN_SHOPCART);
                mShopCartDialog.setType(1);
                getPresenter().getGoodSpec(goodsId);
                mShopCartDialog.show();
//                DialogPlus.newDialog(this)
//                    .setContentHolder(new ShopCartHolder())
//                    .setGravity(Gravity.BOTTOM)
//                    .setCancelable(true)
//                    .setContentBackgroundResource(Color.TRANSPARENT)
//                    .create()
//                    .show();
                break;
            case R.id.tv_buy:
                Tracker.getDefaultTracker().trackEvent(UMEventId.GOODS_BUY);
                mShopCartDialog.setType(0);
                getPresenter().getGoodSpec(goodsId);
                mShopCartDialog.show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (TextUtils.equals(event.getMsg(), EventCons.SWITCH_COMMENT)) {
            mViewpager.setCurrentItem(2);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
    }

    @Override
    public void onSuccess(GoodSpeBean goodSpeBean) {
        mShopCartDialog.setData(goodSpeBean);
    }

    @Override
    public void onOrderSuccess(ShopCartBean shopCartBean) {

    }

    @Override
    public void showAffirmOrder(AffirmOrderBean affirmOrderBean) {
        mShopCartDialog.dismiss();
        ARouter.getInstance().build("/foundation/affirmOrder")
                .withDouble("totalPrice",mTotalPrice)
                .withInt("is_cart",0)
                .withParcelable("affirmOrderBean",affirmOrderBean)
                .navigation();
    }

    @Override
    public void showAddShopCart() {
        mToastInstance.showToast("加入购物车成功");
        mShopCartDialog.dismiss();
    }

    @Override
    public void updateAtteGoods(AtteGoodsBean atteGoodsBean) {

        mFavorite = atteGoodsBean.getFavorite();


        if (mFavorite == 1) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_shoucang_light);
            drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
            mTvFavorite.setCompoundDrawables(null, drawable, null, null);
            mTvFavorite.setText("已收藏");
        } else {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_shoucang);
            drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
            mTvFavorite.setCompoundDrawables(null, drawable, null, null);
            mTvFavorite.setText("收藏");
        }
    }

    public void setShopid(String shopid) {
        mShopId = shopid;
    }

    public void setGoodsDetailBean(GoodsDetailBean.GoodsDetailEntity bean) {
        mShopId = bean.getShop_id();
        mShop_qq = bean.getShop_qq();
        int favorite = bean.getFavorite();
        VLog.d("favorite: "+favorite);
        if (favorite == 1) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_shoucang_light);
            drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
            mTvFavorite.setCompoundDrawables(null, drawable, null, null);
            mTvFavorite.setText("已收藏");
        } else {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_shoucang);
            drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
            mTvFavorite.setCompoundDrawables(null, drawable, null, null);
            mTvFavorite.setText("收藏");
        }
    }
}
