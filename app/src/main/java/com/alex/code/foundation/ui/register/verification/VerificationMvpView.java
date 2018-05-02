package com.alex.code.foundation.ui.register.verification;

import com.alex.code.foundation.base.BaseMvpView;

public interface VerificationMvpView extends BaseMvpView {

    void showGraphicVerificationCode(String url);

    void verificationSuccessful();

    void verificationFailed();
}
