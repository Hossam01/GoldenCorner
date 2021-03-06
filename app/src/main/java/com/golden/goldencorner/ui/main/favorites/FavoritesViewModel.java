package com.golden.goldencorner.ui.main.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.FavDataList;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoritesViewModel extends ViewModel {

    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }


    private MutableLiveData<Resource<List<FavDataList>>> FavLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<FavDataList>>> getFavLiveData() {
        return FavLiveData;
    }
    public void invokeFavoritesApi(String token, long page) {
        FavLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getProductsFavs(page, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(favResponse -> {
                    metaLiveData.setValue(favResponse.getData().get(0).getMeta());
                    FavLiveData.setValue(Resource.success(favResponse.getData().get(0).getItems()));
                }, throwable -> {
                    FavLiveData.setValue(Resource.error(throwable.getMessage(), null));
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