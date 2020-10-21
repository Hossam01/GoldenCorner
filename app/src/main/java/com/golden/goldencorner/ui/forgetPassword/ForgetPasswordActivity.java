package com.golden.goldencorner.ui.forgetPassword;

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
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.ui.changePassword.ChangePasswordActivity;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.edit_password_activity_close_icn)
    ImageView edit_password_activity_close_icn;
    @BindView(R.id.sendBtn)
    CircularProgressButton sendBtn;
    private ForgetPasswordViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this)
                .get(ForgetPasswordViewModel.class);
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
                                    if (Integer.valueOf(resource.getData().getStatus()) != 0) {
                                        navToChangePasswored();
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

    private void navToChangePasswored() {
        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
        finish();
    }

    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            sendBtn.startMorphAnimation();
            sendBtn.setBackground(this.getDrawable(R.drawable.rounded_red_bg));
        } else {
            sendBtn.stopAnimation();
            sendBtn.setBackground(this.getDrawable(R.drawable.login_button_shape));
        }
    }



    @OnClick({R.id.sendBtn,
            R.id.edit_password_activity_close_icn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendBtn:
                String password = passwordET.getText().toString();
                if (TextUtils.isEmpty(password)){
                    passwordET.setError(getString(R.string.this_field_is_required));
                    return;
                }
                mViewModel.invokeResetPasswordApi(password);
                break;
            case R.id.edit_password_activity_close_icn:
                finish();
                break;
        }
    }
}