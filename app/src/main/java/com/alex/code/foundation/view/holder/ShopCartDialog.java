package com.alex.code.foundation.view.holder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.CounterView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by dth
 * Des:
 * Date: 2017/10/30.
 */

public class ShopCartDialog extends Dialog {

    private Context mContext;
    private SimpleDraweeView mSdvGoods;
    private TextView mTvGoodsDes;
    private TextView mTvPrice;
    private CounterView mCounterView;
    private TextView mTvColor;
    private TextView mTvAttrs;
    private TextView mTvConfirm;
    private ImageButton mIbClose;
    private GoodSpeBean mData;
    private LinearLayout mLlAttrContainer;
    ArrayList<TagFlowLayout> tagFlowLayouts = new ArrayList<>();
    private confirmListener mListener;
    private int mType;
    private int mCount = 1;
    private TextView mTvRepertory;

    public ShopCartDialog(@NonNull Context context) {
        super(context, R.style.BottomDialog);
        mContext = context;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

//            View view = getWindow().getDecorView();
//            WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
//            lp.gravity = Gravity.BOTTOM;
//            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;;
//            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            getWindow().getWindowManager().updateViewLayout(view, lp);


        Window window = getWindow();
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_shopcart);
        setCancelable(true);

        mSdvGoods = (SimpleDraweeView) findViewById(R.id.sdv_goods);
        mTvGoodsDes = (TextView) findViewById(R.id.tv_goods_des);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mCounterView = (CounterView) findViewById(R.id.counterView);
        mTvColor = (TextView) findViewById(R.id.tv_color);
        mTvAttrs = (TextView) findViewById(R.id.tv_attrs);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvRepertory = (TextView) findViewById(R.id.tv_repertory);
//        mFlowlayoutColor = (TagFlowLayout) findViewById(R.id.flowlayout_color);
//        mFlowlayoutAttrs = (TagFlowLayout) findViewById(R.id.flowlayout_attrs);
        mIbClose = (ImageButton) findViewById(R.id.ib_close);
        mLlAttrContainer = (LinearLayout) findViewById(R.id.ll_attr_container);

        initListener();
    }

    public void setData(GoodSpeBean data) {
        mData = data;
        notifyData();
    }

    public void setType(int type) {
        mType = type;
    }

    private void notifyData() {
        tagFlowLayouts.clear();
        mLlAttrContainer.removeAllViews();
        mTvGoodsDes.setText(mData.getIntroduction());
        List<GoodSpeBean.SpecListEntity> spec_list = mData.getSpec_list();
        List<GoodSpeBean.SkuListEntity> sku_list = mData.getSku_list();
        for (GoodSpeBean.SpecListEntity specListEntity : spec_list) {
            View view = View.inflate(mContext, R.layout.goods_attr_view, null);
            mLlAttrContainer.addView(view);
            TextView tvAttr = (TextView) view.findViewById(R.id.tv_attr);
            TagFlowLayout flowlayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);

            tagFlowLayouts.add(flowlayout);
            tvAttr.setText(specListEntity.getSpec_name());

            flowlayout.setMaxSelectCount(1);
            List<GoodSpeBean.SpecListEntity.ValueEntity> value = specListEntity.getValue();
            flowlayout.setAdapter(new TagAdapter<GoodSpeBean.SpecListEntity.ValueEntity>(value) {
                @Override
                public View getView(FlowLayout parent, int position, GoodSpeBean.SpecListEntity.ValueEntity o) {
                    TextView textView = (TextView) View.inflate(mContext, R.layout.tag_shop_cart,null);
                    textView.setText(o.getSpec_value_name());
                    return textView;
                }

                @Override
                public void onSelected(int position, View view) {
                    super.onSelected(position, view);
                    view.setClickable(true);
                }

                @Override
                public void unSelected(int position, View view) {
                    super.unSelected(position, view);
                    view.setClickable(false);
                }
            });
            flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    GoodSpeBean.SpecListEntity.ValueEntity valueEntity = value.get(position);
                    flowlayout.setTag(valueEntity.getSpec_value_name());
                    if (tagFlowLayouts.size() == spec_list.size()) {
                        for (GoodSpeBean.SkuListEntity skuListEntity : sku_list) {
                            if (!TextUtils.isEmpty(getSkuKey()) && TextUtils.equals(skuListEntity.getSku_name(), getSkuKey())) {
                                mTvRepertory.setText("库存: "+skuListEntity.getStock()+"  规格: "+skuListEntity.getSku_name());
                                mTvPrice.setText("￥ " + skuListEntity.getPrice());
                                mSdvGoods.setImageURI(AppConstants.PIC_BASE_URL+skuListEntity.getPicture());
                            }
                        }
                    }
                    return true;
                }
            });

            flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    for (Integer integer : selectPosSet) {
                        VLog.d("integer: "+integer);
                    }
                }
            });
//            flowlayout.getAdapter().setSelected(0, value.get(0));
        }
    }

    private String getSkuKey() {
        StringBuilder sb = new StringBuilder();
        for (TagFlowLayout tagFlowLayout : tagFlowLayouts) {
            String tag = (String) tagFlowLayout.getTag();
            if (TextUtils.isEmpty(tag)) {
//                Toast.makeText(mContext, "请选择规格参数", Toast.LENGTH_SHORT).show();
                return "";
            }
            sb.append(tag + " ");
        }

        return sb.toString();
    }

    private void initListener() {
        mIbClose.setOnClickListener(v -> dismiss());
        mCounterView.setCallback(count -> mCount = count);
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData == null)return;

                if (TextUtils.isEmpty(getSkuKey())) {
                    Toast.makeText(mContext, "请选择规格参数", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<GoodSpeBean.SkuListEntity> sku_list = mData.getSku_list();
                for (GoodSpeBean.SkuListEntity skuListEntity : sku_list) {
                    if (!TextUtils.isEmpty(getSkuKey()) && TextUtils.equals(skuListEntity.getSku_name(), getSkuKey())) {
                        int id = skuListEntity.getId();
                        int goods_id = skuListEntity.getGoods_id();
                        String price = skuListEntity.getPrice();
                        double totalPrice = Double.parseDouble(price) * mCount;
                        if (mListener != null) {
                            mListener.onConfirm(mType, totalPrice,goods_id+"",mCount+"",id+"");
                        }
                    }
                }

            }
        });


    }

    public interface confirmListener{
        void onConfirm(int type, double totalPrice, String goods_id, String goods_num, String sku_id);
    }

    public void setConfirmListener(confirmListener listener) {
        mListener = listener;
    }

}
