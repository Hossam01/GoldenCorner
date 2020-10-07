package com.golden.goldencorner.ui.changePassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.login.LogInActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.golden.goldencorner.data.Utils.AppConstant.ACCESS_TOKEN;

public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.codeNumberET)
    EditText codeNumberET;
    @BindView(R.id.newMovbileET)
    EditText new_passwored_ET;
    @BindView(R.id.confirm_pass_edit_text)
    EditText confirm_pass_edit_text;
    @BindView(R.id.saveBtn)
    CircularProgressButton loginBtn;
    private ChangePasswordViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this)
                .get(ChangePasswordViewModel.class);
        subscribeObserver();

    }

    private void subscribeObserver() {
        mViewModel.getLiveData().observe(this,
                new Observer<Resource<SimpleModel>>() {
                    @Override
                    public void onChanged(Resource<SimpleModel> resource) {
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
            loginBtn.startMorphAnimation();
            loginBtn.setBackground(this.getDrawable(R.drawable.rounded_red_bg));
        } else {
            loginBtn.stopAnimation();
            loginBtn.setBackground(this.getDrawable(R.drawable.login_button_shape));
        }
    }
    @OnClick(R.id.saveBtn)
    public void onViewClicked() {
        String codeNumber = codeNumberET.getText().toString();
        String new_password = new_passwored_ET.getText().toString();
        String confirm_pass_edit = confirm_pass_edit_text.getText().toString();
        if (TextUtils.isEmpty(codeNumber)){
            codeNumberET.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(new_password)){
            new_passwored_ET.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (TextUtils.isEmpty(confirm_pass_edit)){
            confirm_pass_edit_text.setError(getString(R.string.this_field_is_required));
            return;
        }
        if (!new_password.equalsIgnoreCase(confirm_pass_edit)){
            showToast(getString(R.string.password_not_match));
            return;
        }
        String accessToken = SharedPreferencesManager.getString(ACCESS_TOKEN);
        mViewModel.invokeResetPasswordApi(accessToken, new_password);
//        if (TextUtils.isEmpty(accessToken)){
//            showToast(getString(R.string.password_not_match));
//        } else {
//            mViewModel.invokeResetPasswordApi(accessToken, new_password);
//        }
    }

}