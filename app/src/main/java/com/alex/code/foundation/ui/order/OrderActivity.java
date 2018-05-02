package com.alex.code.foundation.ui.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.MyFragmentPagerAdapter;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.ui.order.fragment.OrderFragment;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

@Route(path = "/foundation/order")
public class OrderActivity extends BaseMvpActivity<OrderView, OrderPresenter> {

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tabs)
    TabLayout     mTabs;
    @BindView(R.id.viewpager)
    ViewPager     mViewpager;

    @Autowired
    public int type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);

        mToolbar.setTitle("我的订单")
                .setLeftBackListener(this::finish);

        initFragment();
        mViewpager.setCurrentItem(type,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("订单列表");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("订单列表");
    }

    private void initFragment() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderFragment.newInstance(0));
        fragments.add(OrderFragment.newInstance(1));
        fragments.add(OrderFragment.newInstance(2));
        fragments.add(OrderFragment.newInstance(3));
        fragments.add(OrderFragment.newInstance(4));

        List<String> titles = new ArrayList<>();
        titles.add("全部");
        titles.add("待付款");
        titles.add("待发货");
        titles.add("待收货");
        titles.add("待评价");

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fragmentManager, fragments, titles);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(4);
        mTabs.setupWithViewPager(mViewpager);
    }

}
