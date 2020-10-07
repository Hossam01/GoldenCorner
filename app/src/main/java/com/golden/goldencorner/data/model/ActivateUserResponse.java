package com.golden.goldencorner.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivateUserResponse {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("data")
    @Expose
    private List<ActivateUserRecords> data = null;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<ActivateUserRecords> getData() {
        return data;
    }

    public void setData(List<ActivateUserRecords> data) {
        this.data = data;
    }

}