package com.alex.code.foundation.ui.forget.one;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ForgetSMSBean;
import com.alex.code.foundation.ui.register.verification.VerificationDialogFragment;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
 * Date: 2017/11/29.
 */

@Route(path = "/foundation/forgetOne")
public class ForgetOneActivity extends BaseMvpActivity<ForgetOneView, ForgetOnePresenter> implements ForgetOneView{

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.et_phone)
    MaterialEditText mEtPhone;
    @BindView(R.id.et_code)
    MaterialEditText mEtCode;
    @BindView(R.id.tv_getCode)
    TextView         mTvGetCode;
    @BindView(R.id.ll_code)
    LinearLayout     mLlCode;
    @BindView(R.id.tv_commit)
    TextView         mTvCommit;

    @Inject
    ToastInstance mToastInstance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_one;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("忘记密码")
                .setLeftBackListener(this::finish);
    }


    @OnClick({R.id.tv_getCode, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                if (!CommonUtils.isMobileNO(mEtPhone.getText().toString())) {
                    mToastInstance.showToast("手机号码不合法!");
                    mEtPhone.clearAnimation();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mEtPhone.startAnimation(shake);
                    return;
                }

                VerificationDialogFragment.newInstance(mEtPhone.getText().toString()).show(fragmentManager, "dialog");
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

                break;
            case R.id.tv_commit:
                if (!CommonUtils.isMobileNO(mEtPhone.getText().toString())) {
                    mToastInstance.showToast("手机号码不合法!");
                    mEtPhone.clearAnimation();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mEtPhone.startAnimation(shake);
                    return;
                }

                if (TextUtils.isEmpty(mEtCode.getText().toString())) {
                    mToastInstance.showToast("请输入验证码!");
                    mEtCode.clearAnimation();
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mEtCode.startAnimation(shake);
                    return;
                }

                getPresenter().findPasswordSMS(mEtPhone.getText().toString(),mEtCode.getText().toString());

                break;
        }
    }


    @Override
    public void onSuccess(ForgetSMSBean forgetSMSBean) {

        ARouter.getInstance().build("/foundation/forgetPW")
                .withString("mobile",forgetSMSBean.getMobile())
                .withString("token",forgetSMSBean.getToken())
                .navigation();
        finish();
    }
}
