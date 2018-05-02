package com.alex.code.foundation.ui.focus;

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
import com.alex.code.foundation.bean.ShopBean;
import com.alex.code.foundation.bean.ShopTypeBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
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
 * Des:我的关注
 * Date: 2017/10/23.
 */

@Route(path = "/foundation/focus")
public class FocusActivity extends BaseMvpActivity<FocusView, FocusPresenter> implements FocusView {

    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_Category)
    TextView           mTvCategory;
    @BindView(R.id.checkBok)
    CheckBox           mCheckBok;
    @BindView(R.id.tv_delete)
    TextView           mTvDelete;
    @BindView(R.id.ll_bottom)
    LinearLayout       mLlBottom;

    @Inject
    ToastInstance      mToastInstance;

    private FocusAdapter mFocusAdapter;
    private CustomPopupWindow mPopupWindow;
    private ShopTypeAdapter mShopTypeAdapter;
    private boolean         isEditor;
    private String mCategoryId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_focus;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("我的关注")
                .setLeftBackListener(this::finish)
                .setRightTextListener(() -> {
                    mFocusAdapter.setType(isEditor ? 0 : 1);
                    mToolbar.setRightText(isEditor ?  "编辑" : "完成");
                    mLlBottom.setVisibility(isEditor ? View.GONE : View.VISIBLE);
                    isEditor = !isEditor;
                });

        initRecyclerView();
        initListener();
        mPopupWindow = CustomPopupWindow.builder()
                .contentView(CustomPopupWindow.inflateView(this, R.layout.popupwindow_goods_category))
                .isOutsideTouch(true)
                .isWrap(false,true)
                .backgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_f6f6f6)))
                .customListener(contentView -> initPop(contentView))
                .build();

        getPresenter().getShopList(mCategoryId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("我的关注");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("我的关注");
    }

    private void initListener() {
        mCheckBok.setOnCheckedChangeListener((buttonView, isChecked) -> mFocusAdapter.setAllCheck(isChecked));

        mFocusAdapter.setOnItemClickListener((adapter, view, position) -> {
            ShopBean.Shop shop = mFocusAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/shopHome")
                    .withString("shopId",shop.getShop_id()+"")
                    .navigation();
        });

        mFocusAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ShopBean.Shop shop = mFocusAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.checkBok:
                    shop.setSelected(!shop.isSelected());
                    break;
            }
        });
    }

    private void initRecyclerView() {

        mFocusAdapter = new FocusAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_line_h1)
                .create());
//        mRecyclerview.setAdapter(mFocusAdapter);
        mFocusAdapter.bindToRecyclerView(mRecyclerview);
        mFocusAdapter.setEmptyView(R.layout.recyclerview_empty_view);

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
                mFocusAdapter.getData().clear();
                getPresenter().getShopList(mCategoryId);
            }
        });

    }

    @OnClick({R.id.tv_delete, R.id.tv_Category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                StringBuilder sb = new StringBuilder();
                List<ShopBean.Shop> data = mFocusAdapter.getData();
                for (ShopBean.Shop shop : data) {
                    if (shop.isSelected()) {
                        sb.append(shop.getShop_id()).append(",");
                    }
                }

                VLog.d("sb.toString: "+sb.toString());
                if (!TextUtils.isEmpty(sb.toString())) {
                    getPresenter().deleteFavoriteList("shop", sb.toString());
                }
                break;
            case R.id.tv_Category:

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
        mShopTypeAdapter = new ShopTypeAdapter();
        rv.setAdapter(mShopTypeAdapter);

        mShopTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            mShopTypeAdapter.refreshItem(position);
            ShopTypeBean.ListEntity listEntity = mShopTypeAdapter.getData().get(position);
            mTvCategory.setText(listEntity.getGroup_name());
            mFocusAdapter.getData().clear();
            mCategoryId = listEntity.getId();
            getPresenter().getShopList(mCategoryId);
            mPopupWindow.dismiss();
        });

        getPresenter().getShopCategory();
    }

    @Override
    public void onSuccess(List<ShopBean.Shop> data) {
        mRefreshLayout.finishRefresh();
        mFocusAdapter.addData(data);
    }

    @Override
    public void showDeleteSuccess() {

        mRefreshLayout.autoRefresh();
        mToastInstance.showToast("删除成功");

    }

    @Override
    public void showCategorySuccess(List<ShopTypeBean.ListEntity> data) {
        ShopTypeBean.ListEntity listEntity = new ShopTypeBean.ListEntity();
        listEntity.setGroup_name("全部分类");
        listEntity.setId("");
        data.add(0,listEntity);
        mShopTypeAdapter.replaceData(data);
    }


    class ShopTypeAdapter extends BaseQuickAdapter<ShopTypeBean.ListEntity, BaseViewHolder> {

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

        public ShopTypeAdapter() {
            super(R.layout.item_goods_type);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShopTypeBean.ListEntity item) {

            helper.setText(R.id.tv_type, item.getGroup_name())
                    .setTextColor(R.id.tv_type,selectedPos == helper.getAdapterPosition() ? ContextCompat.getColor(App.getAppContext(),R.color.red) : ContextCompat.getColor(App.getAppContext(),R.color.text_system))
                    .setVisible(R.id.iv_tag, selectedPos == helper.getAdapterPosition());
        }
    }

    class FocusAdapter extends BaseQuickAdapter<ShopBean.Shop, BaseViewHolder> {

        private int mType;//是否显示checkBox

        public FocusAdapter() {
            super(R.layout.item_focus);
        }

        public void setType(int type) {
            mType = type;
            notifyDataSetChanged();
        }

        public void setAllCheck(boolean ischeck) {
            List<ShopBean.Shop> data = getData();
            for (ShopBean.Shop shop : data) {
                shop.setSelected(ischeck);
            }
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, ShopBean.Shop item) {


            helper.setGone(R.id.checkBok,mType == 1)
                    .setChecked(R.id.checkBok,item.isSelected());

            SimpleDraweeView sdv = helper.getView(R.id.sdv);
            sdv.setImageURI(AppConstants.PIC_BASE_URL + item.getShop_avatar());
            helper.setText(R.id.tv_name, item.getShop_name())
                    .addOnClickListener(R.id.checkBok);
        }
    }

}
