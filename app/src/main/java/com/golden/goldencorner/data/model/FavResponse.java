package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class FavResponse {
    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<FavData> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<FavData> getData() {
        return data;
    }

    public void setData(List<FavData> data) {
        this.data = data;
    }
}
