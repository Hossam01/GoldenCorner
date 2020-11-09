package com.golden.goldencorner.ui.payment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.AddressRecords;
import com.golden.goldencorner.data.model.CheckoutModel;
import com.golden.goldencorner.data.model.CheckoutResponse;
import com.golden.goldencorner.data.model.City;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.golden.goldencorner.data.Utils.AppConstant.Name;
import static com.golden.goldencorner.data.Utils.AppConstant.UserActivation;
import static com.golden.goldencorner.data.Utils.AppConstant.UserAvatar;
import static com.golden.goldencorner.data.Utils.AppConstant.UserCity;
import static com.golden.goldencorner.data.Utils.AppConstant.UserDescription;
import static com.golden.goldencorner.data.Utils.AppConstant.UserDeviceToken;
import static com.golden.goldencorner.data.Utils.AppConstant.UserEmail;
import static com.golden.goldencorner.data.Utils.AppConstant.UserId;
import static com.golden.goldencorner.data.Utils.AppConstant.UserMobile;
import static com.golden.goldencorner.data.Utils.AppConstant.UserName;
import static com.golden.goldencorner.data.Utils.AppConstant.UserPoint;
import static com.golden.goldencorner.data.Utils.AppConstant.UserType;

public class PaymentViewModel extends ViewModel {
    private MutableLiveData<Resource<List<City>>> citiesLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<List<AddressRecords>>> addressesLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<List<CheckoutModel>>> checkoutLiveData = new MutableLiveData<>();

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

    public User getDataFromShared() {
        User user = new User();
        user.setUserId(SharedPreferencesManager.getLong(UserId));
        user.setUsername(SharedPreferencesManager.getString(UserName));
        user.setName(SharedPreferencesManager.getString(Name));
        user.setActivate(SharedPreferencesManager.getLong(UserActivation));
        user.setEmail(SharedPreferencesManager.getString(UserEmail));
        user.setCity(SharedPreferencesManager.getString(UserCity));
        user.setAvatar(SharedPreferencesManager.getString(UserAvatar));
        user.setDescription(SharedPreferencesManager.getString(UserDescription));
        user.setDeviceToken(SharedPreferencesManager.getString(UserDeviceToken));
        user.setMobile(SharedPreferencesManager.getString(UserMobile));
        user.setPoint(SharedPreferencesManager.getLong(UserPoint));
        user.setUserType(SharedPreferencesManager.getLong(UserType));
        return user;
    }

    public MutableLiveData<Resource<List<AddressRecords>>> getAddressesLiveData() {
        return addressesLiveData;
    }

    public void invokeAddressesApi(String token) {
        addressesLiveData.setValue(Resource.loading());

        RetrofitProvider.getClient().getAddress(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(addressResponse -> {
                    List<AddressRecords> items = addressResponse.getData().get(0).getItems();
                    addressesLiveData.setValue(Resource.success(items));
                }, throwable -> {
                    addressesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.i("error", throwable.getMessage());
                });
    }

    public MutableLiveData<Resource<List<CheckoutModel>>> getcheckoutLiveData() {
        return checkoutLiveData;
    }


    public void invokeCheckoutApi(String toke,
                                  String name,
                                  String country,
                                  String city,
                                  String region,
                                  String address,
                                  String mobile,
                                  String description,
                                  int status,
                                  String order_time,
                                  String branch_id,
                                  int delivery_status,
                                  double totalprice,
                                  int totalquantity,
                                  int number_of_items,
                                  String product_ids,
                                  String product_name,
                                  String product_price,
                                  String product_quantity,
                                  String product_extension,
                                  String product_size,
                                  String product_rice,
                                  String product_dish,
                                  String product_totalPrice,
                                  String lat,
                                  String lang,
                                  String copun) {
        addressesLiveData.setValue(Resource.loading());

        RetrofitProvider.getClient().checkout(toke, name, country, city, region, address, mobile, description, status, order_time, branch_id, delivery_status,
                totalprice, totalquantity, number_of_items, product_ids, product_name, product_price, product_quantity, product_extension, product_size, product_rice, product_dish, product_totalPrice, lat, lang, copun)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((CheckoutResponse addressResponse) -> {
                    List<CheckoutModel> items = addressResponse.getData();
                    checkoutLiveData.setValue(Resource.success(items));
                }, throwable -> {
                    addressesLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.i("error", throwable.getMessage());
                });
    }
}