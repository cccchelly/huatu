package com.alex.code.foundation.ui.login;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpViewStateActivity;
import com.alex.code.foundation.data.preference.IPreference;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.utils.KeyboardUtils;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/foundation/login")
public class LoginActivity extends BaseMvpViewStateActivity<LoginView, LoginPresenter, LoginViewState> implements LoginView {

    @BindView(R.id.loginForm)
    ViewGroup mLoginForm;

    @BindView(R.id.username)
    EditText mUserName;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.loginButton)
    ActionProcessButton mLoginButton;

    @BindView(R.id.errorView)
    View          mErrorView;
    @BindView(R.id.tvRegister)
    TextView      mTvRegister;
    @BindView(R.id.toolbar)
    CustomToolBar mToobar;

    @Named(BaseActivityModule.ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    @Inject
    FragmentManager mFragmentManager;
    @Inject
    IPreference mIPreference;

    @Autowired
    public int main;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        mToobar.setTitle("登录").setLeftImage(R.drawable.ic_black_back).setLeftBackListener(() -> finish());
        if (main == 1000) {
            mIPreference.setToken("");
        }
        int startDelay = getResources().getInteger(android.R.integer.config_mediumAnimTime) + 100;
        LayoutTransition transition = new LayoutTransition();
        transition.enableTransitionType(LayoutTransition.CHANGING);
        transition.setStartDelay(LayoutTransition.APPEARING, startDelay);
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, startDelay);
        mLoginForm.setLayoutTransition(transition);

    }

    @Override
    protected void onFirstCreate() {
        super.onFirstCreate();
        showLoginForm();
    }


    @OnClick({R.id.tvRegister,R.id.tv_forget, R.id.loginButton, R.id.img_qq, R.id.img_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRegister:
                ARouter.getInstance().build("/foundation/register").navigation();
                finish();
                break;
            case R.id.tv_forget:
                ARouter.getInstance().build("/foundation/forgetOne").navigation();
                break;
            case R.id.loginButton:
                String username = mUserName.getText().toString();
                String password = mPassword.getText().toString();

                mLoginForm.clearAnimation();

                if (TextUtils.isEmpty(username)) {
                    mUserName.clearAnimation();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mUserName.startAnimation(shake);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.clearAnimation();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mPassword.startAnimation(shake);
                    return;
                }

                if (!KeyboardUtils.hideKeyboard(mUserName)) {
                    KeyboardUtils.hideKeyboard(mPassword);
                }

                //                getPresenter().login(new AuthCredentials(username, password));
                getPresenter().login(username, password);
                break;
            case R.id.img_qq:
                UMShareAPI.get(App.getAppContext()).getPlatformInfo(this, SHARE_MEDIA.QQ,authListener);
                break;
            case R.id.img_weixin:
                UMShareAPI.get(App.getAppContext()).getPlatformInfo(this, SHARE_MEDIA.WEIXIN,authListener);
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(App.getAppContext(),platform +"登录", Toast.LENGTH_LONG).show();

            String payload = new Gson().toJson(data);
            VLog.d("payload: "+payload);
            getPresenter().oauthLogin(platform.toString(), payload);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(App.getAppContext(), platform +"登录失败：" + t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(App.getAppContext(), platform +"登录取消了", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 对于QQ，新浪精简版，twitter，facebook等大多数平台的回调都是通过onActivityResult来完成的，所以要在你的activity中添加如下代码：
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
    }

    @Override
    public void showLoginForm() {
        getViewState().setShowLoginForm();
        mErrorView.setVisibility(View.GONE);
        setFormEnabled(true);
        mLoginButton.setProgress(0);
    }

    @Override
    public void showError() {
        getViewState().setShowError();
        setFormEnabled(true);
        mLoginButton.setProgress(0);

        if (!isRestoringViewState()) {
            // Enable animations only if not restoring view state
            mLoginForm.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mLoginForm.startAnimation(shake);
        }

        mErrorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
        mErrorView.setVisibility(View.GONE);
        setFormEnabled(false);
        // any progress between 0 - 100 shows animation
        mLoginButton.setProgress(30);
    }

    @Override
    public void loginSuccessful() {
        mLoginButton.setProgress(100); // We are done
        VLog.d("main: "+main);
        if (main == 100) {//代表从mainActivity过来的登录
            setResult(200);
            finish();
        } else if(main == 1000){//通过服务器返回状态的登录
            finish();
        } else {
            ARouter.getInstance().build("/foundation/main").navigation();
            finish();
            overridePendingTransition(0, R.anim.zoom_out);
        }
    }

    private void setFormEnabled(boolean enabled) {
        mUserName.setEnabled(enabled);
        mPassword.setEnabled(enabled);
        mLoginButton.setEnabled(enabled);
    }

}
