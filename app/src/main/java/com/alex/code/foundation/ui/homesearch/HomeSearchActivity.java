package com.alex.code.foundation.ui.homesearch;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.MyFragmentPagerAdapter;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.ui.homesearch.fragment.HomeSearchFragment;
import com.alex.code.foundation.utils.ToastInstance;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

@Route(path = "/foundation/homeSearch")
public class HomeSearchActivity extends BaseMvpActivity<HomeSearchView, HomeSearchPresenter> {

    @BindView(R.id.et_search)
    EditText  mEtSearch;
    @BindView(R.id.tv_search)
    TextView  mTvSearch;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Inject
    ToastInstance mToastInstance;
    @Inject
    AppDataManager mAppDataManager;
    @Autowired
    public String category;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        initFragment();
        initListener();
    }

    private void initListener() {
        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            //避免提交两次 KeyEvent.ACTION_DOWN 和KeyEvent.ACTION_UP都会执行
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                search();
                return true;
            }
            return false;
        });
    }


    private void initFragment() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeSearchFragment.newInstance(1));
        fragments.add(HomeSearchFragment.newInstance(2));
        fragments.add(HomeSearchFragment.newInstance(3));

        List<String> titles = new ArrayList<>();
        titles.add("商品");
        titles.add("店铺");
        titles.add("品牌");

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fragmentManager, fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(2);
        mTabs.setupWithViewPager(mViewpager);
    }

    public void search() {
        String text = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            mToastInstance.showToast("搜索内容不能为空！");
            return;
        }

        String searchHistory = mAppDataManager.getSearchHistory();
        if (TextUtils.isEmpty(searchHistory)) {
            mAppDataManager.setSearchHistory(text);
        } else {
            if (!searchHistory.contains(text + ",")) {
                mAppDataManager.setSearchHistory(text+","+searchHistory);
            }
        }

        int currentItem = mViewpager.getCurrentItem();

        String url = "";
        if (currentItem == 2) {
            url = "/foundation/secondSearch";
        } else if (currentItem == 1) {
            url = "/foundation/shopSearch";
        } else {
            url = "/foundation/secondSearch";
        }

        ARouter.getInstance().build(url)
                .withInt("type",currentItem+1)
                .withString("content", text)
                .withString("category", category)
                .navigation();
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }
}
