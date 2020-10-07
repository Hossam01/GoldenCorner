package com.golden.goldencorner.ui.ads;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.AdsRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdsViewModel extends ViewModel {


    // A placeholder username validation check
    private boolean isEmailValid(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    private MutableLiveData<Resource<List<AdsRecords>>> adsLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<AdsRecords>>> getAdsLiveData() {
        return adsLiveData;
    }

    public void invokeAdsApi(Context mContext) {
        adsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getAds()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(adsResponse -> {
                    if (adsResponse.getData() != null) {
                        adsLiveData.setValue(Resource.success(adsResponse.getData()));
                    } else {
                        adsLiveData.setValue(Resource.error(mContext.getString(R.string.no_offers_for_this_week), null));
                    }
                }, throwable -> {
                    adsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}
