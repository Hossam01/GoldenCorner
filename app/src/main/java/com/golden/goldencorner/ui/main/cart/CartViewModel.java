package com.golden.goldencorner.ui.main.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.ProductResponse;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends ViewModel {


    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }

    private MutableLiveData<Resource<ProductResponse>> productLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<ProductResponse>> getProductLiveData() {
        return productLiveData;
    }

    public void invokeProductApi(long branchId, int page) {
        productLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getNewProducts(branchId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(productResponse -> {
                    productLiveData.setValue(Resource.success(productResponse));
                }, throwable -> {
                    productLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}