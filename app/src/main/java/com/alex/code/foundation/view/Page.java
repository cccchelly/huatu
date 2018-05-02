package com.alex.code.foundation.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by dth
 * Des: 商品详情
 * Date: 2017/10/26.
 */

public class Page extends NestedScrollView {
    private boolean scrollAble = true;
    private float y;
    private float oldy = 0;
    private OnScrollListener onScrollListener;

    public Page(Context context) {
        this(context, null);
    }

    public Page(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Page(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration.getTouchSlop();
    }

    public void setScrollAble(boolean scrollAble) {
        this.scrollAble = scrollAble;
    }

    public void setScrollAble(boolean scrollAble, float y) {
        this.scrollAble = scrollAble;
        this.y = y;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollAble) {
            return super.onTouchEvent(ev);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float distanceY = 0;
                    float newY = 0;
                    if (oldy == 0) {
                        distanceY = 1;
                        newY = ev.getRawY();
                    } else {
                        newY = ev.getRawY();
                        distanceY = newY - oldy;
                    }
                    oldy = newY;

                    if (onScrollListener != null) {
                        onScrollListener.onScroll(distanceY, y);
                    }

                    return true;
                case MotionEvent.ACTION_UP:

                    if (onScrollListener != null) {
                        onScrollListener.onScroll(-10000, y);
                    }
                    scrollAble = true;
                    oldy = 0;
                    return true;
                default:
                    return true;
            }

        }
    }

    public void setScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    public interface OnScrollListener {
        void onScroll(float scrollY, float y);
    }

}
