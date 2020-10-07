package com.golden.goldencorner.data.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<AdsRecords> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<AdsRecords> getData() {
        return data;
    }

    public void setData(List<AdsRecords> data) {
        this.data = data;
    }

}
