package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LimitResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<LimitResponseModel> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LimitResponseModel> getData() {
        return data;
    }

    public void setData(List<LimitResponseModel> data) {
        this.data = data;
    }
}
