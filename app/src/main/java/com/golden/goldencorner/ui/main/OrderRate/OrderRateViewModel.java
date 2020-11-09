package com.golden.goldencorner.ui.main.OrderRate;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Qustions.DataItem;
import com.golden.goldencorner.data.Qustions.QustionsModel;
import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.model.SimpleResponse;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderRateViewModel extends ViewModel {

    private MutableLiveData<Resource<List<DataItem>>> rateLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<SimpleModel>> rateSendLiveData = new MutableLiveData<>();

    public MutableLiveData<Resource<List<DataItem>>> getRateLiveData() {
        return rateLiveData;
    }

    public void invokeRateApi(String token) {
        rateLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().rateQuestion(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((QustionsModel response) -> {
                    rateLiveData.setValue(Resource.success(response.getData()));
                }, throwable -> {
                    rateLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    public MutableLiveData<Resource<SimpleModel>> getRateSendLiveData() {
        return rateSendLiveData;
    }

    public void invokeSendRateApi(String token, String order_id, String driver_id, String question_id, String rate, String type) {
        rateLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().rateAnswer(token, order_id, driver_id, question_id, rate, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((SimpleResponse response) -> {
                    rateSendLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    rateSendLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

}
