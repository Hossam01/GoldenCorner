package com.golden.goldencorner.data.Qustions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QustionsModel {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("status")
    private int status;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}