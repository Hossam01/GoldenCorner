package com.golden.goldencorner.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.Utils.Utils;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.receiver.NetworkReceiver;
import com.golden.goldencorner.ui.accountActivation.AccountActivationActivity;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements NetworkReceiver.NetworkReceiverListener {


    @BindView(R.id.signup_activity_firstname_edit_text)
    EditText signup_activity_firstname_edit_text;
    @BindView(R.id.signup_activity_family_name_edit_text)
    EditText signup_activity_family_name_edit_text;
    @BindView(R.id.signup_activity_phone_edit_text)
    EditText signup_activity_phone_edit_text;
    @BindView(R.id.signup_activity_email_edit_text)
    EditText signup_activity_email_edit_text;
    @BindView(R.id.signup_activity_password_edit_text)
    EditText signup_activity_password_edit_text;
    @BindView(R.id.signup_activity_password__confirm_edit_text)
    EditText signup_activity_password__confirm_edit_text;
    @BindView(R.id.signup_activity_rules_checkbox)
    CheckBox signup_activity_rules_checkbox;
    @BindView(R.id.signupBtn)
    CircularProgressButton signupBtn;
    @BindView(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.mDilatingDotsProgressBar)
    DilatingDotsProgressBar mDilatingDotsProgressBar;

    private SignUpViewModel mViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this)
                .get(SignUpViewModel.class);
        NetworkReceiver.getInstance().setNetworkReceiverListener(this);
        subscribeObserver();


    }

    private void subscribeObserver() {
        mViewModel.getSignUpLiveData().observe(this,
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    showToast(resource.getMessage());
                                    navToActivation();
                                    break;
                                case ERROR:
                                    showProgressBar(false);
                                    showToast(resource.getMessage());
                                    break;
                                case LOADING:
                                    showProgressBar(true);
                                    break;
                            }
                        }
                    }
                });
    }

    private void navToActivation() {
        startActivity(new Intent(getApplicationContext(), AccountActivationActivity.class));
        finish();
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            signupBtn.startMorphAnimation();
            signupBtn.setBackground(this.getDrawable(R.drawable.rounded_red_bg));
        } else {
            signupBtn.stopAnimation();
            signupBtn.setBackground(this.getDrawable(R.drawable.login_button_shape));
        }
    }


    @OnClick(R.id.signupBtn)
    public void onSignupBtnClicked() {
        String firstname_edit_text = signup_activity_firstname_edit_text.getText().toString();
        String family_name_edit_text = signup_activity_family_name_edit_text.getText().toString();
        String phone_edit_text = signup_activity_phone_edit_text.getText().toString();
        String email_edit_text = signup_activity_email_edit_text.getText().toString();
        String password_edit_text = signup_activity_password_edit_text.getText().toString();
        String password__confirm_edit_text = signup_activity_password__confirm_edit_text.getText().toString();

        if (TextUtils.isEmpty(firstname_edit_text)){
            signup_activity_firstname_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(family_name_edit_text)){
            signup_activity_family_name_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(phone_edit_text)){
            signup_activity_phone_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(email_edit_text)){
            signup_activity_email_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (!mViewModel.isEmailValid(email_edit_text)){
            signup_activity_email_edit_text.setError(getString(R.string.invalid_email));
            return;
        }
        if (TextUtils.isEmpty(password_edit_text)){
            signup_activity_password_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(password__confirm_edit_text)){
            signup_activity_password__confirm_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (!password__confirm_edit_text.equals(password_edit_text)) {
                showToast(getString(R.string.password_not_match));
        }
        if (signup_activity_rules_checkbox.isChecked()){
            showToast(getString(R.string.accept_terms_and_conditions_please));
        }
        mViewModel.invokeSignUp(firstname_edit_text,
                family_name_edit_text,
                phone_edit_text,
                password_edit_text,
                password__confirm_edit_text,
                email_edit_text);

    }

    @Override
    public void onNetworkReceiverChange(boolean flag) {
        super.onNetworkReceiverChange(flag);
        if (!flag) {
            if (getCoordinatorLayout() != null)
                showSnackBar(getCoordinatorLayout(), getString(R.string.no_internet_connection));
            else
                Utils.getInstance().showToast(this, getString(R.string.no_internet_connection));
        } else {
            hideSnackBar();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //hideNavigation();
    }

    private void hideNavigation() {
        // Removal of status and navigation bar
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


}
