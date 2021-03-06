package com.golden.goldencorner.ui.main.creditCards;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.golden.goldencorner.data.Resource;
import com.golden.goldencorner.data.model.CardRecords;
import com.golden.goldencorner.data.model.CardTypeItem;
import com.golden.goldencorner.data.model.Meta;
import com.golden.goldencorner.data.model.SimpleModel;
import com.golden.goldencorner.data.remote.RetrofitProvider;
import com.golden.goldencorner.ui.main.addresses.AddressesViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardsViewModel extends ViewModel {

    public static final String TAG = AddressesViewModel.class.getSimpleName();
    private MutableLiveData<Meta> metaLiveData = new MutableLiveData<>();
    public MutableLiveData<Meta> getMetaLiveData() {
        return metaLiveData;
    }


    private MutableLiveData<Resource<List<CardRecords>>> cardLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<CardRecords>>> getCardsLiveData() {
        return cardLiveData;
    }
    public void invokeCardsApi(String token) {
        cardLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getCreditCard(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(cardResponse -> {
                    metaLiveData.setValue(cardResponse.getData().get(0).getMeta());
                    cardLiveData.setValue(Resource.success(cardResponse.getData().get(0).getItems()));
                }, throwable -> {
                    cardLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    private MutableLiveData<Resource<SimpleModel>> cardActionsLiveData = new MutableLiveData<>();

    public MutableLiveData<Resource<SimpleModel>> getCardActionsLiveData() {
        return cardActionsLiveData;
    }

    private MutableLiveData<Resource<List<CardTypeItem>>> cardLiveDataType = new MutableLiveData<>();

    public void invokeUpdateAddressApi(String access_token,
                                       long id,
                                       String name,
                                       long card_number,
                                       String expired) {

        cardActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().updateCreditCardAddress(access_token,
                id, name, card_number, expired)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    cardActionsLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    cardActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    public void invokeDeleteAddressApi(String token, long id) {
        cardActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().deleteCreditCard(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    cardActionsLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    cardActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                });
    }

    public void invokeAddCardApi(String token,
                                 String name,
                                 String card_number,
                                 String expired, String type) {

        cardActionsLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().addCreditCard(token, name, card_number, expired, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    cardActionsLiveData.setValue(Resource.success(response.getData().get(0)));
                }, throwable -> {
                    cardActionsLiveData.setValue(Resource.error(throwable.getMessage(), null));
                    Log.e("error", throwable.getMessage());
                });
    }

    public MutableLiveData<Resource<List<CardTypeItem>>> getCardsLiveDataType() {
        return cardLiveDataType;
    }

    public void invokeCardsApiType() {
        cardLiveData.setValue(Resource.loading());
        RetrofitProvider.getClient().getCreditCardType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(cardResponse -> {
                    cardLiveDataType.setValue(Resource.success(cardResponse.getData().get(0).getCardType()));
                }, throwable -> {
                    cardLiveDataType.setValue(Resource.error(throwable.getMessage(), null));
                });
    }


}