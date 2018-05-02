package com.alex.code.foundation.ui.hotgoods;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
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
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

@Route(path = "/foundation/hotGoods")
public class HotGoodsActivity extends BaseMvpActivity<HotGoodsView, HotGoodsPresenter> implements HotGoodsView{
    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.banner)
    Banner             mBanner;
    @BindView(R.id.rb_synth)
    RadioButton        mRbSynth;
    @BindView(R.id.rb_top_hot_hours)
    RadioButton        mRbTopHotHours;
    @BindView(R.id.rb_top_hot_total)
    RadioButton        mRbTopHotTotal;
    @BindView(R.id.radioGroup)
    RadioGroup         mRadioGroup;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int mSort     = 1;
    private int pageIndex = 1;
    private int pageNum   = 10;
    private int mTotalCount;
    private HotGoodsAdapter mHotGoodsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_goods;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("热卖商品")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        initListener();
        getPresenter().getHotGoods(mSort+"",pageIndex+"",pageNum+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("热卖商品");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("热卖商品");
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_synth:
                    mSort = 1;
                    break;
                case R.id.rb_top_hot_hours:
                    mSort = 2;
                    break;
                case R.id.rb_top_hot_total:
                    mSort = 3;
                    break;
            }
            pageIndex = 1;
            mHotGoodsAdapter.getData().clear();
            getPresenter().getHotGoods(mSort+"",pageIndex+"",pageNum+"");
        });

        mHotGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            HotGoodsBean.GoodsEntity goodsEntity = mHotGoodsAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id())
                    .navigation();
        });
    }

    private void initRecyclerView() {
        mHotGoodsAdapter = new HotGoodsAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
//                .type(0, R.drawable.divider_decoration_transparent_h1)
//                .create());
        mRecyclerview.setAdapter(mHotGoodsAdapter);

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
                getPresenter().getHotGoods(mSort+"",pageIndex+"",pageNum+"");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mHotGoodsAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getHotGoods(mSort+"",pageIndex+"",pageNum+"");
            }
        });
    }

    @Override
    public void onSuccess(HotGoodsBean hotGoodsBean) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        mTotalCount = hotGoodsBean.getTotal_count();
        List<HotGoodsBean.GoodsEntity> goods = hotGoodsBean.getGoods();
        List<HotGoodsBean.PlatformEntity> platform = hotGoodsBean.getPlatform();
        mBanner.setImages(platform).setImageLoader(new HotImageLoder()).start();
        mHotGoodsAdapter.addData(goods);
    }

    class HotImageLoder extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            HotGoodsBean.PlatformEntity platform = (HotGoodsBean.PlatformEntity) path;
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


    class HotGoodsAdapter extends BaseQuickAdapter<HotGoodsBean.GoodsEntity, BaseViewHolder> {
        public HotGoodsAdapter() {
            super(R.layout.item_shop_page);
        }

        @Override
        protected void convert(BaseViewHolder helper, HotGoodsBean.GoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPrice());
        }
    }

}
