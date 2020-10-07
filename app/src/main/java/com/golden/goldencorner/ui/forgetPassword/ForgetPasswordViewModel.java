package com.golden.goldencorner.ui.forgetPassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordViewModel extends ViewModel {


    private MutableLiveData<Resource<SimpleModel>> mLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getLiveData() {
        return mLiveData;
    }
    public void invokeResetPasswordApi(String mobile) {
        mLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().passwordReset(mobile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response.getData() != null) {
                        mLiveData.setValue(Resource.success(response.getData().get(0)));
                    }
                }, throwable -> {
                    mLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}
