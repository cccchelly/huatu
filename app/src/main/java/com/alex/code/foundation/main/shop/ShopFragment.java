package com.alex.code.foundation.main.shop;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.App;
import com.alex.code.foundation.R;
import com.alex.code.foundation.adapter.ShopCartAdapter;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.CartGoodsBean;
import com.alex.code.foundation.bean.CartShopBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.utils.eventbus.EventCons;
import com.alex.code.foundation.utils.eventbus.MessageEvent;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.CustomToolBar;
import com.alex.code.foundation.view.holder.ShopCartDialog;
import com.alibaba.android.arouter.launcher.ARouter;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.alex.code.foundation.R.id.exListView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class ShopFragment extends BaseMvpFragment<ShopView, ShopPresenter> implements ShopView, ShopCartAdapter.CheckInterface, ShopCartAdapter.ModifyCountInterface, ShopCartAdapter.onAttrsClickListener {

    @BindView(R.id.toolbar)
    CustomToolBar      mToolbar;
    @BindView(exListView)
    ExpandableListView mExListView;
    @BindView(R.id.all_chekbox)
    CheckBox           mAllChekbox;
    @BindView(R.id.tv_total_price)
    TextView           mTvTotalPrice;
    @BindView(R.id.tv_go_to_pay)
    TextView           mTvGoToPay;
    @BindView(R.id.ll_info)
    LinearLayout       mLlInfo;
    @BindView(R.id.tv_share)
    TextView           mTvShare;
    @BindView(R.id.tv_save)
    TextView           mTvSave;
    @BindView(R.id.tv_delete)
    TextView           mTvDelete;
    @BindView(R.id.ll_shar)
    LinearLayout       mLlShar;
    @BindView(R.id.ll_cart)
    LinearLayout       mLlCart;
    @BindView(R.id.ll_empty)
    LinearLayout       mLlEmpty;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private ShopCartAdapter mShopCartAdapter;
    private List<CartShopBean> mData;
    private int totalCount;
    private double totalPrice;
    private boolean flag;
    private AlertDialog alert;
    List<String> ids = new ArrayList<>();
    private ShopCartDialog mShopCartDialog;
    private String mCart_id;
    private int mAttrsGroupPosition = -1;
    private int mAttrsChildPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isDataInitiated = false; //当前页面可见时强制刷新数据
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        mToolbar.setTitle("购物车")
                .setRightText("编辑")
                .setRightTextListener(() -> {
                    if (flag) {
                        mLlInfo.setVisibility(View.VISIBLE);
                        mTvGoToPay.setVisibility(View.VISIBLE);
                        mLlShar.setVisibility(View.GONE);
                        mToolbar.setRightText("编辑");
                    } else {
                        mLlInfo.setVisibility(View.GONE);
                        mTvGoToPay.setVisibility(View.GONE);
                        mLlShar.setVisibility(View.VISIBLE);
                        mToolbar.setRightText("完成");
                    }
                    flag = !flag;
                });
        if (mShopCartDialog == null) {
            mShopCartDialog = new ShopCartDialog(getActivity());
        }
        initExpandableListView();

        mShopCartDialog.setConfirmListener((type, totalPrice, goods_id, goods_num, sku_id) -> {

            if (!TextUtils.isEmpty(mCart_id) && mAttrsChildPosition != -1 && mAttrsGroupPosition != -1) {
                getPresenter().updateGoodsSpe(mCart_id,sku_id,mAttrsGroupPosition,mAttrsChildPosition);
            }

        });
    }

    private void initExpandableListView() {
        mShopCartAdapter = new ShopCartAdapter(getContext());
        mShopCartAdapter.setCheckInterface(this);// 关键步骤1,设置复选框接口
        mShopCartAdapter.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        mShopCartAdapter.setOnAttrsClickListener(this);
        mExListView.setAdapter(mShopCartAdapter);

        mExListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            CartGoodsBean cartGoodsBean = (CartGoodsBean) mShopCartAdapter.getChild(groupPosition, childPosition);
            VLog.d("onChildClick....   goodsId: "+cartGoodsBean.getGoods_id());
            ARouter.getInstance().build("/foundation/goodsDetail")
                    .withString("goodsId",cartGoodsBean.getGoods_id())
                    .navigation();
            return true;
        });

        mExListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            VLog.d("onGroupClick....");
            return true;
        });
        //设置 Header 为 经典样式
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        //设置 Footer 为 经典样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                prepareFetchData(true);
            }
        });

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onPageStart("购物车");
        prepareFetchData(true);
        VLog.d("onResume: -----");
    }

    @Override
    public void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPageEnd("购物车");
    }

    @Override
    public void fetchData() {

        getPresenter().getShopCartList();
        VLog.d("fetchData: -----");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (TextUtils.equals(event.getMsg(), EventCons.SHOP_REFRESH)) {
            //后来改成了resume时刷新，暂时废除通知刷新
//            getPresenter().getShopCartList();
        }

    }


    @OnClick({R.id.all_chekbox, R.id.tv_go_to_pay, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:
                doCheckAll();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(App.getAppContext(), "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                getPresenter().confirmOrderList("1",getselectIds(),"","","");
//                alert = new AlertDialog.Builder(getActivity()).create();
//                alert.setTitle("操作提示");
//                alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
//                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                return;
//                            }
//                        });
//                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getPresenter().confirmOrderList("1",getselectIds(),"","","");
//                                return;
//                            }
//                        });
//                alert.show();
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(App.getAppContext(), "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                alert.show();
                break;
        }
    }

    @Override
    public void onSuccess(List<CartShopBean> data) {
        mRefreshLayout.finishRefresh();
        mData = data;
        if (mData != null && mData.size() > 0) {
            mLlEmpty.setVisibility(View.GONE);
            mLlCart.setVisibility(View.VISIBLE);
        }
        mShopCartAdapter.setData(mData);
        for (int i = 0; i < mShopCartAdapter.getGroupCount(); i++) {
            mExListView.expandGroup(i);// 获取到数据时，将ExpandableListView以展开的方式呈现
        }
        setCartNum();
        calculate();
    }

    @Override
    public void showChangeNum(List<CartShopBean> data, int groupPosition, int childPosition) {
        mData.get(groupPosition).getList().get(childPosition).setNum(data.get(groupPosition).getList().get(childPosition).getNum());
        mShopCartAdapter.notifyDataSetChanged();
                calculate();
    }

    @Override
    public void showOrderDetail(AffirmOrderBean affirmOrderBean) {
        ARouter.getInstance().build("/foundation/affirmOrder")
                .withDouble("totalPrice",totalPrice)
                .withInt("is_cart",1)
                .withString("cart_ids",getselectIds())
                .withParcelable("affirmOrderBean",affirmOrderBean)
                .navigation();
    }

    @Override
    public void showGoodSpec(GoodSpeBean goodSpeBean) {
        mShopCartDialog.setData(goodSpeBean);
    }

    @Override
    public void showChangeSpe(List<CartShopBean> data, int groupPosition, int childPosition) {
        mCart_id = "";
        mAttrsGroupPosition = -1;
        mAttrsChildPosition = -1;
        mShopCartDialog.dismiss();
        mData.get(groupPosition).getList().get(childPosition).setSpec_name(data.get(groupPosition).getList().get(childPosition).getSpec_name());
        mData.get(groupPosition).getList().get(childPosition).setPrice(data.get(groupPosition).getList().get(childPosition).getPrice());
        mShopCartAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {

        CartShopBean cartShopBean = mData.get(groupPosition);
        List<CartGoodsBean> childs = cartShopBean.getList();
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            mAllChekbox.setChecked(true);
        else
            mAllChekbox.setChecked(false);
        mShopCartAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {

        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        CartShopBean cartShopBean = mData.get(groupPosition);
        List<CartGoodsBean> childs = cartShopBean.getList();
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            cartShopBean.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            cartShopBean.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck()) {
            mAllChekbox.setChecked(true);// 全选
        } else {
            mAllChekbox.setChecked(false);// 反选
        }
        mShopCartAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartGoodsBean product = (CartGoodsBean) mShopCartAdapter.getChild(groupPosition,
                childPosition);
        int currentCount = product.getNum();
        currentCount++;

        getPresenter().updateCartNum(product.getId(),currentCount+"",groupPosition,childPosition);

//        product.setNum(currentCount);
//        ((TextView) showCountView).setText(currentCount + "");
//        mShopCartAdapter.notifyDataSetChanged();
//        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartGoodsBean product = (CartGoodsBean) mShopCartAdapter.getChild(groupPosition,
                childPosition);
        int currentCount = product.getNum();
        if (currentCount == 1)
            return;
        currentCount--;

        getPresenter().updateCartNum(product.getId(),currentCount+"",groupPosition,childPosition);

//        product.setNum(currentCount);
//        ((TextView) showCountView).setText(currentCount + "");
//        mShopCartAdapter.notifyDataSetChanged();
//        calculate();
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {

        CartGoodsBean cartGoodsBean = mData.get(groupPosition).getList().get(childPosition);
        String id = cartGoodsBean.getId();
        getPresenter().deleteCartGoods(id);
//        mData.get(groupPosition).getList().remove(childPosition);
//        ShopCartBean.CartShopBean cartShopBean = mData.get(groupPosition);
//        List<CartGoodsBean> childs = cartShopBean.getList();
//        if (childs.size() == 0) {
//            mData.remove(groupPosition);
//        }
//        mShopCartAdapter.notifyDataSetChanged();
//        calculate();
    }

    private boolean isAllCheck() {

        for (CartShopBean cartShopBean : mData) {
            if (!cartShopBean.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < mData.size(); i++) {
            CartShopBean cartShopBean = mData.get(i);
            List<CartGoodsBean> childs = cartShopBean.getList();
            for (int j = 0; j < childs.size(); j++) {
                CartGoodsBean product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getNum();
                }
            }
        }

        mTvTotalPrice.setText("￥" + totalPrice);
        mTvGoToPay.setText("去支付(" + totalCount + ")");
        //计算购物车的金额为0时候清空购物车的视图
        if(totalCount==0){
            setCartNum();
        } else{
            mToolbar.setTitle("购物车" + "(" + totalCount + ")");
        }
    }

    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setChoosed(mAllChekbox.isChecked());
            CartShopBean cartShopBean = mData.get(i);
            List<CartGoodsBean> childs = cartShopBean.getList();
            for (CartGoodsBean cartGoodsBean : childs) {
                cartGoodsBean.setChoosed(mData.get(i).isChoosed());
                count += 1;
            }
        }

        //购物车已清空
        if(count==0){
            clearCart();
        } else{
            mToolbar.setTitle("购物车" + "(" + count + ")");
        }
    }

    private void clearCart() {
        mToolbar.setTitle("购物车" + "(" + 0 + ")");
        mToolbar.setRightTextVisibility(View.GONE);
        mLlCart.setVisibility(View.GONE);
        mLlEmpty.setVisibility(View.VISIBLE);
    }

    public String getselectIds() {
        ids.clear();
        for (CartShopBean cartShopBean : mData) {
            List<CartGoodsBean> list = cartShopBean.getList();
            for (CartGoodsBean cartGoodsBean : list) {
                if (cartGoodsBean.isChoosed()) {
                    ids.add(cartGoodsBean.getId());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append(id).append(",");
        }

        return sb.toString();
    }
    /**
     * 删除操作
     * 1.不要边遍历边删除，容易出现数组越界的情况
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {


        getPresenter().deleteCartGoods(getselectIds());

//        List<ShopCartBean.CartShopBean> toBeDeleteGroups = new ArrayList<>();// 待删除的组元素列表
//        for (int i = 0; i < mData.size(); i++) {
//            ShopCartBean.CartShopBean cartShopBean = mData.get(i);
//            if (cartShopBean.isChoosed()) {
//                toBeDeleteGroups.add(cartShopBean);
//            }
//            List<CartGoodsBean> toBeDeleteProducts = new ArrayList<>();// 待删除的子元素列表
//            List<CartGoodsBean> childs = cartShopBean.getList();
//            for (int j = 0; j < childs.size(); j++) {
//                if (childs.get(j).isChoosed()) {
//                    toBeDeleteProducts.add(childs.get(j));
//                }
//            }
//            childs.removeAll(toBeDeleteProducts);
//        }
//        mData.removeAll(toBeDeleteGroups);
//        //记得重新设置购物车
//        setCartNum();
//        mShopCartAdapter.notifyDataSetChanged();

    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setChoosed(mAllChekbox.isChecked());
            CartShopBean cartShopBean = mData.get(i);
            List<CartGoodsBean> childs = cartShopBean.getList();
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(mAllChekbox.isChecked());
            }
        }
        mShopCartAdapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void onAttrsClick(String goods_id,String cart_id,int groupPosition,int childPosition) {
        mCart_id = cart_id;
        mAttrsGroupPosition = groupPosition;
        mAttrsChildPosition = childPosition;
        VLog.d("onAttrsClick: "+goods_id + "   "+ cart_id);
        getPresenter().getGoodSpec(goods_id);
        mShopCartDialog.show();
    }
}
