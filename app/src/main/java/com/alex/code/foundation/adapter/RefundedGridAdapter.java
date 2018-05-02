package com.alex.code.foundation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.RefundedListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/25.
 */

public class RefundedGridAdapter extends BaseAdapter{

    private Context mContext;
    private List<RefundedListBean.DataEntity.GoodsListEntity> mData = new ArrayList<>();

    public RefundedGridAdapter(Context context, List<RefundedListBean.DataEntity.GoodsListEntity> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        RefundedListBean.DataEntity.GoodsListEntity goodsListEntity = mData.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_grid_refund, null);
            viewHolder = new ViewHolder();
            viewHolder.sdv_pic = (SimpleDraweeView) convertView.findViewById(R.id.sdv_pic);
            viewHolder.tv_goods_des = (TextView) convertView.findViewById(R.id.tv_goods_des);
            viewHolder.tv_attr = (TextView) convertView.findViewById(R.id.tv_attr);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.tv_refund_status = (TextView) convertView.findViewById(R.id.tv_refund_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.sdv_pic.setImageURI(AppConstants.PIC_BASE_URL + goodsListEntity.getPicture());
        viewHolder.tv_goods_des.setText(goodsListEntity.getGoods_name());
        viewHolder.tv_attr.setText(goodsListEntity.getSku_name());
        viewHolder.tv_price.setText("单价: "+goodsListEntity.getPrice());
        viewHolder.tv_num.setText("数量: "+goodsListEntity.getNum());
        //退款状态[-3退款申请不通过,-2退款关闭,-1拒绝退款,1买家申请退款,2等待买家退货,3等待卖家确认收货,4等待卖家确认退款,5退款成功]
        switch (goodsListEntity.getRefund_status()) {
            case -3:
                viewHolder.tv_refund_status.setText("退款申请不通过");
                break;
            case -2:
                viewHolder.tv_refund_status.setText("退款关闭");
                break;
            case -1:
                viewHolder.tv_refund_status.setText("拒绝退款");
                break;
            case 1:
                viewHolder.tv_refund_status.setText("买家申请退款");
                break;
            case 2:
                viewHolder.tv_refund_status.setText("等待买家退货");
                break;
            case 3:
                viewHolder.tv_refund_status.setText("等待卖家确认收货");
                break;
            case 4:
                viewHolder.tv_refund_status.setText("等待卖家确认退款");
                break;
            case 5:
                viewHolder.tv_refund_status.setText("退款成功");
                break;
            default:
                viewHolder.tv_refund_status.setText("我要退款");
        }
        return convertView;
    }


    private static class ViewHolder {
        private SimpleDraweeView sdv_pic;
        private TextView         tv_goods_des;
        private TextView         tv_attr;
        private TextView         tv_price;
        private TextView         tv_num;
        private TextView         tv_refund_status;
    }
}
