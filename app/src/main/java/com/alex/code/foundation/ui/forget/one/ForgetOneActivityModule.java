package com.alex.code.foundation.ui.forget.one;

import android.support.v7.app.AppCompatActivity;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PerFragment;
import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.ui.register.verification.VerificationDialogFragment;
import com.alex.code.foundation.ui.register.verification.VerificationFragmentModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by dth
 * Des:
 * Date: 2017/11/29.
 */

@Module(includes = BaseActivityModule.class)
public abstract class ForgetOneActivityModule {

    @Binds
    @PerActivity
    abstract AppCompatActivity appCompatActivity(ForgetOneActivity forgetOneActivity);

    @PerFragment
    @ContributesAndroidInjector(modules = VerificationFragmentModule.class)
    abstract VerificationDialogFragment verificationFragmentInjector();
}
