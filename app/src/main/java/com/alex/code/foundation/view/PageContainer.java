package com.alex.code.foundation.view;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dth
 * Des: 商品详情
 * Date: 2017/10/26.
 */

public class PageContainer extends CoordinatorLayout implements Page.OnScrollListener {
    private Page child;
    private PageBehavior behavior;

    public PageContainer(Context context) {
        this(context, null);
    }

    public PageContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onScroll(float scrollY, float distance) {
        if (scrollY == -10000) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollTo(0, 0);
                }
            }, -10);
        } else {
            int y = (int) (getScrollY() - scrollY);
            if (y < distance) {
                return;
            }
            scrollTo(0, y);
            if (behavior != null) {
                behavior.setScrollY((int) (distance - y));
            }
        }
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof Page) {
            this.child = ((Page) child);
            this.child.setScrollListener(this);
        }

        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        if (layoutParams.getBehavior() != null && layoutParams.getBehavior() instanceof PageBehavior) {
            behavior = (PageBehavior) layoutParams.getBehavior();
        }

    }

    public void setOnPageChangeListener(PageBehavior.PageChangeListener listener) {
        if (behavior != null) {
            behavior.setOnPageChangeListener(listener);
        }
    }

}
