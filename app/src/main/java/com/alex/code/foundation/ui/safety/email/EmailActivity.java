package com.alex.code.foundation.ui.safety.email;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.EmailBean;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alex.code.foundation.view.holder.EmailHolder;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.dialogplus.DialogPlus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/7.
 */

@Route(path = "/foundation/email")
public class EmailActivity extends BaseMvpActivity<EmailView, EmailPresenter> implements EmailView{

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.email)
    TextView      mEmail;
    @BindView(R.id.emailPass)
    TextView      mEmailPass;
    @BindView(R.id.tv_Submit)
    TextView      mTvSubmit;

    @Inject
    ToastInstance mToastInstance;
    @Autowired
    public String email;
    @Autowired
    public int emailBind;

    private DialogPlus mDialogPlus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_email;
    }

    @Override
    protected void init() {

        ARouter.getInstance().inject(this);
        mToolbar.setTitle("绑定邮箱")
                .setLeftBackListener(this::finish);

        if (emailBind == 1 && !TextUtils.isEmpty(email)) {
            mEmail.setText(email);
            mEmailPass.setText("已绑定");
            mTvSubmit.setText("更换邮箱");
        } else {
            mEmail.setText("您还未绑定邮箱哦~");
        }
    }


    @OnClick(R.id.tv_Submit)
    public void onViewClicked() {
        mDialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new EmailHolder())
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setContentBackgroundResource(Color.TRANSPARENT)
                .setOnClickListener((dialog, view) -> {
                    EditText email = (EditText) dialog.findViewById(R.id.et_email);
                    switch (view.getId()) {
                        case R.id.tv_cancle:
                            dialog.dismiss();
                            break;
                        case R.id.tv_commit:
                            if (!CommonUtils.checkMail(App.getAppContext(), email.getText().toString())) {
                                mToastInstance.showToast("邮箱格式不正确！");
                                return;
                            }
                            getPresenter().bindEmail(email.getText().toString());
                            break;
                    }
                })
                .create();
        mDialogPlus.show();
    }

    @Override
    public void onSuccess(EmailBean emailBean) {
        mDialogPlus.dismiss();
        mToastInstance.showToast("已发送邮件至"+emailBean.getEmail()+",请登录邮箱验证");
        if (emailBean.getUser_email_bind() == 1) {
            mTvSubmit.setText("更换邮箱");
            mEmail.setText(emailBean.getEmail());
            mEmailPass.setText("已绑定");

        } else {
            mTvSubmit.setText("绑定邮箱");
            mEmail.setText(emailBean.getEmail());
            mEmailPass.setText("等待校验邮箱");
        }
    }
}
