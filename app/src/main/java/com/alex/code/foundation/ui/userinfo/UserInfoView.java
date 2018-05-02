package com.alex.code.foundation.ui.userinfo;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.bean.UploadPicBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

interface UserInfoView extends BaseMvpView {

    void onSuccess(ProfileInfo.UserinfoEntity userInfo);

    void showUpdateData(ProfileInfo.UserinfoEntity userInfo);

    void onPicSuccess(UploadPicBean uploadPicBean);
}
