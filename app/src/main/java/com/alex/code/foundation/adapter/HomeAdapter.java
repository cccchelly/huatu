package com.alex.code.foundation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategoreGroupBean;
import com.alex.code.foundation.bean.CategroeChildBean;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.GridViewForScrollView;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

/**
 * Created by dth
 * Des: 右侧菜单适配器
 * Date: 2017/10/12.
 */

public class HomeAdapter extends BaseAdapter {

    private Context                 mContext;
    private List<CategoreGroupBean> mData;

    public HomeAdapter(Context context, List<CategoreGroupBean> data) {
        mContext = context;
        mData = data;
    }

    public void setData(List<CategoreGroupBean> data) {
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
        CategoreGroupBean dataBean = mData.get(position);
        List<CategroeChildBean> dataList = dataBean.getChild_list();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(mContext, dataList);
        viewHold.blank.setText(dataBean.getCategory_name());
        viewHold.gridView.setAdapter(adapter);
        viewHold.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategroeChildBean childBean = dataList.get(position);
                VLog.d("childBean.getCategory_name(): "+childBean.getCategory_name());
                ARouter.getInstance().build("/foundation/secondSearch")
                        .withInt("type",1)
                        .withString("category",childBean.getCategory_name())
                        .navigation();
            }
        });
        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView              blank;
    }

}
