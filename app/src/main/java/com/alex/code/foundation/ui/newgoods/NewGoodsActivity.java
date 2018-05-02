package com.alex.code.foundation.ui.newgoods;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.VLog;
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
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/15.
 */

@Route(path = "/foundation/newGoods")
public class NewGoodsActivity extends BaseMvpActivity<NewGoodsView, NewGoodsPresenter> implements NewGoodsView{

    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.banner)
    Banner             mBanner;
    @BindView(R.id.rb_recommend)
    RadioButton        mRbRecommend;
    @BindView(R.id.rb_price)
    RadioButton        mRbPrice;
    @BindView(R.id.tv_filter)
    TextView           mTvFilter;
    @BindView(R.id.radioGroup)
    RadioGroup         mRadioGroup;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_reset)
    TextView           mTvReset;
    @BindView(R.id.tv_commit)
    TextView           mTvCommit;
    @BindView(R.id.ll_bottom)
    LinearLayout       mLlBottom;
    @BindView(R.id.et_low_price)
    EditText           mEtLowPrice;
    @BindView(R.id.et_high_price)
    EditText           mEtHighPrice;
    @BindView(R.id.ll_container)
    LinearLayout       mLlContainer;
    @BindView(R.id.drawerLayout)
    DrawerLayout       mDrawerLayout;

    private int mSort     = 1;
    private int pageIndex = 1;
    private int pageNum   = 10;
    ArrayList<TagFlowLayout> mTagFlowLayouts = new ArrayList<>();
    private int mTotalCount;
    private NewGoodsAdapter mNewGoodsAdapter;
    private boolean isDowm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_goods;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("新品")
                .setLeftBackListener(this::finish);
        initRecyclerView();
        initListener();
        getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"","","","");
        getPresenter().getSearchFilterList();

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_price_dowm);
        drawable.setBounds(0, 0, DensityUtil.dip2px(10), DensityUtil.dip2px(16));
        mRbPrice.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("新品上市");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("新品上市");
    }

    private void initListener() {

        mNewGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            HotGoodsBean.GoodsEntity goodsEntity = mNewGoodsAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id())
                    .navigation();
        });
    }

    private void initRecyclerView() {

            mNewGoodsAdapter = new NewGoodsAdapter();
            mRecyclerview.setNestedScrollingEnabled(false);
            mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
//            mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
//                    .type(0, R.drawable.divider_decoration_transparent_h1)
//                    .create());
            mRecyclerview.setAdapter(mNewGoodsAdapter);

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
                    getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey());
                    //                refreshlayout.autoLoadmore();
                }

                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    mNewGoodsAdapter.getData().clear();
                    pageIndex = 1;
                    getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey());
                }
            });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.tv_filter, R.id.tv_reset, R.id.tv_commit,R.id.rb_price,R.id.rb_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.tv_reset:
                for (TagFlowLayout tagFlowLayout : mTagFlowLayouts) {
                    tagFlowLayout.setTag("");
                    tagFlowLayout.getSelectedList().clear();
                    tagFlowLayout.getAdapter().notifyDataChanged();
                }
                break;
            case R.id.tv_commit:
                String filterKey = getFilterKey();
                mNewGoodsAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey());
                mDrawerLayout.closeDrawer(GravityCompat.END);
                VLog.d("filterKey: "+filterKey);
                break;
            case R.id.rb_price:
                if (!isDowm) {
                    Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_price_up);
                    drawable.setBounds(0, 0, DensityUtil.dip2px(10), DensityUtil.dip2px(16));
                    mRbPrice.setCompoundDrawables(null, null, drawable, null);
                    mSort = 2;
                } else {
                    Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_price_dowm);
                    drawable.setBounds(0, 0, DensityUtil.dip2px(10), DensityUtil.dip2px(16));
                    mRbPrice.setCompoundDrawables(null, null, drawable, null);
                    mSort = 3;
                }
                pageIndex = 1;
                mNewGoodsAdapter.getData().clear();
                getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey());
                isDowm = !isDowm;
                break;
            case R.id.rb_recommend:
                pageIndex = 1;
                mSort = 1;
                mNewGoodsAdapter.getData().clear();
                getPresenter().getNewGoods(mSort+"",pageIndex+"",pageNum+"",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey());
                break;
        }
    }

    private String getFilterKey() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (TagFlowLayout tagFlowLayout : mTagFlowLayouts) {
            String tag = (String) tagFlowLayout.getTag();
            if (TextUtils.isEmpty(tag)) {
                //                tag = "";
                continue;
            }
            sb.append(tag).append(",");
        }

        if (sb.length() > 2) {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void onSuccess(HotGoodsBean hotGoodsBean) {
        mRefreshLayout.finishLoadmore();
        mRefreshLayout.finishRefresh();
        mTotalCount = hotGoodsBean.getTotal_count();
        List<HotGoodsBean.GoodsEntity> goods = hotGoodsBean.getGoods();
        List<HotGoodsBean.PlatformEntity> platform = hotGoodsBean.getPlatform();
        mBanner.setImages(platform).setImageLoader(new NewImageLoder()).start();
        mNewGoodsAdapter.addData(goods == null ? new ArrayList<>() : goods);
    }

    @Override
    public void showSearchFilter(List<SearchFilterBean.SearchEntity> filterData) {
        mLlContainer.removeAllViews();

        for (SearchFilterBean.SearchEntity searchEntity : filterData) {
            View view = View.inflate(this, R.layout.search_filter_view, null);
            mLlContainer.addView(view);
            TextView tvAttr = (TextView) view.findViewById(R.id.tv_attr);
            TagFlowLayout flowlayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);
            mTagFlowLayouts.add(flowlayout);

            tvAttr.setText(searchEntity.getName());

            tvAttr.setOnClickListener(v -> {
                int visibility = flowlayout.getVisibility();
                if (visibility == View.VISIBLE) {
                    flowlayout.setVisibility(View.GONE);
                } else {
                    flowlayout.setVisibility(View.VISIBLE);
                }
            });

            //            flowlayout.setMaxSelectCount(1);
            List<String> value = searchEntity.getValue();
            flowlayout.setAdapter(new TagAdapter<String>(value) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView textView = (TextView) View.inflate(NewGoodsActivity.this, R.layout.tag_search_filter,null);
                    textView.setText(o);
                    return textView;
                }

                @Override
                public void onSelected(int position, View view) {
                    super.onSelected(position, view);
                    //                    flowlayout.setTag(value.get(position));
                    //                    view.setClickable(true);
                }

                @Override
                public void unSelected(int position, View view) {
                    super.unSelected(position, view);
                    //                    flowlayout.setTag("");
                    //                    view.setClickable(false);
                }
            });

            flowlayout.setOnSelectListener(selectPosSet -> {

                StringBuilder sb = new StringBuilder();
                sb.append("\"")
                        .append(searchEntity.getKey()).append("\"")
                        .append(":").append("\"");
                for (Integer integer : selectPosSet) {
                    sb.append(value.get(integer)).append(",");
                }

                if (sb.length() > 1 && TextUtils.equals(String.valueOf(sb.charAt(sb.length()-1)),",")) {
                    sb.deleteCharAt(sb.length()-1);
                }
                sb.append("\"");
                flowlayout.setTag(sb.toString());
            });
        }
    }

    class NewImageLoder extends ImageLoader {

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

    class NewGoodsAdapter extends BaseQuickAdapter<HotGoodsBean.GoodsEntity, BaseViewHolder> {
        public NewGoodsAdapter() {
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
