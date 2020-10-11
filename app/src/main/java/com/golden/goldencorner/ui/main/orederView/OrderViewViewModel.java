package com.golden.goldencorner.ui.main.orederView;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.data.model.ProductDetailData;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderViewViewModel extends ViewModel {


    private MutableLiveData<Resource<ProductDetailData>> orderLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<ProductDetailData>> getOrderLiveData() {
        return orderLiveData;
    }
    public void invokeOrderApi(long id) {
        orderLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getProductDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    orderLiveData.setValue(Resource.success(response.getData()));
                }, throwable -> {
                    orderLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("orderView",throwable.getMessage());
                });
    }

    private MutableLiveData<Resource<SimpleModel>> addToFavLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getAddToFavLiveData() {
        return addToFavLiveData;
    }
    public void invokeAddToFavoritesApi(String token, long id) {
        addToFavLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().addProductsToFavs(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(simpleResponse -> {
                    addToFavLiveData.setValue(Resource.success(simpleResponse.getData().get(0)));
                }, throwable -> {
                    addToFavLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("orderView",throwable.getMessage());
                });
    }
    private MutableLiveData<Resource<List<BranchRecords>>> banchesLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<BranchRecords>>> getBranchesLiveData() {
        return banchesLiveData;
    }

    public void invokeBranchesApi() {

        banchesLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getBranches()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(branchesResponse -> {
                    banchesLiveData.setValue(Resource.success(branchesResponse.getData()));
                }, throwable -> {
                    banchesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}