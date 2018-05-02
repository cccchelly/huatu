package com.alex.code.foundation.ui.details.comment;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.CommentBean;
import com.alex.code.foundation.bean.CommentTypeBean;

import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

interface CommentView extends BaseMvpView{

    void onSuccess(CommentBean commentBean);

    void showCommentType(List<CommentTypeBean.EvalCountEntity> data);
}
