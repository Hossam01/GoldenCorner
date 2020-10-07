package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<CategoryResponseData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CategoryResponseData> getData() {
        return data;
    }

    public void setData(List<CategoryResponseData> data) {
        this.data = data;
    }
}
