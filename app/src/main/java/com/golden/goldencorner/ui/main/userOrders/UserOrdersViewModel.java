package com.golden.goldencorner.ui.main.userOrders;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.OrderRecords;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserOrdersViewModel extends ViewModel {

    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }


    private MutableLiveData<Resource<List<OrderRecords>>> orderLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<OrderRecords>>> getOrderLiveData() {
        return orderLiveData;
    }
    public void invokeOrderApi(String token, long page) {
        orderLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getOrders(page, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    metaLiveData.setValue(response.getData().get(0).getMeta());
                    orderLiveData.setValue(Resource.success(response.getData().get(0).getItems()));
                }, throwable -> {
                    orderLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("myOrders",throwable.getMessage());
                });
    }

    private MutableLiveData<Resource<SimpleModel>> deleteLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getDeleteLiveData() {
        return deleteLiveData;
    }
    public void invokeDeleteApi(String token, long id) {
        deleteLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().cancelOrder(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(simpleResponse -> {
                    deleteLiveData.setValue(Resource.success(simpleResponse.getData().get(0)));
                }, throwable -> {
                    deleteLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("myOrders",throwable.getMessage());
                });
    }
}