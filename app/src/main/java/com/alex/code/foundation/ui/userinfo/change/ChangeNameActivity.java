package com.alex.code.foundation.ui.userinfo.change;

import android.text.TextUtils;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

@Route(path = "/foundation/changeName")
public class ChangeNameActivity extends BaseMvpActivity<ChangeNameView, ChangeNamePresenter> implements ChangeNameView{


    @BindView(R.id.toolBar)
    CustomToolBar    mToolBar;
    @BindView(R.id.username)
    MaterialEditText mUsername;
    @BindView(R.id.tv_save)
    TextView         mTvSave;

    @Inject
    ToastInstance mToastInstance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_name;
    }

    @Override
    protected void init() {

        mToolBar.setTitle("修改用户名").setLeftBackListener(this::finish);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {

        String username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mToastInstance.showToast("用户名不能为空");
            return;
        }

        if (username.length() > 7) {
            mToastInstance.showToast("用户名长度不能超过7位");
            return;
        }

        getPresenter().updateProfileInfo(username.trim(),"","");
    }

    @Override
    public void onSuccess(ProfileInfo.UserinfoEntity userinfo) {
        mToastInstance.showToast("用户名修改成功");
        EventBus.getDefault().post(new MessageEvent<>(EventCons.UPDATE_NAME, userinfo));
        finish();
    }
}
