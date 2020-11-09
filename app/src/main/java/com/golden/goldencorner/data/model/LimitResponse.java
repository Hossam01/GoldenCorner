package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class LimitResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private LimitResponseModel data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LimitResponseModel getData() {
        return data;
    }

    public void setData(LimitResponseModel data) {
        this.data = data;
    }
}
