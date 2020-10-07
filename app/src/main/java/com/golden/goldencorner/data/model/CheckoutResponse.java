package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CheckoutResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<CheckoutModel> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CheckoutModel> getData() {
        return data;
    }

    public void setData(List<CheckoutModel> data) {
        this.data = data;
    }
}
