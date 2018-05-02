package com.alex.code.foundation.ui.details.comment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.CommentPicAdapter;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.CommentBean;
import com.alex.code.foundation.bean.CommentTypeBean;
import com.alex.code.foundation.view.GridViewForScrollView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

public class CommentFragment extends BaseMvpFragment<CommentView, CommentPresenter> implements CommentView {

    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.flowlayout)
    TagFlowLayout      mFlowlayout;
    private int pageIndex = 1;
    private int pageNum = 10;
    private int mCommentType = 1;
    private CommentAdapter mCommentAdapter;

    public static final String GOODS_ID = "goods_id";
    public String mGoodsId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void init() {

        Bundle bundle = getArguments();
        mGoodsId = bundle.getString(GOODS_ID);
        initRecyclerView();
    }

    public static CommentFragment newInstance(String goodsId) {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GOODS_ID,goodsId);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    private void initRecyclerView() {

        mCommentAdapter = new CommentAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(getContext())
                .type(0, R.drawable.divider_decoration_transparent_h1)
                .create());
        mRecyclerview.setAdapter(mCommentAdapter);

        mCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CommentBean.EvalListEntity> data = mCommentAdapter.getData();
                ARouter.getInstance().build("/foundation/commentDetails")
                        .withString("commentId",data.get(position).getId())
                        .navigation();
            }
        });
        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
//                refreshlayout.finishLoadmore(2000);
                getPresenter().getGoodsComment(mGoodsId,mCommentType + "",pageIndex + "", pageNum+"");
                refreshlayout.autoLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mCommentAdapter.getData().clear();
                pageIndex = 1;
                getPresenter().getGoodsComment(mGoodsId,mCommentType + "",pageIndex + "", pageNum+"");
            }
        });
    }

    @Override
    public void fetchData() {

        getPresenter().getGoodsCommentType(mGoodsId);
        getPresenter().getGoodsComment(mGoodsId,mCommentType + "",pageIndex + "", pageNum+"");
    }

    @Override
    public void onSuccess(CommentBean commentBean) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        List<CommentBean.EvalListEntity> eval_list = commentBean.getEval_list();
        mCommentAdapter.addData(eval_list == null ? new ArrayList<CommentBean.EvalListEntity>() : eval_list);

    }

    @Override
    public void showCommentType(List<CommentTypeBean.EvalCountEntity> data) {

        mFlowlayout.setAdapter(new TagAdapter<CommentTypeBean.EvalCountEntity>(data) {
            @Override
            public View getView(FlowLayout parent, int position, CommentTypeBean.EvalCountEntity o) {
                TextView textView = (TextView) View.inflate(getContext(), R.layout.tag_comment,null);
                textView.setText(o.getName()+"("+ o.getCount() + ")");
                return textView;
            }
        });

        mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mCommentType = data.get(position).getValue();
                pageIndex = 1;
                mCommentAdapter.getData().clear();
                getPresenter().getGoodsComment(mGoodsId,mCommentType + "",pageIndex + "", pageNum+"");
                return true;
            }
        });

        mFlowlayout.getAdapter().setSelectedList(0);

    }

    class CommentAdapter extends BaseQuickAdapter<CommentBean.EvalListEntity, BaseViewHolder> {

        public CommentAdapter() {
            super(R.layout.item_comment);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentBean.EvalListEntity item) {

            SimpleDraweeView sdvhead = helper.getView(R.id.sdv_head);
            GridViewForScrollView gridView = helper.getView(R.id.gridView);

            sdvhead.setImageURI(AppConstants.PIC_BASE_URL + item.getUser_headimg());

            helper.setText(R.id.tv_name, item.getMember_name())
                    .setText(R.id.tv_color, item.getGoods_spe())
                    .setText(R.id.tv_date, item.getAddtime())
                    .setText(R.id.tv_comment, item.getContent());

            CommentPicAdapter commentPicAdapter = new CommentPicAdapter(getContext());
            gridView.setAdapter(commentPicAdapter);
            commentPicAdapter.setData(item.getImages());

        }
    }


}
