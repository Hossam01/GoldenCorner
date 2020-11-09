package com.golden.goldencorner.ui.login;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.User;
import com.golden.goldencorner.data.remote.RetrofitProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.golden.goldencorner.data.Utils.AppConstant.ACCESS_TOKEN;
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

public class LoginViewModel extends ViewModel {


    // A placeholder username validation check
    public boolean isUserNameValid(String username) {
        return TextUtils.isEmpty(username);
    }
    // A placeholder password validation check
    public boolean isPasswordValid(String password) {
        //return password != null && password.trim().length() >= 5;
        return TextUtils.isEmpty(password);
    }

    private MutableLiveData<Resource<User>> mLoginLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<User>> getLoginLiveData() {
        return mLoginLiveData;
    }
    public void invokeLogin(String userName, String password) {
        mLoginLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getLogin(userName,password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(loginResponse -> {
                    String accessToken = loginResponse.getData().get(0).getAccessToken();
                    SharedPreferencesManager.put(ACCESS_TOKEN, accessToken);
                    User data = loginResponse.getData().get(0).getData();
                    saveDataToShared(data);
                    mLoginLiveData.setValue(Resource.success(data));
                }, throwable -> {
                    mLoginLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    public void saveDataToShared(User user) {
        SharedPreferencesManager.put(UserId, user.getUserId());
        SharedPreferencesManager.put(UserName, user.getUsername());
        SharedPreferencesManager.put(Name, user.getName());
        SharedPreferencesManager.put(UserActivation, user.getActivate());
        SharedPreferencesManager.put(UserEmail, user.getEmail());
        SharedPreferencesManager.put(UserCity, user.getCity());
        SharedPreferencesManager.put(UserAvatar, user.getAvatar());
        SharedPreferencesManager.put(UserDescription, user.getDescription());
        SharedPreferencesManager.put(UserDeviceToken, user.getDeviceToken());
        SharedPreferencesManager.put(UserMobile, user.getMobile());
        //   SharedPreferencesManager.put(UserFamilyName, user.get());
        //   SharedPreferencesManager.put(UserAddress, user.get());
        SharedPreferencesManager.put(UserPoint, user.getPoint());
        SharedPreferencesManager.put(UserType, user.getUserType());
    }
    public void removeDataFromShared() {
        SharedPreferencesManager.remove(UserId);
        SharedPreferencesManager.remove(UserName);
        SharedPreferencesManager.remove(UserActivation);
        SharedPreferencesManager.remove(UserEmail);
        SharedPreferencesManager.remove(UserCity);
        SharedPreferencesManager.remove(UserAvatar);
        SharedPreferencesManager.remove(UserDescription);
        SharedPreferencesManager.remove(UserDeviceToken);
        SharedPreferencesManager.remove(UserMobile);
//        SharedPreferencesManager.remove(UserFamilyName);
//        SharedPreferencesManager.remove(UserAddress);
        SharedPreferencesManager.remove(UserPoint);
        SharedPreferencesManager.remove(UserType);
    }
}
