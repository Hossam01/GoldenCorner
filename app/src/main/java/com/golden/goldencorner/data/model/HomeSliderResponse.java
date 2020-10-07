package com.golden.goldencorner.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeSliderResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<HomeSliderRecords> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<HomeSliderRecords> getData() {
        return data;
    }

    public void setData(List<HomeSliderRecords> data) {
        this.data = data;
    }

}
