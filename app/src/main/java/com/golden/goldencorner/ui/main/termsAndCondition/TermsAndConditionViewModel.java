package com.golden.goldencorner.ui.main.termsAndCondition;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.TermsAndConditionsRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TermsAndConditionViewModel extends ViewModel {


    private MutableLiveData<Resource<TermsAndConditionsRecords>> termsLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<TermsAndConditionsRecords>> getTermsLiveData() {
        return termsLiveData;
    }

    public void invokeTermsApi() {
        termsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getTermsAndConditions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(termsAndConditionsRecords -> {
                    termsLiveData.setValue(Resource.success(termsAndConditionsRecords));
                }, throwable -> {
                    termsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}