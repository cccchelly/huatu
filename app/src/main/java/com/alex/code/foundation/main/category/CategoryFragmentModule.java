package com.alex.code.foundation.main.category;

import android.support.v4.app.Fragment;

import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseFragmentModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseFragmentModule.class)
public abstract class CategoryFragmentModule {

    @Binds
    @PerFragment
    @Named(BaseFragmentModule.FRAGMENT)
    abstract Fragment fragment(CategoryFragment classifyFragment);
}
