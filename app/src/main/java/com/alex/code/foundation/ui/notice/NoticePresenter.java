package com.alex.code.foundation.ui.notice;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/23.
 */

class NoticePresenter extends BaseMvpPresenter<NoticeView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public NoticePresenter() {
    }
}
