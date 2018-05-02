package com.alex.code.foundation.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import com.alex.code.foundation.utils.um.Tracker;
import com.alex.code.foundation.view.holder.LoadingHolder;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;
import com.orhanobut.dialogplus.DialogPlus;
import com.umeng.message.PushAgent;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvpViewStateActivity<V extends MvpView, P extends MvpPresenter<V>, VS extends ViewState<V>>
        extends MvpViewStateActivity<V, P, VS>
        implements HasSupportFragmentInjector, BaseMvpView {

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    Provider<P> mPresenterProvider;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    Provider<VS> mViewStateProvider;

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

    protected abstract int getLayoutId();

    protected abstract void init();

    // Delegate propagation ***********************

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mCompositeDisposable.dispose();
    }

    @NonNull
    @Override
    public P createPresenter() {
        return mPresenterProvider.get();
    }

    @NonNull
    @Override
    public VS createViewState() {
        return mViewStateProvider.get();
    }

    @Override
    public void onNewViewStateInstance() {
        onFirstCreate();
    }

    protected void onFirstCreate() {

    }

    protected void addDispose(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
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
