package com.alex.code.foundation.view.holder;

import android.view.View;

import com.alex.code.foundation.R;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.CounterView;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/30.
 */

public class ShopCartHolder extends ViewHolder {

    public ShopCartHolder() {
        super(R.layout.dialog_add_shopcart);
    }

    @Override
    public View getInflatedView() {
        View view = super.getInflatedView();
        CounterView counterView = (CounterView) view.findViewById(R.id.counterView);
        counterView.setCallback(new CounterView.IChangeCoutCallback() {
            @Override
            public void change(int count) {
                VLog.d("count: "+count);
            }
        });
        return view;
    }

}
