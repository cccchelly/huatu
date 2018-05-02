package com.alex.code.foundation.ui.shopgoods;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.TotalShopGoodsBean;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

@Route(path = "/foundation/shopGoods")
public class ShopGoodsActivity extends BaseMvpActivity<ShopGoodsView, ShopGoodsPresenter> implements ShopGoodsView{

    @BindView(R.id.iv_back)
    ImageButton        mIvBack;
    @BindView(R.id.tv_search)
    TextView           mTvSearch;
    @BindView(R.id.ib_shop_cart)
    ImageButton        mIbShopCart;
    @BindView(R.id.rb_synth)
    RadioButton        mRbSynth;
    @BindView(R.id.rb_sale)
    RadioButton        mRbSale;
    @BindView(R.id.rb_price_down)
    RadioButton        mRbPriceDown;
    @BindView(R.id.rb_price_up)
    RadioButton        mRbPriceUp;
    @BindView(R.id.radioGroup)
    RadioGroup         mRadioGroup;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Autowired
    public String content;
    @Autowired
    public String shopId;

    private ShopGoodsAdapter mShopGoodsAdapter;
    private int mSort     = 1;
    private int pageIndex = 1;
    private int pageNum   = 10;
    private int mTotalCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_goods;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        initRecyclerView();
        initListener();
        getPresenter().getTotalShopGoods(shopId,content,mSort+"",pageIndex+"",pageNum+"");
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_synth:
                    pageIndex = 1;
                    mSort = 1;
                    break;
                case R.id.rb_sale:
                    pageIndex = 1;
                    mSort = 2;
                    break;
                case R.id.rb_price_down:
                    pageIndex = 1;
                    mSort = 3;
                    break;
                case R.id.rb_price_up:
                    pageIndex = 1;
                    mSort = 4;
                    break;
            }
            mShopGoodsAdapter.getData().clear();
            getPresenter().getTotalShopGoods(shopId,content,mSort+"",pageIndex+"",pageNum+"");
        });

        mShopGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            TotalShopGoodsBean.GoodsEntity goodsEntity = mShopGoodsAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id()+"")
                    .navigation();
        });
    }

    private void initRecyclerView() {
        mShopGoodsAdapter = new ShopGoodsAdapter();
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
//                .type(0, R.drawable.divider_decoration_transparent_h1)
//                .create());
        mRecyclerview.setAdapter(mShopGoodsAdapter);

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
                getPresenter().getTotalShopGoods(shopId,content,mSort+"",pageIndex+"",pageNum+"");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mShopGoodsAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getTotalShopGoods(shopId,content,mSort+"",pageIndex+"",pageNum+"");
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_search,R.id.ib_shop_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                ARouter.getInstance().build("/foundation/searchGoodsInShop").navigation();
                finish();
                break;
            case R.id.ib_shop_cart:
                ARouter.getInstance().build("/foundation/main")
                        .withString("type","shop")
                        .navigation();
                break;
        }
    }

    @Override
    public void onSuccess(TotalShopGoodsBean totalShopGoodsBean) {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();

        mTotalCount = totalShopGoodsBean.getTotal_count();
        List<TotalShopGoodsBean.GoodsEntity> goods = totalShopGoodsBean.getGoods();
        mShopGoodsAdapter.addData(goods == null ? new ArrayList<>() : goods);
    }

    class ShopGoodsAdapter extends BaseQuickAdapter<TotalShopGoodsBean.GoodsEntity, BaseViewHolder> {

        public ShopGoodsAdapter() {
            super(R.layout.item_shop_page);
        }

        @Override
        protected void convert(BaseViewHolder helper, TotalShopGoodsBean.GoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPrice());
        }
    }

}
