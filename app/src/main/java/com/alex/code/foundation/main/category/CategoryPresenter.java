package com.alex.code.foundation.main.category;

import com.alex.code.foundation.base.BaseMvpPresenter;
import com.alex.code.foundation.base.BaseObserver;
import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.CategoryBean;
import com.alex.code.foundation.data.AppDataManager;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dth
 * Des:
 * Date: 2017/10/12.
 */

public class CategoryPresenter extends BaseMvpPresenter<CategoryView> {

    @Inject
    AppDataManager mAppDataManager;

    @Inject
    public CategoryPresenter() {
    }

    public void getCategoryInfo() {
        mAppDataManager.getCategoryInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse<CategoryBean>>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse<CategoryBean> response) {

                        CategoryBean data = response.getData();
                        getView().showCategoryData(data.getList());
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        addDisposable(d);
                    }
                });
    }
}
