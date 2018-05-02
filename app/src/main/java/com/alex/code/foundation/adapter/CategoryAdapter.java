package com.alex.code.foundation.adapter;

import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategoreGroupBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoreGroupBean,BaseViewHolder> {

    public CategoryAdapter() {
        super(R.layout.item_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoreGroupBean item) {

        helper.setText(R.id.tvName, item.getCategory_name())
                .addOnClickListener(R.id.tvName);
    }
}
