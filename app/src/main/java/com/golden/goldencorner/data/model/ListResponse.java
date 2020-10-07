package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<ListData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListData> getData() {
        return data;
    }

    public void setData(List<ListData> data) {
        this.data = data;
    }
}
