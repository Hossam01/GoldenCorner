package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ProductDetailData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProductDetailData getData() {
        return data;
    }

    public void setData(ProductDetailData data) {
        this.data = data;
    }
}
