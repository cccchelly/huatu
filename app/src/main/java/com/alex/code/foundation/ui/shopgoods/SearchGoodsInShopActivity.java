package com.alex.code.foundation.ui.shopgoods;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.utils.ToastInstance;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/16.
 */

@Route(path = "/foundation/searchGoodsInShop")
public class SearchGoodsInShopActivity extends BaseMvpActivity<ShopGoodsView, ShopGoodsPresenter> {

    @BindView(R.id.iv_back)
    ImageButton mIvBack;
    @BindView(R.id.et_search)
    EditText    mEtSearch;
    @BindView(R.id.tv_search)
    TextView    mTvSearch;

    @Inject
    ToastInstance mToastInstance;
    @Autowired
    public String shopId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_goods_in_shop;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        initListener();
    }

    private void initListener() {
        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            //避免提交两次 KeyEvent.ACTION_DOWN 和KeyEvent.ACTION_UP都会执行
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                search();
                return true;
            }
            return false;
        });
    }

    private void search() {
        String text = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            mToastInstance.showToast("搜索内容不能为空！");
            return;
        }
        ARouter.getInstance().build("/foundation/shopGoods")
                .withString("content", text)
                .withString("shopId",shopId)
                .navigation();
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }
}
