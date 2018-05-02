package com.alex.code.foundation.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategoreGroupBean;
import com.alex.code.foundation.utils.VLog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class WaresAllAdapter extends BaseQuickAdapter<CategoreGroupBean,BaseViewHolder> {

    private WaresAdapter mWaresAdapter;
    int height;

    public WaresAllAdapter() {
        super(R.layout.item_wares_all);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoreGroupBean item) {

        RecyclerView recyclerView = helper.getView(R.id.recyclerview);

            mWaresAdapter = new WaresAdapter();
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(mWaresAdapter);
            mWaresAdapter.setNewData(item.getChild_list());
        helper.setText(R.id.tv_Category, item.getCategory_name());
        VLog.d("item: "+item.getCategory_name()+ "child: "+item.getChild_list().size());

    }
}
