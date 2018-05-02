package com.alex.code.foundation.ui.homesearch.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.HotSearchBean;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.utils.VLog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

public class HomeSearchFragment extends BaseMvpFragment<HomeSearchView2, HomeSearchPresenter2> implements HomeSearchView2 {

    public static final String TYPE = "type";
    @BindView(R.id.flowlayout_history)
    TagFlowLayout mFlowlayoutHistory;
    @BindView(R.id.ll_history)
    LinearLayout  mLlHistory;
    @BindView(R.id.flowlayout_hot)
    TagFlowLayout mFlowlayoutHot;
    @BindView(R.id.ll_hot)
    LinearLayout  mLlHot;
    private int mType;

    @Inject
    AppDataManager mAppDataManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_search;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        mType = bundle.getInt(TYPE);

        initData();
    }

    private void initData() {
        String searchHistory = mAppDataManager.getSearchHistory();
        VLog.d("searchHistory: " + searchHistory);
        if (TextUtils.isEmpty(searchHistory)) {
            mLlHistory.setVisibility(View.GONE);
        } else {
            mLlHistory.setVisibility(View.VISIBLE);
            String[] split = searchHistory.split(",");
            mFlowlayoutHistory.setAdapter(new TagAdapter<String>(split) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView textView = (TextView) View.inflate(getContext(), R.layout.tag_search_filter, null);
                    textView.setText(o);
                    return textView;
                }
            });

            mFlowlayoutHistory.setOnTagClickListener((view, position, parent) -> {
                ARouter.getInstance().build("/foundation/secondSearch")
                        .withInt("type",mType)
                        .withString("content", split[position])
                        .navigation();
                getActivity().finish();
                return true;
            });
        }

    }

    @Override
    public void fetchData() {

        getPresenter().getHotSearchList();
    }

    public static HomeSearchFragment newInstance(int Type) {
        HomeSearchFragment homeSearchFragment = new HomeSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, Type);
        homeSearchFragment.setArguments(bundle);
        return homeSearchFragment;
    }

    @Override
    public void onSuccess(HotSearchBean hotSearchBean) {

        List<HotSearchBean.SearchEntity> searchEntities;
        if (mType == 3) {
            searchEntities = hotSearchBean.getBrand();
        } else if (mType == 2) {
            searchEntities = hotSearchBean.getShop();
        } else {
            searchEntities = hotSearchBean.getGoods();
        }

        mFlowlayoutHot.setAdapter(new TagAdapter<HotSearchBean.SearchEntity>(searchEntities) {
            @Override
            public View getView(FlowLayout parent, int position, HotSearchBean.SearchEntity o) {
                TextView textView = (TextView) View.inflate(getContext(), R.layout.tag_search_filter, null);
                textView.setText(o.getName());
                return textView;
            }
        });

        mFlowlayoutHot.setOnTagClickListener((view, position, parent) -> {
            String url = "";
            if (mType == 3) {
                url = "/foundation/secondSearch";
            } else if (mType == 2) {
                url = "/foundation/shopSearch";
            } else {
                url = "/foundation/secondSearch";
            }
            ARouter.getInstance().build(url)
                    .withInt("type", mType)
                    .withString("content", searchEntities.get(position).getName())
                    .navigation();
            getActivity().finish();
            return true;
        });


    }

    @OnClick(R.id.ll_history)
    public void onViewClicked() {
        mAppDataManager.setSearchHistory("");
        mLlHistory.setVisibility(View.GONE);
    }
}
