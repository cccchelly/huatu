package com.alex.code.foundation.ui.footprint;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.FootPrintBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.um.Tracker;
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
 * Des: 我的足迹
 * Date: 2017/10/23.
 */

@Route(path = "/foundation/footprint")
public class FootprintActivity extends BaseMvpActivity<FootpintView, FootprintPresenter> implements FootpintView {

    @BindView(R.id.checkBok)
    CheckBox           mCheckBok;
    @BindView(R.id.tv_delete)
    TextView           mTvDelete;
    @BindView(R.id.ll_bottom)
    LinearLayout       mLlBottom;
    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    ToastInstance mToastInstance;
    private FootPrintAdapter mFootPrintAdapter;
    private List<FootPrintBean.HistoryEntity> mData;
    private boolean isEditor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("我的足迹")
                .setLeftBackListener(this::finish)
                .setRightTextListener(() -> {
                    mFootPrintAdapter.setType(isEditor ? 0 : 1);
                    mToolbar.setRightText(isEditor ?  "编辑" : "完成");
                    mLlBottom.setVisibility(isEditor ? View.GONE : View.VISIBLE);
                    isEditor = !isEditor;
                });

        initRecyclerView();
        initListener();
        getPresenter().getFootPrint();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("我的足迹");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("我的足迹");
    }

    private void initListener() {

        mCheckBok.setOnCheckedChangeListener((buttonView, isChecked) -> mFootPrintAdapter.setAllCheck(isChecked));

        mFootPrintAdapter.setOnItemClickListener((adapter, view, position) -> {
            FootPrintBean.HistoryEntity historyEntity = mFootPrintAdapter.getData().get(position);
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",historyEntity.getGoods_id()+"")
                    .navigation();
        });

        mFootPrintAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FootPrintBean.HistoryEntity historyEntity = mFootPrintAdapter.getData().get(position);
            switch (view.getId()) {
                case R.id.checkBok:
                    historyEntity.setSelected(!historyEntity.isSelected());
//                    mFootPrintAdapter.notifyItemChanged(position);
                    break;
            }
        });
    }

    private void initRecyclerView() {

        mFootPrintAdapter = new FootPrintAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_line_h1)
                .create());
//        mRecyclerview.setAdapter(mFootPrintAdapter);
        mFootPrintAdapter.bindToRecyclerView(mRecyclerview);
        mFootPrintAdapter.setEmptyView(R.layout.recyclerview_empty_view);

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
                mFootPrintAdapter.getData().clear();
                getPresenter().getFootPrint();
            }
        });


    }

    @OnClick(R.id.tv_delete)
    public void onViewClicked() {

        StringBuilder sb = new StringBuilder();
        List<FootPrintBean.HistoryEntity> data = mFootPrintAdapter.getData();
        for (FootPrintBean.HistoryEntity historyEntity  : data) {
            if (historyEntity.isSelected()) {
                sb.append(historyEntity.getGoods_id()).append(",");
            }
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            getPresenter().deleteFootPrint(sb.toString());
        }
    }

    @Override
    public void onSuccess(List<FootPrintBean.HistoryEntity> data) {
        mRefreshLayout.finishRefresh();
        mData = data;
        mFootPrintAdapter.addData(mData);
    }

    @Override
    public void showDeleteSuccess() {
        mToastInstance.showToast("删除成功");
        mRefreshLayout.autoRefresh();
    }

    class FootPrintAdapter extends BaseQuickAdapter<FootPrintBean.HistoryEntity, BaseViewHolder> {

        private int mType;//是否显示checkBox

        public FootPrintAdapter() {
            super(R.layout.item_footprint);
        }

        public void setType(int type) {
            mType = type;
            notifyDataSetChanged();
        }

        public void setAllCheck(boolean ischeck) {
            List<FootPrintBean.HistoryEntity> data = getData();
            for (FootPrintBean.HistoryEntity historyEntity : data) {
                historyEntity.setSelected(ischeck);
            }
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, FootPrintBean.HistoryEntity item) {

            helper.setGone(R.id.checkBok,mType == 1)
                    .setChecked(R.id.checkBok,item.isSelected());

            int position = helper.getAdapterPosition();

            String updated_at = mData.get(position).getUpdated_at();
            String date = updated_at.substring(0, 10);
            if (position == 0) {
                helper.setText(R.id.tv_date, date)
                        .setGone(R.id.tv_date, true);
            } else {
                String oldUpdated_at = mData.get(position - 1).getUpdated_at();
                String oldDate = oldUpdated_at.substring(0, 10);
                if (!TextUtils.equals(date, oldDate)) {
                    helper.setText(R.id.tv_date, date)
                            .setGone(R.id.tv_date, true);
                } else {
                    helper.setGone(R.id.tv_date, false);
                }
            }

            SimpleDraweeView sdv = helper.getView(R.id.sdv);
            sdv.setImageURI(AppConstants.PIC_BASE_URL+item.getPic_cover_micro());

            helper.setText(R.id.tv_price,"￥"+item.getPrice())
                    .setText(R.id.tv_name,item.getGoods_name())
                    .addOnClickListener(R.id.checkBok);

        }
    }
}
