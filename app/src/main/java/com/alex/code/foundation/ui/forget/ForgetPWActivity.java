package com.alex.code.foundation.ui.forget;

import android.text.TextUtils;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ForgetPWBean;
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
 * Des:
 * Date: 2017/11/29.
 */

@Route(path = "/foundation/forgetPW")
public class ForgetPWActivity extends BaseMvpActivity<ForgetPWView, ForgetPWPresenter> implements ForgetPWView{
    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.et_password)
    MaterialEditText mEtPassword;
    @BindView(R.id.et_password2)
    MaterialEditText mEtPassword2;
    @BindView(R.id.tv_commit)
    TextView         mTvCommit;

    @Inject
    ToastInstance mToastInstance;

    @Autowired
    public String mobile;
    @Autowired
    public String token;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pw;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("忘记密码")
                .setLeftBackListener(this::finish);
    }


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String password = mEtPassword.getText().toString();
        String password2 = mEtPassword2.getText().toString();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            mToastInstance.showToast("新密码不能为空!");
            return;
        }

        if (!TextUtils.equals(password, password2)) {
            mToastInstance.showToast("两次输入密码不一致!");
            return;
        }

        getPresenter().findPassword(mobile,token,password);
    }

    @Override
    public void onSuccess(ForgetPWBean forgetPWBean) {
        mToastInstance.showToast("重置密码成功");
        finish();
    }
}
