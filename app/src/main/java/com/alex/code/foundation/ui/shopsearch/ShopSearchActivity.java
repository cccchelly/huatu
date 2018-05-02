package com.alex.code.foundation.ui.shopsearch;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.SecondSearchBean;
import com.alibaba.android.arouter.facade.annotation.Autowired;
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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

@Route(path = "/foundation/shopSearch")
public class ShopSearchActivity extends BaseMvpActivity<ShopSearchView, ShopSearchPresenter> implements ShopSearchView{

    @BindView(R.id.iv_back)
    ImageButton        mIvBack;
    @BindView(R.id.tv_search)
    TextView           mTvSearch;
    @BindView(R.id.iv_msg)
    ImageView          mIvMsg;
    @BindView(R.id.rb_synth)
    RadioButton        mRbSynth;
    @BindView(R.id.rb_credit)
    RadioButton        mRbCredit;
    @BindView(R.id.rb_sales)
    RadioButton        mRbSales;
    @BindView(R.id.radioGroup)
    RadioGroup         mRadioGroup;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Autowired
    public int    type;
    @Autowired
    public String content;
    @Autowired
    public String category;

    private int mSort     = 1;
    private int pageIndex = 1;
    private int pageNum   = 10;
    private int mTotalCount;
    private ShopSearchAdapter mShopSearchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_search;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        initRecyclerVeiw();
        initListener();
        mTvSearch.setText(content);
        getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_synth:
                    pageIndex = 1;
                    mSort = 1;
                    mShopSearchAdapter.getData().clear();
                    getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
                    break;
                case R.id.rb_credit:
                    pageIndex = 1;
                    mSort = 2;
                    mShopSearchAdapter.getData().clear();
                    getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
                    break;
                case R.id.rb_sales:
                    pageIndex = 1;
                    mSort = 3;
                    mShopSearchAdapter.getData().clear();
                    getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
                    break;
            }
        });

        mShopSearchAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SecondSearchBean.ShopEntity shopEntity = (SecondSearchBean.ShopEntity) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.rv_shop:
                    ARouter.getInstance().build("/foundation/shopHome")
                            .withString("shopId",shopEntity.getShop_id()+"")
                            .navigation();
                    break;
                case R.id.sdv_pic1:
                    ARouter.getInstance().build("/foundation/goodsDetail")
                            .withString("goodsId",shopEntity.getGoods_intro().get(0).getGoods_id()+"")
                            .navigation();
                    break;
                case R.id.sdv_pic2:
                    ARouter.getInstance().build("/foundation/goodsDetail")
                            .withString("goodsId",shopEntity.getGoods_intro().get(1).getGoods_id()+"")
                            .navigation();
                    break;
                case R.id.sdv_pic3:
                    ARouter.getInstance().build("/foundation/goodsDetail")
                            .withString("goodsId",shopEntity.getGoods_intro().get(2).getGoods_id()+"")
                            .navigation();
                    break;
            }
        });
    }

    private void initRecyclerVeiw() {
        mShopSearchAdapter = new ShopSearchAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
//        mRecyclerview.setAdapter(mShopSearchAdapter);
        mShopSearchAdapter.bindToRecyclerView(mRecyclerview);
        mShopSearchAdapter.setEmptyView(R.layout.recyclerview_empty_view);

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
                //                refreshlayout.finishLoadmore(2000);
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mShopSearchAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                ARouter.getInstance().build("/foundation/homeSearch").navigation();
                finish();
                break;
            case R.id.iv_msg:
                break;
        }
    }

    @Override
    public void onSuceess(List<SecondSearchBean.ShopEntity> data, int totalCount) {
        mTotalCount = totalCount;
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        if (data != null) {
            mShopSearchAdapter.addData(data);
        }
    }

    class ShopSearchAdapter extends BaseQuickAdapter<SecondSearchBean.ShopEntity, BaseViewHolder> {

        public ShopSearchAdapter() {
            super(R.layout.item_shop_search);
        }

        @Override
        protected void convert(BaseViewHolder helper, SecondSearchBean.ShopEntity item) {

            SimpleDraweeView sdvHead = helper.getView(R.id.sdv_head);
            SimpleDraweeView sdvPic1 = helper.getView(R.id.sdv_pic1);
            SimpleDraweeView sdvPic2 = helper.getView(R.id.sdv_pic2);
            SimpleDraweeView sdvPic3 = helper.getView(R.id.sdv_pic3);

            sdvHead.setImageURI(AppConstants.PIC_BASE_URL + item.getShop_avatar());
            helper.setText(R.id.tv_shop_name, item.getShop_name())
                    .setText(R.id.tv_sales, "销量" + item.getShow_number() + "  共" + item.getShop_number() + "件宝贝")
                    .addOnClickListener(R.id.rv_shop);

            helper.setVisible(R.id.rl1,true)
                    .setVisible(R.id.rl2,true)
                    .setVisible(R.id.rl3,true);
            try {
                List<SecondSearchBean.ShopEntity.GoodsIntroEntity> goods_intro = item.getGoods_intro();

                if (TextUtils.isEmpty(goods_intro.get(0).getGoods_id())) {
                    helper.setVisible(R.id.rl1, false);
                } else {
                    sdvPic1.setImageURI(AppConstants.PIC_BASE_URL + goods_intro.get(0).getMaster());
                    helper.setVisible(R.id.rl1, true)
                            .setText(R.id.tv_price1,"￥"+goods_intro.get(0).getPrice())
                            .addOnClickListener(R.id.sdv_pic1);
                }

                if (TextUtils.isEmpty(goods_intro.get(1).getGoods_id())) {
                    helper.setVisible(R.id.rl2, false);
                } else {
                    sdvPic2.setImageURI(AppConstants.PIC_BASE_URL + goods_intro.get(1).getMaster());
                    helper.setVisible(R.id.rl2, true)
                            .setText(R.id.tv_price2,"￥"+goods_intro.get(1).getPrice())
                            .addOnClickListener(R.id.sdv_pic2);
                }

                if (TextUtils.isEmpty(goods_intro.get(2).getGoods_id())) {
                    helper.setVisible(R.id.rl3, false);
                } else {
                    sdvPic3.setImageURI(AppConstants.PIC_BASE_URL + goods_intro.get(2).getMaster());
                    helper.setVisible(R.id.rl3, true)
                            .setText(R.id.tv_price3,"￥"+goods_intro.get(2).getPrice())
                            .addOnClickListener(R.id.sdv_pic3);
                }

                //                helper.setText(R.id.tv_price1,"￥"+goods_intro.get(0).getPrice())
//                        .setText(R.id.tv_price2,"￥"+goods_intro.get(1).getPrice())
//                        .setText(R.id.tv_price3,"￥"+goods_intro.get(2).getPrice())
//                        .addOnClickListener(R.id.sdv_pic1)
//                        .addOnClickListener(R.id.sdv_pic2)
//                        .addOnClickListener(R.id.sdv_pic3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
