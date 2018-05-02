package com.alex.code.foundation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.ImagesEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/31.
 */

public class CommentPicAdapter extends BaseAdapter{

    private  Context                                                                 mContext;
    private  List<ImagesEntity> mData = new ArrayList<>();

    public CommentPicAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ImagesEntity> data) {
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
        ImagesEntity imageEntity = mData.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_comment_pic, null);
            viewHold = new ViewHold();
            viewHold.sdv_pic = (SimpleDraweeView) convertView.findViewById(R.id.sdv_pic);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.sdv_pic.setImageURI(AppConstants.PIC_BASE_URL + imageEntity.getPic_cover());
        return convertView;

    }

    private static class ViewHold {
        private SimpleDraweeView sdv_pic;
    }
}
