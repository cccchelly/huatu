package com.alex.code.foundation.ui.splash;

import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Locale;

import butterknife.BindView;

public class SplashActivity extends BaseMvpActivity<SplashView, SplashPresenter>
        implements SplashView {

    @BindView(R.id.tvCountDown)
    TextView mTvCountDown;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        getPresenter().autoLogin();
    }

    @Override
    public void enterLogin() {
        ARouter.getInstance().build("/foundation/login").navigation();
        finish();
    }

    @Override
    public void enterMain() {
        ARouter.getInstance().build("/foundation/main").navigation();
        finish();
    }

    @Override
    public void timeCountDown(long time) {
        mTvCountDown.setText(String.format(Locale.getDefault(), "Wait %d Seconds", time));
    }
}
