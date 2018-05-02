package com.alex.code.foundation.ui.safety.changephone;

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
 * Date: 2017/10/19.
 */

@Route(path = "/foundation/changePhone")
public class ChangePhoneActivity extends BaseMvpActivity<ChangePhoneView, ChangePhonePresenter> implements ChangePhoneView{

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.et_phone)
    MaterialEditText mEtPhone;
    @BindView(R.id.et_code)
    MaterialEditText mEtCode;
    @BindView(R.id.tv_getCode)
    TextView         mTvGetCode;
    @BindView(R.id.tv_commit)
    TextView         mTvCommit;
    private String mPhone;

    @Inject
    ToastInstance mToastInstance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("修改手机号码")
                .setLeftBackListener(() -> finish());
    }

    @OnClick({R.id.tv_getCode, R.id.tv_commit})
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
            case R.id.tv_commit:
                if (checkAccount()) {
                    String code = mEtCode.getText().toString();

                    if (TextUtils.isEmpty(code)) {
                        mEtCode.clearAnimation();
                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                        mEtCode.startAnimation(shake);
                        return;
                    }
                    getPresenter().updatePhone(code,mPhone);
                }
                break;
        }
    }

    public boolean checkAccount() {

        mPhone = mEtPhone.getText().toString();

        if (TextUtils.isEmpty(mPhone) || !CommonUtils.isMobileNO(mPhone)) {
            mToastInstance.showToast("手机号码不合法！");
            mEtPhone.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEtPhone.startAnimation(shake);
            return false;
        }


        if (!KeyboardUtils.hideKeyboard(mEtPhone)) {
            KeyboardUtils.hideKeyboard(mEtCode);
        }

        return true;
    }

    @Override
    public void onSuccess() {
        mToastInstance.showToast("修改手机号码成功");
        finish();
    }

}
