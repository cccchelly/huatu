package com.alex.code.foundation.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.code.foundation.R;


/**
 * Created by dth
 * Des:
 * Date: 2017/6/6.
 */

public class CustomToolBar extends FrameLayout{

    private Context     mContext;
    private TextView    mTvTitle;
    private ImageButton mIbBack;
    private RelativeLayout     mToolbar;
    private ImageButton mIbRight;
    private View mViewLine;
    private TextView mTvRight;

    public CustomToolBar(@NonNull Context context) {
        this(context,null);
    }

    public CustomToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.custom_toolbar_view, this);
        mToolbar = (RelativeLayout) findViewById(R.id.container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.textView);
        mIbBack = (ImageButton) findViewById(R.id.ib_back);
        mIbRight = (ImageButton) findViewById(R.id.ib_right);
        mViewLine = findViewById(R.id.view_line);
    }

    public CustomToolBar setSplitLineVisibility(int visibility) {
        mViewLine.setVisibility(visibility);
        return this;
    }


    public CustomToolBar setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public CustomToolBar setTitleColor(int color) {
        mTvTitle.setTextColor(color);
        return this;
    }

    public CustomToolBar setToolBarBackground(int color) {
        mToolbar.setBackgroundColor(color);
        return this;
    }

    public CustomToolBar setLeftImage(int resId) {
        mIbBack.setImageResource(resId);
        return this;
    }

    public CustomToolBar setRightImage(int resId) {
        mIbRight.setImageResource(resId);
        return this;
    }

    public CustomToolBar setRightText(String text) {
        mTvRight.setText(text);
        return this;
    }

    public CustomToolBar setRightTextVisibility(int visibility) {
        mTvRight.setVisibility(visibility);
        return this;
    }

    public CustomToolBar setLeftBackListener(final LeftBackListener listener) {
        mIbBack.setVisibility(VISIBLE);
        mIbBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftBack();
                }
            }
        });

        return this;
    }

    public CustomToolBar setRightImgListener(final RightImgListener listener) {
        mIbRight.setVisibility(VISIBLE);
        mIbRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightListener();
                }
            }
        });

        return this;
    }

    public CustomToolBar setRightTextListener(RightTextListener listener) {
        mTvRight.setVisibility(VISIBLE);
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightText();
                }
            }
        });

        return this;
    }

    public interface LeftBackListener{
        void onLeftBack();
    }

    public interface RightImgListener{
        void onRightListener();
    }

    public interface RightTextListener{
        void onRightText();
    }
}
