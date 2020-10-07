package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class LimitResponseModel {
    @SerializedName("status")
    private int status;
    @SerializedName("order_limit")
    private double order_limit;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getOrder_limit() {
        return order_limit;
    }

    public void setOrder_limit(double order_limit) {
        this.order_limit = order_limit;
    }
}
