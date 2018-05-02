package com.alex.code.foundation.main.category;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.HomeAdapter;
import com.alex.code.foundation.adapter.MenuAdapter;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.CategoreGroupBean;
import com.alex.code.foundation.view.CustomToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class CategoryFragment extends BaseMvpFragment<CategoryView, CategoryPresenter> implements CategoryView {


    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.lv_menu)
    ListView      mLvMenu;
    @BindView(R.id.lv_home)
    ListView      mLvHome;
    @BindView(R.id.tv_titile)
    TextView      mTvTitile;

    private List<CategoreGroupBean> mDatas = new ArrayList<>();
    private List<Integer> titleData = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("分类");

        initListView();
        initListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isDataInitiated = false; //当前页面可见时强制刷新数据
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void fetchData() {
        getPresenter().getCategoryInfo();
    }


    private void initListView() {

        menuAdapter = new MenuAdapter(getContext(), mDatas);
        mLvMenu.setAdapter(menuAdapter);

        homeAdapter = new HomeAdapter(getContext(), mDatas);
        mLvHome.setAdapter(homeAdapter);

    }

    private void initListener() {
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                mTvTitile.setText(mDatas.get(position).getCategory_name());
                mLvHome.setSelection(titleData.get(position));
            }
        });

        mLvHome.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = titleData.indexOf(firstVisibleItem);
                //				mLvHome.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    mTvTitile.setText(mDatas.get(currentItem).getCategory_name());
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }


    @Override
    public void showCategoryData(List<CategoreGroupBean> data) {
        mDatas = data;
        menuAdapter.setData(mDatas);
        homeAdapter.setData(mDatas);
        for (int i = 0; i < data.size(); i++) {
            titleData.add(i);
        }
        mTvTitile.setText(data.get(0).getCategory_name());
    }

}
