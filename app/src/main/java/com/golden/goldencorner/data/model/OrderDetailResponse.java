package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public class OrderDetailResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    @NotNull
    private OrderRecords data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NotNull
    public OrderRecords getData() {
        return data;
    }

    public void setData(@NotNull OrderRecords data) {
        this.data = data;
    }
}
