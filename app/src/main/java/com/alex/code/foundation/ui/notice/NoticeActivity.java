package com.alex.code.foundation.ui.notice;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.UMsg;
import com.alex.code.foundation.utils.ACache;
import com.alex.code.foundation.utils.CommonUtils;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.alex.code.foundation.App.UM_MSG;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/23.
 */

@Route(path = "/foundation/notice")
public class NoticeActivity extends BaseMvpActivity<NoticeView, NoticePresenter> {

    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView       mRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private UMsgAdapter mUMsgAdapter;
    private ACache      mACache;
    private AlertDialog mTipDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("平台通知")
                .setLeftBackListener(this::finish);

        mACache = ACache.get(App.getAppContext());

        mTipDialog = new AlertDialog.Builder(this).create();
        initRecyclerView();
        initListener();
        initData();
    }

    private void initListener() {

        mUMsgAdapter.setOnItemClickListener((adapter, view, position) -> {
            UMsg uMsg = mUMsgAdapter.getData().get(position);
            String appUrl = uMsg.getAppUrl();
            if (!TextUtils.isEmpty(appUrl)) {
                CommonUtils.startNativeActivity(appUrl);
            }
        });

        mUMsgAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mTipDialog.setTitle("操作提示");
            mTipDialog.setMessage("您确定要这删除条消息吗？");
            mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                    (dialog, which) -> {
                        return;
                    });
            mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                    (dialog, which) -> {
                        mUMsgAdapter.remove(position);
                        mACache.remove(App.UM_MSG);
                        ArrayList<UMsg> data = (ArrayList<UMsg>) mUMsgAdapter.getData();
                        mACache.put(App.UM_MSG, data);
                    });
            mTipDialog.show();
            return true;
        });
    }

    private void initData() {

        addDispose(Observable.create((ObservableOnSubscribe<List<UMsg>>) e -> {
            ArrayList<UMsg> uMsgs = (ArrayList<UMsg>) mACache.getAsObject(UM_MSG);
            VLog.d("uMsgs" + uMsgs);
            e.onNext(uMsgs);
            e.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uMsgs -> {

                    if (uMsgs != null) {
                        mUMsgAdapter.addData(uMsgs);
                    }
                }, throwable -> {

                }));

    }

    private void initRecyclerView() {

        mUMsgAdapter = new UMsgAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
        mUMsgAdapter.bindToRecyclerView(mRecyclerview);
        mUMsgAdapter.setEmptyView(R.layout.recyclerview_empty_view);

        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mRefreshLayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshLayout.finishRefresh(2000);
            }
        });
    }

    class UMsgAdapter extends BaseQuickAdapter<UMsg, BaseViewHolder> {

        public UMsgAdapter() {
            super(R.layout.item_um_msg);
        }

        @Override
        protected void convert(BaseViewHolder helper, UMsg item) {
            helper.setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_date, item.getDate())
                    .setText(R.id.tv_content, item.getContent());
        }
    }


}
