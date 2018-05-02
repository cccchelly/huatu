package com.alex.code.foundation.ui.login;

import com.alex.code.foundation.base.BaseMvpView;

public interface LoginView extends BaseMvpView {

    void showLoginForm();

    void showError();

    void showLoading();

    void loginSuccessful();
}
