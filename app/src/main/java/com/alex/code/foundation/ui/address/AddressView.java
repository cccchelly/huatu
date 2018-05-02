package com.alex.code.foundation.ui.address;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.AddressBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

interface AddressView extends BaseMvpView{

    void onSuccess(List<AddressBean.DataEntity> data);
}
