package com.alex.code.foundation.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;

import com.alex.code.foundation.di.module.BaseActivityModule;
import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.holder.LoadingHolder;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.orhanobut.dialogplus.DialogPlus;
import com.umeng.message.PushAgent;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P>
        implements HasSupportFragmentInjector, BaseMvpView{

    @Inject
    @Named(BaseActivityModule.ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    protected FragmentManager fragmentManager;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    Provider<P> mPresenterProvider;

    protected Unbinder mUnbinder;
    private DialogPlus mDialogPlus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        init();
        PushAgent.getInstance(this).onAppStart();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    // Delegate propagation ***********************


    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getDefaultTracker().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tracker.getDefaultTracker().onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mCompositeDisposable.dispose();
        super.onDestroy();
    }

    @NonNull
    @Override
    public P createPresenter() {
        return mPresenterProvider.get();
    }

    protected void addDispose(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    protected final void addFragment(@IdRes int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    @Override
    public void showLoading(String msg) {
        if (mDialogPlus == null) {
            mDialogPlus = DialogPlus.newDialog(this)
                    .setContentHolder(new LoadingHolder(msg))
                    .setGravity(Gravity.CENTER)
                    .setCancelable(true)
                    .setContentBackgroundResource(Color.TRANSPARENT)
                    .setOverlayBackgroundResource(Color.TRANSPARENT)
                    .create();
        }
        mDialogPlus.show();
    }

    @Override
    public void dismissLoading() {
        if (mDialogPlus != null && mDialogPlus.isShowing()) {
            mDialogPlus.dismiss();
            mDialogPlus = null;
        }
    }
}
