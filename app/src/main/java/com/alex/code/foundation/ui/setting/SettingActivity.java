package com.alex.code.foundation.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.ui.webview.WebviewActivity;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.DataCleanManager;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/24.
 */

@Route(path = "/foundation/setting")
public class SettingActivity extends BaseMvpActivity<SettingView, SettingPresenter> {
    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.tv_clean_cache)
    TextView      mTvCleanCache;
    @BindView(R.id.tv_feedback)
    TextView      mTvFeedback;
    @BindView(R.id.tv_about)
    TextView      mTvAbout;
    @BindView(R.id.tv_check_update)
    TextView      mTvCheckUpdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("设置")
                .setLeftBackListener(this::finish);

        try {
            mTvCleanCache.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
            mTvCheckUpdate.setText(CommonUtils.getVersionName(App.getAppContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ll_clean_cache, R.id.tv_feedback, R.id.tv_about,R.id.tv_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_clean_cache:
                addDispose(Observable.just(true)
                        .delay(2, TimeUnit.SECONDS)
                        .doOnSubscribe(disposable -> showLoading("清除缓存中..."))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isLogin -> {
                            DataCleanManager.clearAllCache(getApplicationContext());
                            Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT).show();
                            mTvCleanCache.setText("0.00KB");
                            dismissLoading();

                        },throwable -> {
                            dismissLoading();
                            Toast.makeText(this, "清除缓存失败", Toast.LENGTH_SHORT).show();
                        }));

                break;
            case R.id.tv_feedback:
                Intent intent = new Intent(this,WebviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url","http://jht.meishifulu.cn/public/wap/personalcenter/help_center.html");
                bundle.putString("title","帮助中心");
                intent.putExtra("bundle",bundle);
                startActivity(intent);

                break;
            case R.id.tv_about:

                break;
            case R.id.tv_check_update:
                Toast.makeText(this, "检测更新", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
