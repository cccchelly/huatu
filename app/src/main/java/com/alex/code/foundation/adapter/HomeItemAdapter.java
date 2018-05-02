package com.alex.code.foundation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategroeChildBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by dth
 * Des: 右侧子菜单适配器
 * Date: 2017/10/12.
 */

public class HomeItemAdapter extends BaseAdapter {

    private Context                 mContext;
    private List<CategroeChildBean> mData;

    public HomeItemAdapter(Context context, List<CategroeChildBean> data) {
        mContext = context;
        mData = data;
    }

    public void setData(List<CategroeChildBean> data) {
        mData = data;
        notifyDataSetChanged();
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
        CategroeChildBean subcategory = mData.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (SimpleDraweeView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(subcategory.getCategory_name());
        viewHold.iv_icon.setImageURI(AppConstants.PIC_BASE_URL + subcategory.getCategory_pic());
        return convertView;

    }

    private static class ViewHold {
        private TextView         tv_name;
        private SimpleDraweeView iv_icon;
    }

}
