package com.alex.code.foundation.ui.safety.email;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.EmailBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/7.
 */

interface EmailView extends BaseMvpView{

    void onSuccess(EmailBean emailBean);
}
