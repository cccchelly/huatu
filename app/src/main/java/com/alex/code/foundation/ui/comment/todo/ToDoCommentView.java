package com.alex.code.foundation.ui.comment.todo;

import com.alex.code.foundation.base.BaseMvpView;
import com.alex.code.foundation.bean.TodoCommentBean;
import com.alex.code.foundation.bean.UploadPicBean;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/17.
 */

interface ToDoCommentView extends BaseMvpView{

    void onPicSuccess(UploadPicBean uploadPicBean);

    void onSuccess(TodoCommentBean todoCommentBean);
}
