package com.golden.goldencorner.ui.main.orderEvaluate;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderEvaluationViewModel extends ViewModel {

    private MutableLiveData<Resource<SimpleModel>> rateLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getRateLiveData() {
        return rateLiveData;
    }
    public void invokeRateApi(String token, long productId, long rate) {
        rateLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().addRate(token, productId, rate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    rateLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    rateLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }


    private MutableLiveData<Resource<SimpleModel>> commentLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getCommentLiveData() {
        return commentLiveData;
    }
    public void invokeCommentApi(long productId, String token, String comment) {
        commentLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().addComment(productId, comment, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    commentLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    commentLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.d("comment",throwable.getMessage());
                });
    }
}