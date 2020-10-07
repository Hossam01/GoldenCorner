package com.golden.goldencorner.ui.main.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Category;
import com.golden.goldencorner.data.model.HomeSliderRecords;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {


    public HomeViewModel() {
        initializeMeta();
    }


    private MutableLiveData<Resource<List<HomeSliderRecords>>> homeSliderLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<HomeSliderRecords>>> getHomeSlider() {
        return homeSliderLiveData;
    }

    public void invokeHomeSliderApi() {
        categoriesLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getHomeSlider()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(homeSliderResponse -> {
                    if (homeSliderResponse.getData() != null) {
                        homeSliderLiveData.setValue(Resource.success(homeSliderResponse.getData()));
                    } else {
                        homeSliderLiveData.setValue(Resource.error(null, null));
                    }
                }, throwable -> {
                    homeSliderLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }
    public void setMetaLiveData(Meta metaLiveData) {
        this.metaLiveData.setValue(metaLiveData);
    }
    private void initializeMeta() {
        Meta meta = new Meta();
        meta.setCurrentPage(0);
        meta.setNumberOfPage(0);
        meta.setPageCount(0);
        meta.setTotalCount(0);
        setMetaLiveData(meta);
    }

    private MutableLiveData<Resource<List<Category>>> categoriesLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<Category>>> getCategoriesLiveData() {
        return categoriesLiveData;
    }
    public void invokeCategoriesApi(Context mContext, long page) {
        if (getMetaLiveData() != null && getMetaLiveData().getValue().getTotalCount() > 0 &&
                getMetaLiveData().getValue().getCurrentPage() == getMetaLiveData().getValue().getTotalCount()){
            categoriesLiveData.setValue(Resource.error(mContext.getString(R.string.no_more_data_to_load), null));
            return;
        }
        categoriesLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getCategories(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(categoryResponse -> {
                    categoriesLiveData.setValue(Resource.success(categoryResponse.getData().get(0).getItems()));
                    metaLiveData.setValue(categoryResponse.getData().get(0).getMeta());
                }, throwable -> {
                    categoriesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }


}