package com.golden.goldencorner.ui.main.DiscounteCode;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import com.golden.goldencorner.ui.main.DiscounteCode.Model.DataItem;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiscounteCodeViewModel extends ViewModel {
    private MutableLiveData<Resource<List<DataItem>>> orderLiveData = new MutableLiveData<>();

    public MutableLiveData<Resource<List<DataItem>>> getOrderLiveData() {
        return orderLiveData;
    }

    public void invokeOrderApi(String token, String id) {
        Log.e("token and id", token + id);
        orderLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().checkCode(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    orderLiveData.setValue(Resource.success(response.getData()));
                }, throwable -> {
                    orderLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("orderView", throwable.getMessage());
                });
    }
}
