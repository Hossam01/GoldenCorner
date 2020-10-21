package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class SimpleResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<SimpleModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotNull
    public List<SimpleModel> getData() {
        return data;
    }

    public void setData(@NotNull List<SimpleModel> data) {
        this.data = data;
    }
}
