package com.alex.code.foundation.ui.secondsearch;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.bean.SecondSearchBean;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.um.Tracker;
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
 * Date: 2017/11/13.
 */

@Route(path = "/foundation/secondSearch")
public class SecondSearchActivity extends BaseMvpActivity<SecondSearchView, SecondSearchPresenter> implements SecondSearchView {

    @BindView(R.id.iv_back)
    ImageButton        mIvBack;
    @BindView(R.id.tv_search)
    TextView           mTvSearch;
    @BindView(R.id.iv_filtrate)
    ImageView          mIvFiltrate;
    @BindView(R.id.rb_synth)
    RadioButton        mRbSynth;
    @BindView(R.id.rb_price)
    RadioButton        mRbPrice;
    @BindView(R.id.rb_sale)
    RadioButton        mRbSale;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.radioGroup)
    RadioGroup   mRadioGroup;
    @BindView(R.id.et_low_price)
    EditText     mEtLowPrice;
    @BindView(R.id.et_high_price)
    EditText     mEtHighPrice;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;


    @Autowired
    public int    type = 1;
    @Autowired
    public String content;
    @Autowired
    public String category;

    private SecondSearchAdapter mSecondSearchAdapter;
    private int mSort     = 1;
    private int pageIndex = 1;
    private int pageNum   = 10;
    private boolean isDowm;
    ArrayList<TagFlowLayout> mTagFlowLayouts = new ArrayList<>();
    private int mTotalCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_search2;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        initRecyclerView();
        initListener();

        VLog.d("content: "+content+ "----- category: "+category);
        mTvSearch.setText(TextUtils.isEmpty(content) ? category : content);

        getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "","","","",category);
        getPresenter().getSearchFilterList(type + "", content,category);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("搜索详情");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("搜索详情");
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private void initListener() {

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_synth:
                    pageIndex = 1;
                    mSort = 1;
                    mSecondSearchAdapter.getData().clear();
                    getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey(),category);
                    break;
                case R.id.rb_sale:
                    pageIndex = 1;
                    mSort = 4;
                    mSecondSearchAdapter.getData().clear();
                    getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey(),category);
                    break;
            }
        });

        mSecondSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
            SecondSearchBean.GoodsEntity goodsEntity = mSecondSearchAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goodsEntity.getGoods_id()+"")
                    .navigation();
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                VLog.d("onDrawerOpened:  ");

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                VLog.d("onDrawerClosed:  ");
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initRecyclerView() {
        mSecondSearchAdapter = new SecondSearchAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h1)
                .create());
//        mRecyclerview.setAdapter(mSecondSearchAdapter);
        mSecondSearchAdapter.bindToRecyclerView(mRecyclerview);
        mSecondSearchAdapter.setEmptyView(R.layout.recyclerview_empty_view);

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
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey(),category);
//                refreshlayout.autoLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mSecondSearchAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey(),category);
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.iv_filtrate, R.id.rb_price, R.id.tv_search,R.id.tv_reset,R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                ARouter.getInstance().build("/foundation/homeSearch")
                        .withString("category",category)
                        .navigation();
                finish();
                break;
            case R.id.iv_filtrate:
                mDrawerLayout.openDrawer(GravityCompat.END);
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
                mSecondSearchAdapter.getData().clear();
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),getFilterKey(),category);
                isDowm = !isDowm;
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
                mSecondSearchAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getSecondSearchList(type + "", content, mSort + "", pageIndex + "", pageNum + "",mEtLowPrice.getText().toString(),mEtHighPrice.getText().toString(),filterKey,category);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                VLog.d("filterKey: "+filterKey);
                break;
        }
    }

    @Override
    public void onSuccess(List<SecondSearchBean.GoodsEntity> data, int totalCount) {
        mTotalCount = totalCount;
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        mSecondSearchAdapter.addData(data == null ? new ArrayList<>() : data);
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
                    TextView textView = (TextView) View.inflate(SecondSearchActivity.this, R.layout.tag_search_filter,null);
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

    class SecondSearchAdapter extends BaseQuickAdapter<SecondSearchBean.GoodsEntity, BaseViewHolder> {

        public SecondSearchAdapter() {
            super(R.layout.item_second_search);
        }

        @Override
        protected void convert(BaseViewHolder helper, SecondSearchBean.GoodsEntity item) {

            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getMaster());
            helper.setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getPrice());
        }
    }

}
