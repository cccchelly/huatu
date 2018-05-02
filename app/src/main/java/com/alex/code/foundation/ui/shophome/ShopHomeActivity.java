package com.alex.code.foundation.ui.shophome;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.HomePageBean;
import com.alex.code.foundation.bean.ShopFocusBean;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.view.UPMarqueeView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

@Route(path = "/foundation/shopHome")
public class ShopHomeActivity extends BaseMvpActivity<ShopHomeView, ShopHomePresenter> implements ShopHomeView{
    @BindView(R.id.iv_back)
    ImageButton      mIvBack;
    @BindView(R.id.tv_search)
    TextView         mTvSearch;
    @BindView(R.id.ib_shop_cart)
    ImageButton      mIbShopCart;
    @BindView(R.id.sdv_shop_bg)
    SimpleDraweeView mSdvShopBg;
    @BindView(R.id.iv_notice)
    ImageView        mIvNotice;
    @BindView(R.id.upview)
    UPMarqueeView    mUpview;
    @BindView(R.id.ll_notice)
    LinearLayout     mLlNotice;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.tv_goods_num)
    TextView         mTvGoodsNum;
    @BindView(R.id.tv_shop_name)
    TextView         mTvShopName;
    @BindView(R.id.tv_focus)
    TextView         mTvFocus;
    @BindView(R.id.recyclerview)
    RecyclerView     mRecyclerview;
    @BindView(R.id.tv_home_page)
    TextView         mTvHomePage;
    @BindView(R.id.tv_total_goods)
    TextView         mTvTotalGoods;
    @BindView(R.id.iv_shop_level)
    ImageView         mIvShopLevel;


    @Autowired
    public String shopId;
    private ShopHomeAdapter mShopHomeAdapter;
    private int mFavorite = -1;
    List<View> views = new ArrayList<>();
    private String mShop_qq;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_home;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        initRecyclerView();
        getPresenter().getShopHomePage(shopId);
    }

    private void initRecyclerView() {
        mShopHomeAdapter = new ShopHomeAdapter();
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerview.setNestedScrollingEnabled(false);
//        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
//                .type(0, R.drawable.divider_decoration_transparent_h1)
//                .create());
        mRecyclerview.setAdapter(mShopHomeAdapter);

        mShopHomeAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageBean.GoodsEntity goodsEntity = mShopHomeAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id()+"")
                    .navigation();
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_search, R.id.ib_shop_cart, R.id.tv_focus, R.id.tv_home_page, R.id.tv_total_goods, R.id.tv_seller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                ARouter.getInstance().build("/foundation/searchGoodsInShop")
                        .withString("shopId",shopId)
                        .navigation();
                break;
            case R.id.ib_shop_cart:
                ARouter.getInstance().build("/foundation/main")
                        .withString("type","shop")
                        .navigation();
                break;
            case R.id.tv_focus:
                if(mFavorite == -1)return;
                if (mFavorite == 1) {
                    getPresenter().updateShopFocus(shopId,0+"");
                } else {
                    getPresenter().updateShopFocus(shopId,1+"");
                }
                break;
            case R.id.tv_home_page:
                break;
            case R.id.tv_total_goods:
                ARouter.getInstance().build("/foundation/shopGoods")
                        .withString("shopId",shopId)
                        .navigation();
                break;
            case R.id.tv_seller:
                CommonUtils.openQQStranger(this,mShop_qq);
                break;
        }
    }

    @Override
    public void onSuccess(HomePageBean homePageBean) {
        List<HomePageBean.GoodsEntity> goods = homePageBean.getGoods();
        mFavorite = homePageBean.getFavorite();
        List<HomePageBean.NoticeEntity> notice = homePageBean.getNotice();
        HomePageBean.ShopIntroEntity shop_intro = homePageBean.getShop_intro();
        if (shop_intro != null) {

            mShop_qq = shop_intro.getShop_qq();
            mSdvShopBg.setImageURI(AppConstants.PIC_BASE_URL + shop_intro.getShop_banner());
            mSdvHead.setImageURI(AppConstants.PIC_BASE_URL + shop_intro.getShop_avatar());
            mTvShopName.setText(shop_intro.getShop_name());
            mTvGoodsNum.setText("商品"+shop_intro.getShop_number()+"  |  "+"销量"+shop_intro.getShow_number());
            switch (shop_intro.getShop_credit()) {
                case 1:
                    mIvShopLevel.setImageResource(R.drawable.ic_shop_level1);
                    break;
                case 2:
                    mIvShopLevel.setImageResource(R.drawable.ic_shop_level2);
                    break;
                case 3:
                    mIvShopLevel.setImageResource(R.drawable.ic_shop_level3);
                    break;
                case 4:
                    mIvShopLevel.setImageResource(R.drawable.ic_shop_level4);
                    break;
                case 5:
                    mIvShopLevel.setImageResource(R.drawable.ic_shop_level5);
                    break;
            }
        }
        if (mFavorite == 1) {
            mTvFocus.setTextColor(ContextCompat.getColor(this,R.color.white));
            mTvFocus.setBackgroundColor(ContextCompat.getColor(this,R.color.red));
            mTvFocus.setText("已关注");
        } else {
            mTvFocus.setTextColor(ContextCompat.getColor(this,R.color.text_333333));
            mTvFocus.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            mTvFocus.setText("加关注");
        }

        views.clear();
        for (int i = 0; i < notice.size(); i++) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_shop_broadcast, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
//            tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.ll1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(App.getAppContext(), position + "你点击了---" + notice.get(position).getNotice_message(), Toast.LENGTH_SHORT).show();
                }
            });
            //进行对控件赋值
            tv1.setText(notice.get(i).getNotice_message());
            //添加到循环滚动数组里面去
            views.add(moreView);
        }

        mUpview.setViews(views);

        if (goods != null) {
            mShopHomeAdapter.replaceData(goods);
        }
    }

    @Override
    public void updateFocus(ShopFocusBean shopFocusBean) {
        mFavorite = shopFocusBean.getFavorite();

        if (mFavorite == 1) {
            mTvFocus.setTextColor(ContextCompat.getColor(this,R.color.white));
            mTvFocus.setBackgroundColor(ContextCompat.getColor(this,R.color.red));
            mTvFocus.setText("已关注");
        } else {
            mTvFocus.setTextColor(ContextCompat.getColor(this,R.color.text_333333));
            mTvFocus.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            mTvFocus.setText("加关注");
        }
    }

    class ShopHomeAdapter extends BaseQuickAdapter<HomePageBean.GoodsEntity, BaseViewHolder> {

        public ShopHomeAdapter() {
            super(R.layout.item_shop_page);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomePageBean.GoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPrice());
        }
    }
}
