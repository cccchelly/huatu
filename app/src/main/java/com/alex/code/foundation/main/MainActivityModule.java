package com.alex.code.foundation.main;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.main.category.CategoryFragment;
import com.alex.code.foundation.main.category.CategoryFragmentModule;
import com.alex.code.foundation.main.home.HomeFragment;
import com.alex.code.foundation.main.home.HomeFragmentModule;
import com.alex.code.foundation.main.mine.MineFragment;
import com.alex.code.foundation.main.mine.MineFragmentModule;
import com.alex.code.foundation.main.shop.ShopFragment;
import com.alex.code.foundation.main.shop.ShopFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/9.
 */
@Module(includes = BaseActivityModule.class)
public abstract class MainActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(MainActivity mainActivity);

    @PerFragment
    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment homeFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = CategoryFragmentModule.class)
    abstract CategoryFragment classifyFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = ShopFragmentModule.class)
    abstract ShopFragment shopFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = MineFragmentModule.class)
    abstract MineFragment mineFragmentInjector();
}
