package com.golden.goldencorner.ui.accountActivation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.ActivateUserRecords;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.login.LogInActivity;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivationActivity extends BaseActivity {

    @BindView(R.id.edit_password_activity_close_icn)
    ImageView edit_password_activity_close_icn;
    @BindView(R.id.mobileNumberET)
    EditText mobileNumberET;
    @BindView(R.id.codeTVET)
    EditText codeTVET;
    @BindView(R.id.activateBtn)
    CircularProgressButton activateBtn;
    @BindView(R.id.mDilatingDotsProgressBar)
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    private AccountActivationViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activation);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this)
                .get(AccountActivationViewModel.class);
        subscribeObserver();


    }

    private void subscribeObserver() {
        mViewModel.getActivateLiveData().observe(this,
                new Observer<Resource<ActivateUserRecords>>() {
                    @Override
                    public void onChanged(Resource<ActivateUserRecords> resource) {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case SUCCESS:
                                    showProgressBar(false);
                                    showToast(resource.getData().getMessage());
                                    if (resource.getData().getStatus() != 0) {
                                        navToLogin();
                                    }
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

    private void navToLogin() {
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        finish();
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            activateBtn.startMorphAnimation();
            activateBtn.setBackground(this.getDrawable(R.drawable.rounded_red_bg));
        } else {
            activateBtn.stopAnimation();
            activateBtn.setBackground(this.getDrawable(R.drawable.login_button_shape));
        }
    }


    @OnClick({R.id.activateBtn,R.id.edit_password_activity_close_icn})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.activateBtn:
                String mobileNumber = mobileNumberET.getText().toString();
                String codeTV = codeTVET.getText().toString();
                if (TextUtils.isEmpty(mobileNumber)) {
                    mobileNumberET.setError(getString(R.string.this_field_is_required));
                    return;
                }
                if (TextUtils.isEmpty(codeTV)) {
                    codeTVET.setError(getString(R.string.this_field_is_required));
                    return;
                }
                mViewModel.invokeActivateApi(mobileNumber, codeTV);
                break;
            case R.id.edit_password_activity_close_icn:
                finish();
                break;



        }
    }
}