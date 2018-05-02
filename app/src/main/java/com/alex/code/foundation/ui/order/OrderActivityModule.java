package com.alex.code.foundation.ui.order;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.ui.order.fragment.OrderFragment;
import com.alex.code.foundation.ui.order.fragment.OrderFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/8.
 */

@Module(includes = BaseActivityModule.class)
public abstract class OrderActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(OrderActivity orderActivity);

    @PerFragment
    @ContributesAndroidInjector(modules = OrderFragmentModule.class)
    abstract OrderFragment orderFragmentInjector();
}
