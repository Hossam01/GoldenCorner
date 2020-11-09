package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCardType {

    @SerializedName("data")
    private List<DataItemCardType> data;

    @SerializedName("status")
    private int status;

    public List<DataItemCardType> getData() {
        return data;
    }

    public void setData(List<DataItemCardType> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}