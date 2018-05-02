package com.alex.code.foundation.ui.favorite;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.GoodsTypeBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomPopupWindow;
import com.alex.code.foundation.view.CustomToolBar;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des: 我的收藏
 * Date: 2017/10/23.
 */

@Route(path = "/foundation/favorite")
public class FavoriteActivity extends BaseMvpActivity<FavoriteView, FavoritePresenter> implements FavoriteView {

    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.checkBok)
    CheckBox           mCheckBok;
    @BindView(R.id.tv_delete)
    TextView           mTvDelete;
    @BindView(R.id.ll_bottom)
    LinearLayout       mLlBottom;
    @BindView(R.id.tv_Category)
    TextView           mTvCategory;
    private FavoriteAdapter mFavoriteAdapter;
    private boolean isEditor;

    @Inject
    ToastInstance mToastInstance;
    private GoodsTypeAdapter mGoodsTypeAdapter;
    private CustomPopupWindow mPopupWindow;
    private String mCategoryId = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("商品收藏")
                .setLeftBackListener(this::finish)
                .setRightTextListener(() -> {
                    mFavoriteAdapter.setType(isEditor ? 0 : 1);
                    mToolbar.setRightText(isEditor ?  "编辑" : "完成");
                    mLlBottom.setVisibility(isEditor ? View.GONE : View.VISIBLE);
                    isEditor = !isEditor;
                });

        initRecyclerView();

        mPopupWindow = CustomPopupWindow.builder()
                .contentView(CustomPopupWindow.inflateView(this, R.layout.popupwindow_goods_category))
                .isOutsideTouch(true)
                .isWrap(false,true)
                .backgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_f6f6f6)))
                .customListener(contentView -> initPop(contentView))
                .build();

        initListener();
        getPresenter().getGoodsList(mCategoryId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("商品收藏");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("商品收藏");
    }

    private void initListener() {
        mCheckBok.setOnCheckedChangeListener((buttonView, isChecked) -> mFavoriteAdapter.setAllCheck(isChecked));
        mFavoriteAdapter.setOnItemClickListener((adapter, view, position) -> {
            FavoriteBean.Goods goods = mFavoriteAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",goods.getGoods_id()+"")
                    .navigation();
        });

        mFavoriteAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FavoriteBean.Goods goods = mFavoriteAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.checkBok:
                    goods.setSelected(!goods.isSelected());
//                    mFavoriteAdapter.notifyItemChanged(position);
                    break;
            }
        });
    }

    private void initRecyclerView() {

        mFavoriteAdapter = new FavoriteAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_line_h1)
                .create());
//        mRecyclerview.setAdapter(mFavoriteAdapter);
        mFavoriteAdapter.bindToRecyclerView(mRecyclerview);
        mFavoriteAdapter.setEmptyView(R.layout.recyclerview_empty_view);

        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mFavoriteAdapter.getData().clear();
                getPresenter().getGoodsList(mCategoryId);
            }
        });


    }

    @OnClick({R.id.tv_delete, R.id.tv_Category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                StringBuilder sb = new StringBuilder();
                List<FavoriteBean.Goods> data = mFavoriteAdapter.getData();
                for (FavoriteBean.Goods goods : data) {
                    if (goods.isSelected()) {
                        sb.append(goods.getGoods_id()).append(",");
                    }
                }
                if (!TextUtils.isEmpty(sb.toString())) {
                    getPresenter().deleteFavoriteList("goods", sb.toString());
                }
                break;
            case R.id.tv_Category:
                //                        .parentView(mTvCategory)

                mPopupWindow.showAsDropDown(mTvCategory);

                break;
            default:
        }
    }

    private void initPop(View contentView) {
        RecyclerView rv = (RecyclerView) contentView.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        rv.addItemDecoration(ItemDecorations.vertical(contentView.getContext())
                .type(0, R.drawable.divider_decoration_line_h1)
                .create());
        mGoodsTypeAdapter = new GoodsTypeAdapter();
        rv.setAdapter(mGoodsTypeAdapter);

        mGoodsTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            mGoodsTypeAdapter.refreshItem(position);
            GoodsTypeBean.ListEntity item = (GoodsTypeBean.ListEntity) adapter.getItem(position);
            mTvCategory.setText(item.getCategory_name());
            mFavoriteAdapter.getData().clear();
            mCategoryId = item.getId();
            getPresenter().getGoodsList(mCategoryId);
            mPopupWindow.dismiss();
        });

        getPresenter().getGoodsCategory();
    }

    @Override
    public void onSuccess(List<FavoriteBean.Goods> data) {

        mRefreshLayout.finishRefresh();
        if (data != null) {
            mFavoriteAdapter.addData(data);
        }
    }

    @Override
    public void showDeleteSuccess() {
        mRefreshLayout.autoRefresh();
        mToastInstance.showToast("删除成功");
    }

    @Override
    public void showCategorySuccess(List<GoodsTypeBean.ListEntity> data) {
        GoodsTypeBean.ListEntity listEntity = new GoodsTypeBean.ListEntity();
        listEntity.setCategory_name("全部分类");
        listEntity.setId("");
        data.add(0,listEntity);
        mGoodsTypeAdapter.replaceData(data);
    }


    class GoodsTypeAdapter extends BaseQuickAdapter<GoodsTypeBean.ListEntity, BaseViewHolder> {

        private int selectedPos = 0; // 默认选中0
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

        public GoodsTypeAdapter() {
            super(R.layout.item_goods_type);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsTypeBean.ListEntity item) {

            helper.setText(R.id.tv_type, item.getCategory_name())
                    .setTextColor(R.id.tv_type,selectedPos == helper.getAdapterPosition() ? ContextCompat.getColor(App.getAppContext(),R.color.red) : ContextCompat.getColor(App.getAppContext(),R.color.text_system))
                    .setVisible(R.id.iv_tag, selectedPos == helper.getAdapterPosition());
        }
    }

    class FavoriteAdapter extends BaseQuickAdapter<FavoriteBean.Goods, BaseViewHolder> {

        private int mType;//是否显示checkBox

        public FavoriteAdapter() {
            super(R.layout.item_favorite);
        }

        public void setType(int type) {
            mType = type;
            notifyDataSetChanged();
        }

        public void setAllCheck(boolean ischeck) {
            List<FavoriteBean.Goods> data = getData();
            for (FavoriteBean.Goods goods : data) {

                goods.setSelected(ischeck);
            }
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, FavoriteBean.Goods item) {

            helper.setGone(R.id.checkBok,mType == 1)
                    .setChecked(R.id.checkBok,item.isSelected());

            SimpleDraweeView sdv = helper.getView(R.id.sdv);
            sdv.setImageURI(AppConstants.PIC_BASE_URL+item.getPic_cover_micro());

            helper.setText(R.id.tv_price, item.getPrice())
                    .setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_attr, item.getIntroduction())
                    .addOnClickListener(R.id.checkBok);

        }
    }
}
