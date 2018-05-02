package com.alex.code.foundation.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.MyFragmentPagerAdapter;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.data.preference.IPreference;
import com.alex.code.foundation.main.category.CategoryFragment;
import com.alex.code.foundation.main.home.HomeFragment;
import com.alex.code.foundation.main.mine.MineFragment;
import com.alex.code.foundation.main.shop.ShopFragment;
import com.alex.code.foundation.runtimepermission.PermissionsManager;
import com.alex.code.foundation.runtimepermission.PermissionsResultAction;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.NonSwipeViewPager;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = "/foundation/main")
public class MainActivity extends BaseMvpActivity<MainView,MainPresenter> implements MainView {

    @BindView(R.id.rb_home)
    RadioButton       mRbHome;
    @BindView(R.id.rb_classify)
    RadioButton       mRbClassify;
    @BindView(R.id.rb_shop)
    RadioButton       mRbShop;
    @BindView(R.id.rb_mine)
    RadioButton       mRbMine;
    @BindView(R.id.maintab_rg)
    RadioGroup        mMaintabRg;
    @BindView(R.id.no_swipe_vp)
    NonSwipeViewPager mNoSwipeVp;

    @Inject
    IPreference mIPreference;

    private int mLastCheckedId  = R.id.rb_home;
    private long exitTime   = 0;
    private long exitDelay  = 1500;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        requestPermissions();
        initFragment();
        initListener();
    }

    private void initListener() {
        mMaintabRg.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rb_home:
                    mNoSwipeVp.setCurrentItem(0, false);
                    mLastCheckedId = checkedId;
                    break;
                case R.id.rb_classify:
                    mNoSwipeVp.setCurrentItem(1, false);
                    mLastCheckedId = checkedId;
                    break;
                case R.id.rb_shop:
                    mLastCheckedId = checkedId;
                    VLog.d("token: "+mIPreference.getToken());
                    if (TextUtils.isEmpty(mIPreference.getToken())) {
                        ARouter.getInstance().build("/foundation/login").withInt("main",100).navigation(this,100);
                        return;
                    }
                    mNoSwipeVp.setCurrentItem(2, false);
                    break;
                case R.id.rb_mine:
                    mLastCheckedId = checkedId;
                    VLog.d("token: "+mIPreference.getToken());
                    if (TextUtils.isEmpty(mIPreference.getToken())) {
                        ARouter.getInstance().build("/foundation/login").withInt("main",100).navigation(this,100);
                        return;
                    }
                        mNoSwipeVp.setCurrentItem(3, false);
                    break;
                default:
            }
        });
    }

    private void initFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MineFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
        mNoSwipeVp.setAdapter(adapter);
        mNoSwipeVp.setOffscreenPageLimit(4);//2n+1
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        VLog.d("mLastCheckedId------"+mLastCheckedId);
        switch (requestCode) {
            case 100:
                if (resultCode == 200) {
                    switch (mLastCheckedId) {
                        case R.id.rb_shop:
                            mNoSwipeVp.setCurrentItem(2, false);
                            break;
                        case R.id.rb_mine:
                            mNoSwipeVp.setCurrentItem(3, false);
                            break;
                    }
                } else {
                    ((RadioButton)findViewById(R.id.rb_home)).setChecked(true);
//                    mMaintabRg.check(mLastCheckedId);回调了两次Listener
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > exitDelay) {
                Toast.makeText(App.getAppContext(), "再按一次退出.", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String type = intent.getStringExtra("type");
            if (TextUtils.equals(type, "shop")) {
                //                mNoSwipeVp.setCurrentItem(2, false);
                mRbShop.setChecked(true);

                EventBus.getDefault().post(new MessageEvent(EventCons.SHOP_REFRESH));
            } else if(TextUtils.equals(type, "mine")){
                mRbHome.setChecked(true);
                if (TextUtils.isEmpty(mIPreference.getToken())) {
                    ARouter.getInstance().build("/foundation/login").withInt("main",100).navigation(this,100);
                    return;
                }
            }
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                //				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                VLog.d("permission: "+permission);
                //                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
