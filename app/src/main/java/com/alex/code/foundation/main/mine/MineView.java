package com.alex.code.foundation.main.mine;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.UserInfo;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public interface MineView extends BaseMvpView {

    void onSuccess(UserInfo userInfo);
}
