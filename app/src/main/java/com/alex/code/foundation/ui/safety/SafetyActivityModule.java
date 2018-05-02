package com.alex.code.foundation.ui.safety;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.module.BaseActivityModule;

import dagger.Binds;
import dagger.Module;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/19.
 */

@Module(includes = BaseActivityModule.class)
public abstract class SafetyActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(SafetyActivity safetyActivity);

}
