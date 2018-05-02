package com.alex.code.foundation.ui.safety;

import android.view.View;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/19.
 */

@Route(path = "/foundation/safety")
public class SafetyActivity extends BaseMvpActivity<SafetyView, SafetyPresenter> implements SafetyView{

    @BindView(R.id.toolBar)
    CustomToolBar mToolBar;
    @BindView(R.id.tv_name)
    TextView      mTvName;
    @BindView(R.id.tv_phone)
    TextView      mTvPhone;
    @BindView(R.id.tv_email)
    TextView      mTvEmail;

    @Autowired
    public String mobile;
    @Autowired
    public String nickName;
    @Autowired
    public String email;
    @Autowired
    public int emailBind;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_safety;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolBar.setTitle("账户与安全")
                .setLeftBackListener(() -> finish());
        mTvName.setText(nickName);
        mTvPhone.setText(mobile);
        mTvEmail.setText(emailBind == 1 ? "已绑定" : "未绑定");
    }

    @OnClick({R.id.ll_changeName, R.id.ll_changeWord, R.id.tv_password,R.id.tv_logout,R.id.ll_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_changeName:
                break;
            case R.id.ll_changeWord:
                ARouter.getInstance().build("/foundation/changePhone").withInt("type",100).navigation();
                break;
            case R.id.tv_password:
                ARouter.getInstance().build("/foundation/changePassword").navigation();
                break;
            case R.id.tv_logout:
                getPresenter().logout();
                break;
            case R.id.ll_email:
                ARouter.getInstance().build("/foundation/email")
                        .withString("email", email)
                        .withInt("emailBind", emailBind)
                        .navigation();
                break;
            default:
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onLogoutSuccess() {

        ARouter.getInstance().build("/foundation/main")
                .withString("type","mine")
                .navigation();

    }
}
