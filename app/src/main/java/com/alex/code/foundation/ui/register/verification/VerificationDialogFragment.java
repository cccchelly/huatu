package com.alex.code.foundation.ui.register.verification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseDialogFragment;
import com.alex.code.foundation.utils.ToastInstance;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class VerificationDialogFragment
        extends BaseDialogFragment<VerificationMvpView, VerificationMvpPresenter>
        implements VerificationMvpView {

    @Inject
    ToastInstance mToastInstance;

    @BindView(R.id.materialEditText)
    EditText mEditText;

    @BindView(R.id.simpleDraweeView)
    SimpleDraweeView mSimpleDraweeView;

    public static final String PHONE = "phone";
    private String mPhone;


//    public VerificationDialogFragment(String phone) {
//        mPhone = phone;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mPhone = bundle.getString(PHONE);
    }

    public static VerificationDialogFragment newInstance(String phone) {
        VerificationDialogFragment verificationDialogFragment = new VerificationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PHONE,phone);
        verificationDialogFragment.setArguments(bundle);

        return verificationDialogFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_verification_1;
    }

    @Override
    public void showGraphicVerificationCode(String url) {
        mSimpleDraweeView.setImageURI(url);
    }

    @Override
    public void verificationSuccessful() {

        dismiss();
    }

    @Override
    public void verificationFailed() {

        mToastInstance.showToast("验证码错误");
    }

    @OnClick(R.id.confirmButton)
    public void onConfirm() {
        String code = mEditText.getText().toString();
        if (TextUtils.isEmpty(code)) {
            mToastInstance.showCenterToast("验证码不能为空");
            return;
        }

        getPresenter().postVerificationCode(mPhone,code);
    }

    @OnClick(R.id.cancelButton)
    public void onCancel() {
        mToastInstance.showCenterToast("Cancel");
        dismiss();
    }

    @OnClick(R.id.simpleDraweeView)
    public void refreshCode() {
        getPresenter().fetchVerificationUrl(mPhone);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().fetchVerificationUrl(mPhone);
    }
}
