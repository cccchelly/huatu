package com.alex.code.foundation.ui.userinfo.change;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.ProfileInfo;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/20.
 */

interface ChangeNameView extends BaseMvpView{

    void onSuccess(ProfileInfo.UserinfoEntity userinfo);
}
