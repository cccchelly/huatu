package com.alex.code.foundation.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CartGoodsBean;
import com.alex.code.foundation.bean.CartShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/2.
 */

public class ShopCartAdapter extends BaseExpandableListAdapter {

    private List<CartShopBean> mData = new ArrayList<>();
    private Context                         mContext;
    private CheckInterface                  checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private onAttrsClickListener onAttrsClickListener;

    public ShopCartAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<CartShopBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CartGoodsBean> list = mData.get(groupPosition).getList();
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData == null ? null : mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<CartGoodsBean> list = mData.get(groupPosition).getList();
        return list == null ? null : list.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcart_group, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        final CartShopBean group = (CartShopBean) getGroup(groupPosition);

        gholder.tvSourceName.setText(group.getShop_name());
        gholder.determineChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                if (checkInterface != null) {
                    checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
                }
            }
        });
        gholder.determineChekbox.setChecked(group.isChoosed());
        if (group.isEdtor()) {
            gholder.tvStoreEdtor.setText("完成");
        } else {
            gholder.tvStoreEdtor.setText("编辑");
        }
        gholder.tvStoreEdtor.setOnClickListener(new GroupViewClick(groupPosition, gholder.tvStoreEdtor, group));
//        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcart_product, null);
            //            if(isLastChild&&getChild(groupPosition,childPosition)!=null)
            //            {
            //                View    v = View.inflate(context, R.layout.child_footer,null);
            //                TextView txtFooter = (TextView)v.findViewById(R.id.txtFooter);
            //                txtFooter.setText("店铺满99元包邮");
            //                if(convertView instanceof ViewGroup){
            //                    ((ViewGroup) convertView).addView(v);
            //                }
            //            }

            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();
        }

        if (mData.get(groupPosition).isEdtor()) {
            cholder.llEdtor.setVisibility(View.VISIBLE);
            cholder.rlNoEdtor.setVisibility(View.GONE);
        } else {
            cholder.llEdtor.setVisibility(View.GONE);
            cholder.rlNoEdtor.setVisibility(View.VISIBLE);
        }
        final CartGoodsBean goodsInfo = (CartGoodsBean) getChild(groupPosition, childPosition);


        if(isLastChild&&getChild(groupPosition,childPosition)!=null){
            cholder.stub.setVisibility(View.GONE);
            //  TextView tv= (TextView) cholder.stub.findViewById(R.id.txtFooter);//这里用来动态显示店铺满99元包邮文字内容
        }else{
            cholder.stub.setVisibility(View.GONE);
        }
        if (goodsInfo != null) {


            cholder.tvIntro.setText(goodsInfo.getGoods_name());
            cholder.tvPrice.setText("￥" + goodsInfo.getPrice() + "");
            cholder.etNum.setText(goodsInfo.getNum() + "");
            cholder.sdvPic.setImageURI(AppConstants.PIC_BASE_URL + goodsInfo.getPic_cover_mid());
            cholder.tvAttrs.setText(goodsInfo.getSpec_name());
            cholder.tvAttrs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAttrsClickListener != null) {
                        onAttrsClickListener.onAttrsClick(goodsInfo.getGoods_id(),goodsInfo.getId(),groupPosition,childPosition);
                    }
                }
            });
            cholder.tvColorSize.setText(goodsInfo.getSpec_name());
            SpannableString spanString = new SpannableString("￥" + String.valueOf(goodsInfo.getPrice()));
            StrikethroughSpan span = new StrikethroughSpan();
            spanString.setSpan(span, 0, String.valueOf(goodsInfo.getPrice()).length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //避免无限次的appand
            if (cholder.tvDiscountPrice.getText().toString().length() > 0) {
                cholder.tvDiscountPrice.setText("");
            }
            cholder.tvDiscountPrice.append(spanString);
            cholder.tvBuyNum.setText("x" + goodsInfo.getNum());
            cholder.checkBox.setChecked(goodsInfo.isChoosed());
            cholder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsInfo.setChoosed(((CheckBox) v).isChecked());
                    cholder.checkBox.setChecked(((CheckBox) v).isChecked());
                    if (checkInterface != null) {
                        checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                    }
                }
            });
            cholder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modifyCountInterface != null) {
                        modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露增加接口
                    }
                }
            });
            cholder.btReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modifyCountInterface != null) {
                        modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露删减接口
                    }
                }
            });
            /********************方案一：弹出软键盘修改数量，应为又不知名的bug会使然键盘强行关闭***********************/
            /****在清单文件的activity下设置键盘：
             android:windowSoftInputMode="adjustUnspecified|stateHidden|adjustPan"
             android:configChanges="orientation|keyboardHidden"****/
            cholder.etNum.addTextChangedListener(new GoodsNumWatcher(goodsInfo));//监听文本输入框的文字变化，并且刷新数据
            notifyDataSetChanged();
            /********************方案一***************************************************************************/
            /********************方案二：让软键盘不能弹出，文本框不可编辑弹出dialog修改***********************/
            //            cholder.etNum.setOnFocusChangeListener(new android.view.View.
            //                    OnFocusChangeListener() {
            //                @Override
            //                public void onFocusChange(View v, boolean hasFocus) {//监听焦点的变化
            //                    if (hasFocus) {//获取到焦点也就是文本框被点击修改了
            //                        // 1，先强制键盘不弹出
            //                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
            //                        // 2.显示弹出dialog进行修改
            //                        showDialog(goodsInfo,cholder.etNum);
            //3.清除焦点防止不断弹出dialog和软键盘
            //                        cholder.etNum.clearFocus();
            // 4. 数据刷型
            //                        ShopcartAdapter.this.notifyDataSetChanged();
            //                    }
            //                }
            //            });
            /********************方案二***********************/
            //删除 购物车
            cholder.tvGoodsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog.Builder(mContext).create();
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
                                    modifyCountInterface.childDelete(groupPosition, childPosition);

                                }
                            });
                    alert.show();

                }
            });

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public void setOnAttrsClickListener(onAttrsClickListener onAttrsClickListener) {
        this.onAttrsClickListener = onAttrsClickListener;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEdtorListener {
        void groupEdit(int groupPosition);
    }

    public interface onAttrsClickListener{
        void onAttrsClick(String goods_id,String cart_id,int groupPosition,int childPosition);
    }

    /**
     * 使某个组处于编辑状态
     * <p/>
     * groupPosition组的位置
     */
    class GroupViewClick implements View.OnClickListener {
        private int                       groupPosition;
        private Button                    edtor;
        private CartShopBean group;

        public GroupViewClick(int groupPosition, Button edtor, CartShopBean group) {
            this.groupPosition = groupPosition;
            this.edtor = edtor;
            this.group = group;
        }

        @Override
        public void onClick(View v) {
            int groupId = v.getId();
            if (groupId == edtor.getId()) {
                if (group.isEdtor()) {
                    group.setEdtor(false);
                } else {
                    group.setEdtor(true);

                }
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 组元素绑定器
     */
    static class GroupViewHolder {
        @BindView(R.id.determine_chekbox)
        CheckBox determineChekbox;
        @BindView(R.id.tv_source_name)
        TextView tvSourceName;
        @BindView(R.id.tv_store_edtor)
        Button   tvStoreEdtor;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 子元素绑定器
     */
    static class ChildViewHolder {
        @BindView(R.id.check_box)
        CheckBox         checkBox;
        @BindView(R.id.sdv_pic)
        SimpleDraweeView sdvPic;
        @BindView(R.id.tv_intro)
        TextView         tvIntro;
        @BindView(R.id.tv_color_size)
        TextView         tvColorSize;
        @BindView(R.id.tv_price)
        TextView         tvPrice;
        @BindView(R.id.tv_discount_price)
        TextView         tvDiscountPrice;
        @BindView(R.id.tv_buy_num)
        TextView         tvBuyNum;
        @BindView(R.id.rl_no_edtor)
        RelativeLayout   rlNoEdtor;
        @BindView(R.id.bt_reduce)
        Button           btReduce;
        @BindView(R.id.et_num)
        EditText       etNum;
        @BindView(R.id.bt_add)
        Button         btAdd;
        @BindView(R.id.ll_change_num)
        LinearLayout llChangeNum;
        @BindView(R.id.tv_attrs)
        TextView       tvAttrs;
        @BindView(R.id.tv_goods_delete)
        TextView       tvGoodsDelete;
        @BindView(R.id.ll_edtor)
        LinearLayout   llEdtor;
        @BindView(R.id.stub)
        ViewStub       stub;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    /**
     * 购物车的数量修改编辑框的内容监听
     */
    class GoodsNumWatcher implements TextWatcher {
        CartGoodsBean   goodsInfo;
        public GoodsNumWatcher(CartGoodsBean goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!TextUtils.isEmpty(s.toString())){//当输入的数字不为空时，更新数字
                goodsInfo.setNum(Integer.valueOf(s.toString().trim()));
            }
        }

    }
}
