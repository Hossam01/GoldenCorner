package com.golden.goldencorner.ui.main.addresses;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    MutableLiveData<String> metaLiveData = new MutableLiveData<>();

    public void saveData(String data)
    {
        metaLiveData.setValue(data);
    }
}
