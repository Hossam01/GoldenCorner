package com.golden.goldencorner.ui.accountActivation;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.ActivateUserRecords;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountActivationViewModel extends ViewModel {


    private MutableLiveData<Resource<ActivateUserRecords>> signUpLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<ActivateUserRecords>> getActivateLiveData() {
        return signUpLiveData;
    }
    public void invokeActivateApi(String mobile, String code) {

        signUpLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getActivateUser(mobile, code)
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
}
