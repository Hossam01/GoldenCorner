package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BranchesResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<BranchRecords> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BranchRecords> getData() {
        return data;
    }

    public void setData(List<BranchRecords> data) {
        this.data = data;
    }
}
