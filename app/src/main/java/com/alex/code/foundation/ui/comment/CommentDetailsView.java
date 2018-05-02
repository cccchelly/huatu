package com.alex.code.foundation.ui.comment;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.CommentDetailBean;
import com.alex.code.foundation.bean.ReplyEntity;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/1.
 */

interface CommentDetailsView extends BaseMvpView{

    void onSuccess(CommentDetailBean commentDetailBean);

    void showReplyList(List<ReplyEntity> replyEntities);
}
