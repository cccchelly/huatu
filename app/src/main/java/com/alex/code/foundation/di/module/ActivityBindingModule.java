package com.alex.code.foundation.di.module;

import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.main.MainActivity;
import com.alex.code.foundation.main.MainActivityModule;
import com.alex.code.foundation.ui.login.LoginActivity;
import com.alex.code.foundation.ui.login.LoginActivityModule;
import com.alex.code.foundation.ui.splash.SplashActivity;
import com.alex.code.foundation.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity contributesSplashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributesLoginActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivity();
}
