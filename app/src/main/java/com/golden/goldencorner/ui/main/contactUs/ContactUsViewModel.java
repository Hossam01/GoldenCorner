package com.golden.goldencorner.ui.main.contactUs;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.HomeSliderRecords;
import com.golden.goldencorner.data.model.SimpleResponse;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactUsViewModel extends ViewModel {

    private MutableLiveData<Resource<SimpleResponse>> contactLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleResponse>> getContactSlider() {
        return contactLiveData;
    }

    public void invokeContactApi(String subject, String email, String body) {
        contactLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().contact(subject, email, body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(simpleResponse -> {
                    contactLiveData.setValue(Resource.success(simpleResponse));
                }, throwable -> {
                    contactLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}