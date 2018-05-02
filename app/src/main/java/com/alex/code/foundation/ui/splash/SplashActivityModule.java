package com.alex.code.foundation.ui.splash;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.module.BaseActivityModule;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class SplashActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(SplashActivity splashActivity);
}
