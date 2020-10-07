package com.golden.goldencorner.ui.main.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.ProductResponse;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }

    private MutableLiveData<Resource<ProductResponse>> productLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<ProductResponse>> getProductLiveData() {
        return productLiveData;
    }

    public void invokeProductApi(String title, long branchId, long page) {
        productLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getProductsBySearch(title, branchId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(productResponse -> {
                    productLiveData.setValue(Resource.success(productResponse));
                }, throwable -> {
                    productLiveData.setValue(Resource.error(throwable.getMessage(), null));
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
                });
    }
    public void invokeDeleteFavoritesApi(String token, long id) {
        addToFavLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().deleteProductFromFav(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(simpleResponse -> {
                        addToFavLiveData.setValue(Resource.success(simpleResponse.getData().get(0)));
                }, throwable -> {
                        addToFavLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}