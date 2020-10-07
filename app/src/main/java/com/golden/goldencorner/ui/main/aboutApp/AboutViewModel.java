package com.golden.goldencorner.ui.main.aboutApp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.AboutRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AboutViewModel extends ViewModel {


    private MutableLiveData<Resource<AboutRecords>> aboutLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<AboutRecords>> getAboutLiveData() {
        return aboutLiveData;
    }

    public void invokeAboutApi() {
        aboutLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getAboutUs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aboutResponse -> {
                    aboutLiveData.setValue(Resource.success(aboutResponse.getData().get(0)));
                }, throwable -> {
                    aboutLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}