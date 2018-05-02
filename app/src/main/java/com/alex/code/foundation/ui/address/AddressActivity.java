package com.alex.code.foundation.ui.address;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.AddressBean;
import com.alex.code.foundation.utils.ToastInstance;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/16.
 */

@Route(path = "/foundation/address")
public class AddressActivity extends BaseMvpActivity<AddressView, AddressPresenter> implements AddressView {

    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView  mRecyclerview;
    @BindView(R.id.addAdress)
    TextView      mAddAdress;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private AddressAdapter mAddressAdapter;
    private List<AddressBean.DataEntity> mData = new ArrayList<>();

    @Inject
    ToastInstance mToastInstance;

    @Autowired
    public int action;
    private AlertDialog mTipDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);

        mToolbar.setTitle("管理收货地址")
                .setLeftImage(R.drawable.ic_black_back)
                .setLeftBackListener(() -> finish());

        getPresenter().getAddressList();

        initRecyclerView();
        mTipDialog = new AlertDialog.Builder(this).create();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (TextUtils.equals(event.getMsg(),EventCons.ADDRESS_REFRESH)) {
            getPresenter().getAddressList();
        }

    }

    private void initRecyclerView() {

        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        mAddressAdapter = new AddressAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(ItemDecorations.vertical(this)
                .type(0, R.drawable.divider_decoration_transparent_h6)
                .create());
//        mRecyclerview.setAdapter(mAddressAdapter);
        mAddressAdapter.bindToRecyclerView(mRecyclerview);
        mAddressAdapter.setEmptyView(R.layout.recyclerview_empty_view);

        mAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mToastInstance.showToast("点击了： "+position);
                if (action == 100) {
                    AddressBean.DataEntity addressData = (AddressBean.DataEntity) adapter.getData().get(position);
                    EventBus.getDefault().post(new MessageEvent<>(EventCons.ADDRESS_SELECT,addressData));
                    finish();
                }
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getPresenter().getAddressList();
            }
        });

        mAddressAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.checkbox:
//                    mToastInstance.showToast("设为默认");
                    mAddressAdapter.setCheckBox(position);
                    getPresenter().updateAddressDefault(mData.get(position).getId());
                    break;
                case R.id.tv_editor:
//                    mToastInstance.showToast("编辑");
                    ARouter.getInstance().build("/foundation/newAddress")
                            .withInt("type",NewAddressActivity.TYPE_UPDATE)
                            .withParcelable("addressData",mData.get(position))
                            .navigation();
                    break;
                case R.id.tv_delete:
//                    mToastInstance.showToast("删除");
                    mTipDialog.setTitle("删除收货地址");
                    mTipDialog.setMessage("您确定要删除收货地址吗？");
                    mTipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                            (dialog, which) -> {
                                return;
                            });
                    mTipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                            (dialog, which) -> {
                                getPresenter().deleteAddress(mData.get(position).getId());

                            });
                    mTipDialog.show();
                    break;
                default:
            }
        });
    }

    @OnClick(R.id.addAdress)
    public void onViewClicked() {
        ARouter.getInstance().build("/foundation/newAddress").navigation();
    }

    @Override
    public void onSuccess(List<AddressBean.DataEntity> data) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        mData.clear();
        mData = data;
        VLog.d("data.size: "+mData.size());
        mAddressAdapter.replaceData(mData);
    }


    class AddressAdapter extends BaseQuickAdapter<AddressBean.DataEntity,BaseViewHolder>{

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();


        public AddressAdapter() {
            super(R.layout.item_address);
        }

        @Override
        public void replaceData(@NonNull Collection<? extends AddressBean.DataEntity> data) {
            checkBoxes.clear();
            super.replaceData(data);
        }

        public void setCheckBox(int pos) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                CheckBox checkBox = checkBoxes.get(i);
                if (pos != i) {
                    checkBox.setChecked(false);
                    checkBox.setTextColor(getResources().getColor(R.color.text_system));
                }else {
                    checkBox.setChecked(true);
                    checkBox.setTextColor(getResources().getColor(R.color.btn_color));
                }
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, AddressBean.DataEntity item) {

            checkBoxes.add(helper.getView(R.id.checkbox));
            helper.getAdapterPosition();
            helper.setText(R.id.tv_name, item.getConsigner())
                    .setText(R.id.tv_phone, item.getMobile())
                    .setText(R.id.tv_address, item.getProvince() + item.getCity() + item.getDistrict() + item.getAddress())
                    .setChecked(R.id.checkbox, item.getIs_default() == 1)
                    .setTextColor(R.id.checkbox,item.getIs_default() == 1 ? getResources().getColor(R.color.btn_color) : getResources().getColor(R.color.text_system))
                    .addOnClickListener(R.id.tv_editor)
                    .addOnClickListener(R.id.tv_delete)
                    .addOnClickListener(R.id.checkbox);

        }
    }
}
