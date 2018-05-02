package com.alex.code.foundation.ui.details.goods;

import android.support.v4.app.Fragment;

import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseFragmentModule;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/27.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class GoodsFragmentModule {

    @Binds
    @PerFragment
    @Named(BaseFragmentModule.FRAGMENT)
    abstract Fragment fragment(GoodsFragment goodsFragment);
}
