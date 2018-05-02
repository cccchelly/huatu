package com.alex.code.foundation.ui.details;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.ui.details.attrs.AttrsFragment;
import com.alex.code.foundation.ui.details.attrs.AttrsFragmentModule;
import com.alex.code.foundation.ui.details.comment.CommentFragment;
import com.alex.code.foundation.ui.details.comment.CommentFragmentModule;
import com.alex.code.foundation.ui.details.goods.GoodsFragment;
import com.alex.code.foundation.ui.details.goods.GoodsFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/26.
 */

@Module(includes = BaseActivityModule.class)
public abstract class GoodsDetailActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(GoodsDetailActivity goodsDetailActivity);

    @PerFragment
    @ContributesAndroidInjector(modules = GoodsFragmentModule.class)
    abstract GoodsFragment goodsFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = CommentFragmentModule.class)
    abstract CommentFragment commentFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector(modules = AttrsFragmentModule.class)
    abstract AttrsFragment attrsFragmentInjector();
}
