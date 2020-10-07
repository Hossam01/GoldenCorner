package com.golden.goldencorner.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.BranchRecords;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

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