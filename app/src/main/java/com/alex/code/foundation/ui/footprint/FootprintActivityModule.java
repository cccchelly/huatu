package com.alex.code.foundation.ui.footprint;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.module.BaseActivityModule;

import dagger.Binds;
import dagger.Module;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/23.
 */

@Module(includes = BaseActivityModule.class)
public abstract class FootprintActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(FootprintActivity footprintActivity);
}
