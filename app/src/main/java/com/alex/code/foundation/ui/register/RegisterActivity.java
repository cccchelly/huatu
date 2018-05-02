package com.alex.code.foundation.ui.register;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.ui.register.verification.VerificationDialogFragment;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.KeyboardUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by dth
 * Des:
 * Date: 2017/10/9.
 */

@Route(path = "/foundation/register")
public class RegisterActivity extends BaseMvpActivity<RegisterView, RegisterPresenter> implements RegisterView{

    @BindView(R.id.toolBar)
    CustomToolBar       mToolBar;
    @BindView(R.id.tv_getCode)
    TextView            mTvGetCode;
    @BindView(R.id.username)
    MaterialEditText    mUsername;
    @BindView(R.id.password)
    MaterialEditText    mPassword;
    @BindView(R.id.verCode)
    MaterialEditText    mVerCode;
    @BindView(R.id.registerButton)
    ActionProcessButton mRegisterButton;

    @Inject
    ToastInstance mToastInstance;
    private String mPhone;
    private String mWord;
    private String mCode;

    @Override
    protected int getLayoutId(){
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

        mToolBar.setTitle("注册").setLeftBackListener(() -> finish());
    }

    @OnClick(R.id.registerButton)
    public void onLoginClicked() {

        mCode = mVerCode.getText().toString();

//                mLoginForm.clearAnimation();

        if (checkAccount()) {

            if (TextUtils.isEmpty(mCode)) {
                mVerCode.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                mVerCode.startAnimation(shake);
                return;
            }
            getPresenter().register("",mWord,"",mPhone,mCode);
        }
    }

    public boolean checkAccount() {

        mPhone = mUsername.getText().toString();
        mWord = mPassword.getText().toString();

        if (TextUtils.isEmpty(mPhone) || !CommonUtils.isMobileNO(mPhone)) {
            mToastInstance.showToast("手机号码不合法！");
            mUsername.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mUsername.startAnimation(shake);
            return false;
        }

        if (TextUtils.isEmpty(mWord)) {
            mPassword.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return false;
        }


        if (!KeyboardUtils.hideKeyboard(mUsername)) {
            KeyboardUtils.hideKeyboard(mPassword);
        }

        return true;
    }


    @OnClick({R.id.tv_getCode, R.id.verCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                if (checkAccount()) {
                    VerificationDialogFragment.newInstance(mPhone).show(fragmentManager, "dialog");
                    addDispose(Observable.intervalRange(1,60,0,1, TimeUnit.SECONDS)
                            .map(count -> (60 - count))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                if (aLong == 0) {
                                    mTvGetCode.setEnabled(true);
                                    mTvGetCode.setText("获取验证码");
                                    mTvGetCode.setTextColor(getResources().getColor(R.color.bg_ffffff));
                                } else {
                                    mTvGetCode.setText(aLong+ " s");
                                    mTvGetCode.setTextColor(getResources().getColor(R.color.btn_color));
                                    mTvGetCode.setEnabled(false);
                                }
                            }));
                }
                break;
            case R.id.verCode:
                break;
        }
    }

    @Override
    public void onSuccess() {
        mRegisterButton.setProgress(100);
        mToastInstance.showToast("注册成功");
        ARouter.getInstance().build("/foundation/login").navigation();
        finish();
    }

    @Override
    public void onFail() {
        mToastInstance.showToast("注册失败");
        mRegisterButton.setProgress(0);
    }

    @Override
    public void showLoading() {
        // any progress between 0 - 100 shows animation
        mRegisterButton.setProgress(30);
    }
}
