package com.golden.goldencorner.ui.main.termsAndCondition;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.ResponseTerms;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TermsAndConditionViewModel extends ViewModel {


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
                    termsLiveData.setValue(Resource.error(throwable.getMessage(),null));
                    Log.d("terms",throwable.getMessage());
                });
    }
}