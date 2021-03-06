package com.golden.goldencorner.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.ResponseTerms;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SignUpViewModel extends ViewModel {


    // A placeholder username validation check
    public boolean isEmailValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    private MutableLiveData<Resource<SimpleModel>> signUpLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getSignUpLiveData() {
        return signUpLiveData;
    }
    public void invokeSignUp(String name,
                             String familyName,
                             String mobile,
                             String password,
                             String newPasswordConfirm,
                             String email) {

        signUpLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getSignUp(name, familyName, mobile, password, newPasswordConfirm, email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response.getData() != null) {
                        signUpLiveData.setValue(Resource.success(response.getData().get(0)));
                    }
                }, throwable -> {
                    signUpLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    private MutableLiveData<Resource<ResponseTerms>> termsLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<ResponseTerms>> getTermsLiveData() {
        return termsLiveData;
    }

    public void invokeTermsApi() {
        termsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getTermsAndConditions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((ResponseTerms termsAndConditionsRecords) -> {
                    termsLiveData.setValue(Resource.success(termsAndConditionsRecords));
                }, throwable -> {
                    termsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}
