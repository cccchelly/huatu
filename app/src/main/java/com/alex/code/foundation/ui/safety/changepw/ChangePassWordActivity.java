package com.alex.code.foundation.ui.safety.changepw;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.utils.KeyboardUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:修改密码和忘记密码界面
 * Date: 2017/10/19.
 */

@Route(path = "/foundation/changePassword")
public class ChangePassWordActivity extends BaseMvpActivity<ChangePassWordView, ChangePassWordPresenter> implements ChangePassWordView{

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.et_oldword)
    MaterialEditText mEtOldword;
    @BindView(R.id.et_password)
    MaterialEditText mEtPassword;
    @BindView(R.id.et_password2)
    MaterialEditText mEtPassword2;
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

    public static final int TYPE_CHANGE = 100;
    public static final int TYPE_FORGET = 200;

    @Autowired
    public int type;
    private String mOldword;
    private String mPassword;
    private String mPassword2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pass_word;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        if (type == 200) {
            mEtOldword.setVisibility(View.GONE);
            mToolbar.setTitle("忘记密码").setLeftBackListener(() -> finish());
        } else {
            mLlCode.setVisibility(View.GONE);
            mToolbar.setTitle("修改密码").setLeftBackListener(() -> finish());
        }
    }


    @OnClick({R.id.tv_getCode, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                break;
            case R.id.tv_commit:
                if (type == 200) {

                } else {
                    if (checkRules()) {
                        getPresenter().updatePasswrod(mOldword,mPassword);
                    }
                }
                break;
        }
    }

    public boolean checkRules() {
        mOldword = mEtOldword.getText().toString();
        mPassword = mEtPassword.getText().toString();
        mPassword2 = mEtPassword2.getText().toString();

        if (TextUtils.isEmpty(mOldword) || mOldword.length() < 6) {
            mToastInstance.showToast("密码长度不能小于6位！");
            mEtOldword.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEtOldword.startAnimation(shake);
            return false;
        }

        if (TextUtils.isEmpty(mPassword) || mEtPassword.length() < 6) {
            mToastInstance.showToast("密码长度不能小于6位！");
            mEtPassword.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEtPassword.startAnimation(shake);
            return false;
        }

        if (TextUtils.isEmpty(mPassword2) || mPassword2.length() < 6) {
            mToastInstance.showToast("密码长度不能小于6位！");
            mEtPassword2.clearAnimation();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mEtPassword2.startAnimation(shake);
            return false;
        }


        if (!KeyboardUtils.hideKeyboard(mEtOldword) || !KeyboardUtils.hideKeyboard(mEtPassword)) {
            KeyboardUtils.hideKeyboard(mEtPassword2);
        }

        return true;
    }

    @Override
    public void onSuccess() {
        mToastInstance.showToast("修改密码成功");
        finish();
    }

}
