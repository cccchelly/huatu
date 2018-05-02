package com.alex.code.foundation.ui.comment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.CommentDetailBean;
import com.alex.code.foundation.bean.ReplyEntity;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.DensityUtil;
import com.alex.code.foundation.utils.FrescoUtils;
import com.alex.code.foundation.utils.KeyboardUtils;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/1.
 */

@Route(path = "/foundation/commentDetails")
public class CommentDetailsActivity extends BaseMvpActivity<CommentDetailsView, CommentDetailsPresenter> implements CommentDetailsView {

    @BindView(R.id.toolbar)
    CustomToolBar    mToolbar;
    @BindView(R.id.sdv_head)
    SimpleDraweeView mSdvHead;
    @BindView(R.id.tv_name)
    TextView         mTvName;
    @BindView(R.id.tv_date)
    TextView         mTvDate;
    @BindView(R.id.tv_color)
    TextView         mTvColor;
    @BindView(R.id.tv_attrs)
    TextView         mTvAttrs;
    @BindView(R.id.tv_comment)
    TextView         mTvComment;
    @BindView(R.id.recyclerview)
    RecyclerView     mRecyclerview;
    @BindView(R.id.rv_reply)
    RecyclerView     mRvReply;
    @BindView(R.id.et_reply)
    EditText mEtReply;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;

    @Inject
    ToastInstance mToastInstance;

    @Autowired
    public String commentId;
    private PicAdapter   mPicAdapter;
    private ReplyAdapter mReplyAdapter;
    private String mReplyName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment_details;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("评论详情")
                .setLeftBackListener(this::finish);

        initRecyclerView();
        initListener();

        getPresenter().getCommentDetail(commentId);
    }

    private void initListener() {
        mEtReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReplyName = "";
            }
        });
    }

    private void initRecyclerView() {

        mPicAdapter = new PicAdapter();
        mRecyclerview.setNestedScrollingEnabled(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h1)
                .create());
        mRecyclerview.setAdapter(mPicAdapter);


        mReplyAdapter = new ReplyAdapter();
        mRvReply.setNestedScrollingEnabled(false);
        mRvReply.setLayoutManager(new LinearLayoutManager(this));
        mRvReply.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_line_h1)
                .create());
        mRvReply.setAdapter(mReplyAdapter);

        mReplyAdapter.setOnItemClickListener((adapter, view, position) -> {
            ReplyEntity replyEntity = mReplyAdapter.getData().get(position);
            mReplyName = replyEntity.getName();
            mEtReply.setFocusable(true);
            mEtReply.setFocusableInTouchMode(true);
            mEtReply.requestFocus();
            CommonUtils.showSoftInput(CommentDetailsActivity.this,mEtReply);
            mEtReply.setHint("@"+mReplyName+": ");
        });

    }

    @Override
    public void onSuccess(CommentDetailBean commentDetailBean) {
        mSdvHead.setImageURI(AppConstants.PIC_BASE_URL + commentDetailBean.getUser_headimg());
        mTvName.setText(commentDetailBean.getMember_name());
        mTvDate.setText(commentDetailBean.getAddtime());
        mTvColor.setText(commentDetailBean.getGoods_spe());

        mTvComment.setText(commentDetailBean.getContent());

        List<CommentDetailBean.ImageEntity> image = commentDetailBean.getImage();
        mPicAdapter.replaceData(image);

        List<ReplyEntity> reply = commentDetailBean.getReply();
        mReplyAdapter.replaceData(reply);

    }

    @Override
    public void showReplyList(List<ReplyEntity> replyEntities) {
        mReplyAdapter.replaceData(replyEntities);
        mEtReply.clearFocus();
        mEtReply.setText("");
        mReplyName = "";
        mEtReply.setHint("问点什么吧");
        KeyboardUtils.hideKeyboard(mEtReply);
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String reply = mEtReply.getText().toString().trim();
        if (TextUtils.isEmpty(reply)) {
            mToastInstance.showToast("评论不能为空！");
            return;
        }

        getPresenter().getReplyList(commentId+"",TextUtils.isEmpty(mReplyName) ? reply : "@"+mReplyName+": "+reply);
    }

    class PicAdapter extends BaseQuickAdapter<CommentDetailBean.ImageEntity, BaseViewHolder> {

        public PicAdapter() {
            super(R.layout.item_comment_detail_pic);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentDetailBean.ImageEntity item) {
            SimpleDraweeView sdvPic = helper.getView(R.id.sdv_pic);
//            sdvPic.setImageURI(AppConstants.PIC_BASE_URL + item.getPic_cover());
            FrescoUtils.setControllerListener(sdvPic, AppConstants.PIC_BASE_URL + item.getPic_cover(),
                    DensityUtil.getScreenWidth(App.getAppContext()) - DensityUtil.dip2px(4));
        }
    }


    class ReplyAdapter extends BaseQuickAdapter<ReplyEntity, BaseViewHolder> {


        public ReplyAdapter() {
            super(R.layout.item_coment_reply);
        }

        @Override
        protected void convert(BaseViewHolder helper, ReplyEntity item) {
            SimpleDraweeView sdvHead = helper.getView(R.id.sdv_head);
            sdvHead.setImageURI(AppConstants.PIC_BASE_URL + item.getUser_headimg());
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_date, item.getAdd_time())
                    .setText(R.id.tv_comment, item.getContent());
        }
    }

}
