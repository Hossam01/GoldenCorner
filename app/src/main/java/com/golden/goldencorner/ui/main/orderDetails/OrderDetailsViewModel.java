package com.golden.goldencorner.ui.main.orderDetails;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.OrderRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsViewModel extends ViewModel {
    private MutableLiveData<Resource<OrderRecords>> orderLiveData = new MutableLiveData<>();

    public MutableLiveData<Resource<OrderRecords>> getOrderLiveData() {
        return orderLiveData;
    }

    public void invokeOrderApi(long id, String token) {
        orderLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getOrderById(id, token)
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
