package com.alex.code.foundation.ui.details.attrs;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpFragment;
import com.alex.code.foundation.bean.GoodsDetailBean;
import com.alex.code.foundation.utils.WebViewUtils;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

public class AttrsFragment extends BaseMvpFragment<AttrsView, AttrsPresenter> implements AttrsView {

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.webView)
    WebView      mWebView;
    public static final String GOODS_ID = "goods_id";
    private String mGoodsId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attrs;
    }

    @Override
    protected void init() {

        WebViewUtils.initWebView(getContext(),mWebView);

        Bundle bundle = getArguments();
        mGoodsId = bundle.getString(GOODS_ID);
    }

    @Override
    public void fetchData() {

        getPresenter().getGoodsDetail(mGoodsId);
    }

    public static AttrsFragment newInstance(String goodsId) {
        AttrsFragment attrsFragment = new AttrsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GOODS_ID,goodsId);
        attrsFragment.setArguments(bundle);
        return attrsFragment;
    }

    @Override
    public void onSuccess(GoodsDetailBean goodsDetailBean) {
        mWebView.loadUrl(goodsDetailBean.getUrl());
    }
}
