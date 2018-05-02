package com.alex.code.foundation.ui.details.goods;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.CommentPicAdapter;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.GoodsDetailBean;
import com.alex.code.foundation.bean.ImagesEntity;
import com.alex.code.foundation.ui.details.GoodsDetailActivity;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.FrescoImageLoder;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.WebViewUtils;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.GridViewForScrollView;
import com.alex.code.foundation.view.Page;
import com.alex.code.foundation.view.PageContainer;
import com.alex.code.foundation.view.SnapUpCountDownTimerView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

public class GoodsFragment extends BaseMvpFragment<GoodsView, GoodsPresenter> implements GoodsView {

    @BindView(R.id.banner)
    Banner                   mBanner;
    @BindView(R.id.pageOne)
    Page                     mPageOne;
    @BindView(R.id.pageTwo)
    Page                     mPageTwo;
    @BindView(R.id.container)
    PageContainer            mContainer;
    @BindView(R.id.tv_long_name)
    TextView                 mTvLongName;
    @BindView(R.id.tv_share)
    TextView                 mTvShare;
    @BindView(R.id.tv_short_name)
    TextView              mTvShortName;
    @BindView(R.id.tv_price)
    TextView              mTvPrice;
    @BindView(R.id.tv_old_price)
    TextView              mTvOldPrice;
    @BindView(R.id.tv_express_fee)
    TextView              mTvExpressFee;
    @BindView(R.id.tv_sales)
    TextView              mTvSales;
    @BindView(R.id.tv_ship_address)
    TextView              mTvShipAddress;
    @BindView(R.id.tv_sum_comment)
    TextView              mTvSumComment;
    @BindView(R.id.sdv_head)
    SimpleDraweeView      mSdvHead;
    @BindView(R.id.tv_name)
    TextView              mTvName;
    @BindView(R.id.tv_date)
    TextView              mTvDate;
    @BindView(R.id.tv_color)
    TextView              mTvColor;
    @BindView(R.id.tv_attrs)
    TextView              mTvAttrs;
    @BindView(R.id.gridView)
    GridViewForScrollView mGridView;
    @BindView(R.id.tv_more_comment)
    TextView              mTvMoreComment;
    @BindView(R.id.tv_comment)
    TextView              mTvComment;
    @BindView(R.id.ll_comment_container)
    LinearLayout          mLlCommentContainer;
    @BindView(R.id.webView)
    WebView               mWebView;
    @BindView(R.id.rv_recommend)
    RecyclerView          mRvRecommend;
    @BindView(R.id.sdv_shop_logo)
    SimpleDraweeView mSdvShopLogo;
    @BindView(R.id.tv_shop_name)
    TextView         mTvShopName;
    @BindView(R.id.tv_shop_level)
    TextView         mTvShopLevel;
    @BindView(R.id.tv_focus_num)
    TextView                 mTvFocusNum;
    @BindView(R.id.tv_goods_num)
    TextView                 mTvGoodsNum;
    @BindView(R.id.tv_logistics_level)
    TextView                 mTvLogisticsLevel;
    @BindView(R.id.tv_goods_level)
    TextView                 mTvGoodsLevel;
    @BindView(R.id.tv_service_level)
    TextView                 mTvServiceLevel;
    @BindView(R.id.tv_new_money)
    TextView                 mTvNewMoney;
    @BindView(R.id.tv_old_money)
    TextView                 mTvOldMoney;
    @BindView(R.id.timerView)
    SnapUpCountDownTimerView mTimerView;
    @BindView(R.id.ll_time_container)
    LinearLayout             mLlTimeContainer;
    @BindView(R.id.ll_contact_kefu)
    LinearLayout             mLlContactKefu;
    @BindView(R.id.ll_enter_shop)
    LinearLayout             mLlEnterShop;
    @BindView(R.id.ll_shop)
    LinearLayout             mLlShop;
    @BindView(R.id.rb_recommend)
    RadioButton              mRbRecommend;
    @BindView(R.id.rb_top)
    RadioButton              mRbTop;
    @BindView(R.id.radioGroup)
    RadioGroup               mRadioGroup;

    @Inject
    ToastInstance mToastInstance;
    public static final String GOODS_ID = "goods_id";

    private ArrayList<String> list = new ArrayList<>();
    private CommentPicAdapter mCommentPicAdapter;
    private int lastPage = 0;
    private String                                       mGoodsId;
    private RecommendAdapter                             mRecommendAdapter;
    private List<GoodsDetailBean.GoodsRecommend.ADGoods> mRec_goods;
    private List<GoodsDetailBean.GoodsRecommend.ADGoods> mSen_goods;
    private String mShop_qq;
    private String mShop_id;
    private String mShare_url;
    private GoodsDetailBean.GoodsDetailEntity mGoods_detail;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void init() {

        Bundle bundle = getArguments();
        mGoodsId = bundle.getString(GOODS_ID);

        mContainer.setOnPageChangeListener(page -> {
            if (page == 1 && page != lastPage) {
                ((GoodsDetailActivity) getActivity()).setTabVisibility(View.VISIBLE);
            } else if (page == 2 && page != lastPage) {
                ((GoodsDetailActivity) getActivity()).setTabVisibility(View.GONE);
            }

            lastPage = page;
        });

        initRecyclerView();
        initListener();
        mCommentPicAdapter = new CommentPicAdapter(getContext());
        mGridView.setAdapter(mCommentPicAdapter);
        WebViewUtils.initWebView(getContext(), mWebView);

    }

    public static GoodsFragment newInstance(String goodsId) {
        GoodsFragment goodsFragment = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GOODS_ID, goodsId);
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }

    @Override
    public void fetchData() {
        getPresenter().getGoodsDetail(mGoodsId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimerView != null) {
            mTimerView.stop();
        }
    }

    private void initRecyclerView() {

        mRecommendAdapter = new RecommendAdapter();
        mRvRecommend.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        mRvRecommend.addItemDecoration(ItemDecorations.vertical(getContext())
//                .type(0, R.drawable.divider_decoration_transparent_h1)
//                .create());
        mRvRecommend.setAdapter(mRecommendAdapter);
        mRvRecommend.setNestedScrollingEnabled(false);
    }

    private void initListener() {

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_recommend:
                    if (mRec_goods != null) {
                        mRecommendAdapter.replaceData(mRec_goods);
                    }
                    break;
                case R.id.rb_top:
                    if (mSen_goods != null) {
                        mRecommendAdapter.replaceData(mSen_goods);
                    }
                    break;
            }
        });

        mRecommendAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsDetailBean.GoodsRecommend.ADGoods adGoods = mRecommendAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",adGoods.getGoods_id()+"")
                    .navigation();
        });
    }

    @Override
    public void onSuccess(GoodsDetailBean goodsDetailBean) {
        int evaluates_count = goodsDetailBean.getEvaluates_count();//评论总数
        String webUrl = AppConstants.API_BASE_URL + goodsDetailBean.getUrl();//商品详情url


        VLog.d("webUrl: " + goodsDetailBean.getUrl());
        mWebView.loadUrl(goodsDetailBean.getUrl());
        GoodsDetailBean.GoodsRecommend goods_list_tj = goodsDetailBean.getGoods_list_tj();
        if (goods_list_tj != null) {
            mRec_goods = goods_list_tj.getRec_goods();
            mSen_goods = goods_list_tj.getSen_goods();
            if (mRec_goods != null) {
                mRecommendAdapter.replaceData(mRec_goods);
            }
        }

        GoodsDetailBean.ShopInfo shop_info = goodsDetailBean.getShop_info();
        if (shop_info != null) {

            mSdvShopLogo.setImageURI(AppConstants.PIC_BASE_URL + shop_info.getShop_avatar());
            mTvShopName.setText(shop_info.getShop_name());
            mTvShopLevel.setText("综合评分: "+shop_info.getShop_syn());
            mTvFocusNum.setText(shop_info.getShop_atte());
            mTvGoodsNum.setText(shop_info.getGoods_number());
            CommonUtils.setTwoTextColor("物流: "+shop_info.getGrade_wu(),shop_info.getGrade_wu(), Color.RED,mTvLogisticsLevel);
            CommonUtils.setTwoTextColor("商品: "+shop_info.getGoods_gra(),shop_info.getGoods_gra(), Color.RED,mTvGoodsLevel);
            CommonUtils.setTwoTextColor("店铺: "+shop_info.getShop_gra(),shop_info.getShop_gra(), Color.RED,mTvServiceLevel);

        }
        mGoods_detail = goodsDetailBean.getGoods_detail();
        if (mGoods_detail != null) {
            ((GoodsDetailActivity) getActivity()).setGoodsDetailBean(mGoods_detail);
            if (mGoods_detail.getGoods_type() == 1) {
                mLlTimeContainer.setVisibility(View.VISIBLE);
                mTvNewMoney.setText("￥" + mGoods_detail.getPromotion_price());
                mTvOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                mTvOldMoney.setText(String.format(getString(R.string.goods_old_money), mGoods_detail.getPrice() + ""));
                long goods_time = mGoods_detail.getGoods_time();
                int hours = (int) ((goods_time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                int minutes = (int) ((goods_time % (1000 * 60 * 60)) / (1000 * 60));
                int seconds = (int) ((goods_time % (1000 * 60)) / 1000);
                mTimerView.setTime(hours,minutes,seconds);
                mTimerView.start();
            } else {
                mLlTimeContainer.setVisibility(View.GONE);
            }

            mShare_url = mGoods_detail.getShare_url();
            mShop_qq = mGoods_detail.getShop_qq();
            mShop_id = mGoods_detail.getShop_id();
            mTvLongName.setText(mGoods_detail.getGoods_name());
            mTvShortName.setText(mGoods_detail.getIntroduction());
            mTvPrice.setText("￥" + mGoods_detail.getPromotion_price());//商品价格
            mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTvOldPrice.setText(String.format(getString(R.string.goods_old_money), mGoods_detail.getPrice() + ""));//商品原价
            mTvExpressFee.setText(String.format(getString(R.string.goods_express_fee), mGoods_detail.getShipping_fee()));//快递费
            mTvSales.setText("月销量" + mGoods_detail.getSales());
            mTvShipAddress.setText(mGoods_detail.getShop_site());//发货地
            mTvSumComment.setText("宝贝评价(" + evaluates_count + ")");
            List<GoodsDetailBean.GoodsDetailEntity.ImgListEntity> img_list = mGoods_detail.getImg_list();//banner图片
            list.clear();
            if (img_list != null) {

                for (GoodsDetailBean.GoodsDetailEntity.ImgListEntity imgListEntity : img_list) {
                    list.add(AppConstants.PIC_BASE_URL + imgListEntity.getPic_cover());
                }
            }
            mBanner.setImages(list).setImageLoader(new FrescoImageLoder()).start();

            GoodsDetailBean.GoodsDetailEntity.EvaluatesInfoEntity evaluates_info = mGoods_detail.getEvaluates_info();

            if (evaluates_info != null && evaluates_info.getType() == 1) {
                mLlCommentContainer.setVisibility(View.VISIBLE);
                mSdvHead.setImageURI(AppConstants.PIC_BASE_URL + evaluates_info.getUser_headimg());
                mTvName.setText(evaluates_info.getMember_name());
                mTvDate.setText(evaluates_info.getAddtime());
                mTvColor.setText(evaluates_info.getGoods_spe());
                mTvComment.setText(evaluates_info.getContent());

                List<ImagesEntity> image = evaluates_info.getImage();

                mCommentPicAdapter.setData(image);

            } else {
                mLlCommentContainer.setVisibility(View.GONE);
            }
        }
    }


    @OnClick({R.id.tv_share, R.id.tv_more_comment,R.id.ll_contact_kefu,R.id.ll_enter_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                ShareWeb("https://mobile.umeng.com/images/pic/home/social/img-1.png");
                Toast.makeText(App.getAppContext(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_comment:
                EventBus.getDefault().post(new MessageEvent<>(EventCons.SWITCH_COMMENT));
                break;
            case R.id.ll_contact_kefu:
                if (TextUtils.isEmpty(mShop_qq)) {
                    return;
                }
                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin="+mShop_qq;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    mToastInstance.showToast("启动QQ失败");
                }
                break;
            case R.id.ll_enter_shop:
                if (!TextUtils.isEmpty(mShop_id)) {
                    ARouter.getInstance().build("/foundation/shopHome")
                            .withString("shopId",mShop_id)
                            .navigation();
                }
                break;
        }
    }

    private void ShareWeb(String thumb_img) {
        UMImage thumb = new UMImage(getActivity(), R.mipmap.ic_launcher);
        UMWeb web = new UMWeb(TextUtils.isEmpty(mShare_url) ? "http://mobile.umeng.com/social" : mShare_url);
        web.setThumb(thumb);
        web.setDescription(mGoods_detail == null ? "nice to meet you" : mGoods_detail.getGoods_name());
        web.setTitle("商品分享");
        new ShareAction(getActivity()).withMedia(web)
                //                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                        VLog.d("share_media: " + share_media);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        VLog.d("onError: " + share_media);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        VLog.d("onCancel: " + share_media);

                    }
                })
                //                .share()
                .open();
    }


    class RecommendAdapter extends BaseQuickAdapter<GoodsDetailBean.GoodsRecommend.ADGoods, BaseViewHolder> {

        public RecommendAdapter() {
            super(R.layout.item_hot_goods);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsDetailBean.GoodsRecommend.ADGoods item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getPic_cover());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPromotion_price());
        }
    }
}
