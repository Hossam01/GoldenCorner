package com.golden.goldencorner.ui.main.addresses;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.AddressRecords;
import com.golden.goldencorner.data.model.City;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressesViewModel extends ViewModel {


    public static final String TAG = AddressesViewModel.class.getSimpleName();
    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }

    private final MutableLiveData<String> selectedPlayer = new MutableLiveData<>();

    public LiveData<String> getSelectedPlayer() {
        return selectedPlayer;
    }

    public void selectPlayer(String player) {
        selectedPlayer.setValue(player);
    }






    // get Saved Addresses
    private MutableLiveData<Resource<List<AddressRecords>>> addressesLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<AddressRecords>>> getAddressesLiveData() {
        return addressesLiveData;
    }
    private Handler handler = new Handler(Looper.getMainLooper());
    public void invokeAddressesApi(String token) {
        addressesLiveData.setValue(Resource.loading());
//        RetrofitProvider.getAddress(token, new AddressCallback() {
//            @Override
//            public void onFailure(Exception e) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        addressesLiveData.setValue(Resource.error(e.getMessage(), null));
//                    }
//                });
//            }
//            @Override
//            public void onResponse(AddressResponse addressResponse) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<AddressRecords> items = addressResponse.getData().get(0).getItems();
//                        addressesLiveData.setValue(Resource.success(items));
//                        metaLiveData.setValue(addressResponse.getData().get(0).getMeta());
//                    }
//                });
//            }
//        });
        RetrofitProvider.getClient().getAddress(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(addressResponse -> {
                    List<AddressRecords> items = addressResponse.getData().get(0).getItems();
                    addressesLiveData.setValue(Resource.success(items));
                    metaLiveData.setValue(addressResponse.getData().get(0).getMeta());
                }, throwable -> {
                    addressesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.i("error",throwable.getMessage());
                });
    }

    // add Address
    private MutableLiveData<Resource<SimpleModel>> addressActionsLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<SimpleModel>> getAddressActionsLiveData() {
        return addressActionsLiveData;
    }
    public void invokeAddAddressApi(String token,
                                    String name,
                                    String address,
                                    String state,
                                    long late,
                                    long lang,
                                    long is_default,
                                    String map_location) {
        addressActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().addAddress(token, name, address, state,
                late, lang, is_default, map_location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(addressResponse -> {
                    addressActionsLiveData.setValue(Resource.success(addressResponse.getData().get(0)));
                }, throwable -> {
                    addressActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    // add Address
//    private MutableLiveData<Resource<SimpleResponse>> updateAddressLiveData = new MutableLiveData<>();
//    public MutableLiveData<Resource<SimpleResponse>> getUpdateAddressLiveData() {
//        return updateAddressLiveData;
//    }
    public void invokeUpdateAddressApi(String access_token, long id, String name, String address,
                                       String city, String state, String late, String lang,
                                       long is_default, String map_location) {
        addressActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().updateAddress(access_token, id,
                name, address, city, state, late, lang, is_default, map_location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(addressResponse -> {
                    addressActionsLiveData.setValue(Resource.success(addressResponse.getData().get(0)));
                }, throwable -> {
                    addressActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    // delete Address
//    private MutableLiveData<Resource<SimpleResponse>> deleteAddressLiveData = new MutableLiveData<>();
//    public MutableLiveData<Resource<SimpleResponse>> getDeleteAddressLiveData() {
//        return deleteAddressLiveData;
//    }
    public void invokeDeleteAddressApi(String token, long id) {
        addressActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().deleteAddress(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(addressResponse -> {
                    addressActionsLiveData.setValue(Resource.success(addressResponse.getData().get(0)));
                }, throwable -> {
                    addressActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }



    private MutableLiveData<Resource<List<City>>> citiesLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<City>>> getCitiesLiveData() {
        return citiesLiveData;
    }
    public void invokeCitiesApi() {
        citiesLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listResponse -> {
                    citiesLiveData.setValue(Resource.success(listResponse.getData().get(0).getCity()));
                }, throwable -> {
                    citiesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }
}