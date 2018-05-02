package com.alex.code.foundation.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.alex.code.foundation.di.annotation.ApplicationContext;

public class ToastInstance {

    private String mPreMessage;

    private long mTime;

    private final static long DELAY_TIME = 2000;

    private Context mContext;

    public ToastInstance(@ApplicationContext Context context) {
        mContext = context;
    }

    public void showToast(String message) {
        if (TextUtils.equals(message, mPreMessage)) {
            if (System.currentTimeMillis() - mTime > DELAY_TIME) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                mTime = System.currentTimeMillis();
            }
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            mTime = System.currentTimeMillis();
        }

        mPreMessage = message;
    }

    public void showCenterToast(String message) {
        if (TextUtils.equals(message, mPreMessage)) {
            if (System.currentTimeMillis() - mTime > DELAY_TIME) {
                Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                mTime = System.currentTimeMillis();

            }
        } else {
            Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            mTime = System.currentTimeMillis();
        }

        mPreMessage = message;
    }

}

