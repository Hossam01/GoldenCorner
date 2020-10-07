package com.golden.goldencorner.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.data.receiver.NetworkReceiver;
import com.golden.goldencorner.ui.accountActivation.AccountActivationActivity;
import com.golden.goldencorner.ui.forgetPassword.ForgetPasswordActivity;
import com.golden.goldencorner.ui.signup.SignUpActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends BaseActivity implements NetworkReceiver.NetworkReceiverListener {


    @BindView(R.id.login_activity_close_icn)
    ImageView loginActivityCloseIcn;
    @BindView(R.id.login_activity_login_txt)
    TextView loginActivityLoginTxt;
    @BindView(R.id.loginLogoIV)
    ImageView loginLogoIV;
    @BindView(R.id.usernameET)
    EditText usernameET;
    @BindView(R.id.login_activity_phone_layout)
    LinearLayout loginActivityPhoneLayout;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.login_activity_password_layout)
    LinearLayout loginActivityPasswordLayout;
    @BindView(R.id.saveBtn)
    CircularProgressButton loginBtn;
    @BindView(R.id.login_activity_phone_txt)
    TextView loginActivityPhoneTxt;
    @BindView(R.id.login_activity_password_txt)
    TextView loginActivityPasswordTxt;
    @BindView(R.id.forgetPasswordBtn)
    CircularProgressButton forgetPasswordBtn;
    @BindView(R.id.registerBtn)
    CircularProgressButton registerBtn;
    @BindView(R.id.accountActivateBtn)
    CircularProgressButton accountActivateBtn;
    @BindView(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    private LoginViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this)
                .get(LoginViewModel.class);
        setCoordinatorLayout(mCoordinatorLayout);
        NetworkReceiver.getInstance().setNetworkReceiverListener(this);
        mViewModel.removeDataFromShared();
        subscribeLoginObserver();
    }

    private void subscribeLoginObserver() {
        mViewModel.getLoginLiveData().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> resource) {
                if (resource != null){
                    switch (resource.getStatus()){
                        case SUCCESS:
                            showProgressBar(false);
                            navToMainOnSuccessLogin();
                            break;
                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(getApplicationContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            showProgressBar(true);
                            break;
                    }
                }
            }
        });
    }

    private void navToMainOnSuccessLogin() {
        setResult(RESULT_OK, new Intent());
        finish();
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            loginBtn.startMorphAnimation();
        } else {
            loginBtn.stopAnimation();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkReceiverChange(boolean flag) {
        super.onNetworkReceiverChange(flag);
        if (!flag) {
            if (getCoordinatorLayout() != null)
                showSnackBar(getCoordinatorLayout(), getString(R.string.no_internet_connection));
            else
                showToast(getString(R.string.no_internet_connection));
        } else {
            hideSnackBar();
        }
    }




    @OnClick({R.id.saveBtn,
            R.id.forgetPasswordBtn,
            R.id.registerBtn,
            R.id.accountActivateBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.saveBtn:
                onLogin();
                break;
            case R.id.forgetPasswordBtn:
                onForgetPassword();
                break;
            case R.id.registerBtn:
                onRegister();
                break;
            case R.id.accountActivateBtn:
                onAccountActivate();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void onRegister() {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }

    private void onAccountActivate() {
        startActivity(new Intent(getApplicationContext(), AccountActivationActivity.class));
        finish();
    }

    private void onForgetPassword() {
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
        finish();
    }

    private void onLogin() {
        String username  = usernameET.getText().toString();
        String password  = passwordET.getText().toString();

        if (mViewModel.isUserNameValid(username)){
            usernameET.setError(getString(R.string.this_field_is_required));
        }
        if (mViewModel.isPasswordValid(password)){
            usernameET.setError(getString(R.string.this_field_is_required));
        }
        mViewModel.invokeLogin(username,password);
    }
}
