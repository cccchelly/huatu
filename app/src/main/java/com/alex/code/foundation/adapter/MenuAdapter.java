package com.alex.code.foundation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategoreGroupBean;

import java.util.List;

/**
 * Created by dth
 * Des: 左侧菜单适配器
 * Date: 2017/10/12.
 */

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private int selectItem = 0;
    private List<CategoreGroupBean> mData;

    public MenuAdapter(Context context, List<CategoreGroupBean> list) {
        mData = list;
        mContext = context;
    }

    public void setData(List<CategoreGroupBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_menu, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectItem) {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.btn_color));
        } else {
            holder.tv_name.setBackgroundColor(mContext.getResources().getColor(R.color.bg_f6f6f6));
            holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.text_333333));
        }
        holder.tv_name.setText(mData.get(position).getCategory_name());
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
    }
}
