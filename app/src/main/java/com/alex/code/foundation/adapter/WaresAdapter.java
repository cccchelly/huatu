package com.alex.code.foundation.adapter;

import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.R;
import com.alex.code.foundation.bean.CategroeChildBean;
import com.alex.code.foundation.utils.VLog;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class WaresAdapter extends BaseSectionQuickAdapter<CategroeChildBean,BaseViewHolder> {


    public WaresAdapter() {
        super(R.layout.item_wares, R.layout.item_wares_head,null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategroeChildBean item) {
        SimpleDraweeView sdvWears = helper.getView(R.id.sdv_wears);
        sdvWears.setImageURI(AppConstants.PIC_BASE_URL+item.getCategory_pic());
        helper.setText(R.id.tv_wears, item.getShort_name());
    }

    @Override
    protected void convertHead(BaseViewHolder helper, CategroeChildBean item) {

        VLog.d("convertHead: " +item.getCategory_name()+ "   "+item.header);
        helper.setText(R.id.tv_type, item.getCategory_name());
    }


}
