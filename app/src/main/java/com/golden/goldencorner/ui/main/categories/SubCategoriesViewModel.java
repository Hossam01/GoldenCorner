package com.golden.goldencorner.ui.main.categories;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.Category;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.Product;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubCategoriesViewModel extends ViewModel {

//    private MutableLiveData<Resource<List<Category>>> categoriesLiveData = new MutableLiveData<>();
//    public MutableLiveData<Resource<List<Category>>> getCategoriesLiveData() {
//        return categoriesLiveData;
//    }
//    public void invokeCategoriesApi(Context mContext) {
//        categoriesLiveData.setValue(Resource.loading());
//        RetrofitProvider.getClient().getCategories()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(categoryResponse -> {
//                    if (categoryResponse.getData() != null) {
//                        categoriesLiveData.setValue(Resource.success(categoryResponse.getData().get(0).getItems()));
//                    } else {
//                        categoriesLiveData.setValue(Resource.error(mContext.getString(R.string.no_offers_for_this_week), null));
//                    }
//                }, throwable -> {
//                    categoriesLiveData.setValue(Resource.error(throwable.getMessage(), null));
//                });
//    }



    private MutableLiveData<Resource<List<Category>>> subCategoryByIdLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<Category>>> getSubCategoryByIdLiveData() {
        return subCategoryByIdLiveData;
    }
    public void invokeSubCategoryByIdApi(long category_id, int page) {
        subCategoryByIdLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getSubCategories(category_id, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(categoryResponse -> {
                    List<Category> items = categoryResponse.getData().get(0).getItems();
                    subCategoryByIdLiveData.setValue(Resource.success(items));
                }, throwable -> {
                    subCategoryByIdLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    private MutableLiveData<Meta> productMetaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getProductMetaLiveData() {
        return productMetaLiveData;
    }
    private MutableLiveData<Resource<List<Product>>> productsBySubCategoryIdLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<Product>>> getProductsBySubCategory() {
        return productsBySubCategoryIdLiveData;
    }
    @SuppressLint("CheckResult")
    public void invokeProductsBySubCategoryIdApi(long category_id, int page) {
        productsBySubCategoryIdLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getProductsBySubCategories(category_id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( subCategoryResponse-> {
                    productMetaLiveData.setValue(subCategoryResponse.getData().get(0).getMeta());
                    ArrayList<Product> items = subCategoryResponse.getData().get(0).getItems();
                    productsBySubCategoryIdLiveData.setValue(Resource.success(items));
                }, throwable -> {
                    productsBySubCategoryIdLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("Products",throwable.getMessage());
                });
    }
}