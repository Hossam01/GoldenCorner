package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class SubCategoryResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<SubCategoryResponseData> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<SubCategoryResponseData> getData() {
        return data;
    }

    public void setData(List<SubCategoryResponseData> data) {
        this.data = data;
    }
}
