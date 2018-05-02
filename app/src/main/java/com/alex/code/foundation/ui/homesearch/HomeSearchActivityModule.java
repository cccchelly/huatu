package com.alex.code.foundation.ui.homesearch;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.ui.homesearch.fragment.HomeSearchFragment;
import com.alex.code.foundation.ui.homesearch.fragment.HomeSearchFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/10.
 */

@Module(includes = BaseActivityModule.class)
public abstract class HomeSearchActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(HomeSearchActivity homeSearchActivity);

    @PerFragment
    @ContributesAndroidInjector(modules = HomeSearchFragmentModule.class)
    abstract HomeSearchFragment homeSearchFragmentInjector();

}
