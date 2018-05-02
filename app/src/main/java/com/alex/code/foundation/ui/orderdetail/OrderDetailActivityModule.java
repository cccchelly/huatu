package com.alex.code.foundation.ui.orderdetail;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.module.BaseActivityModule;

import dagger.Binds;
import dagger.Module;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/6.
 */

@Module(includes = BaseActivityModule.class)
public abstract class OrderDetailActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(OrderDetailActivity orderDetailActivity);
}
