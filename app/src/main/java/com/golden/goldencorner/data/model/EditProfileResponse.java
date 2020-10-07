package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EditProfileResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<EditProfileResponseData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<EditProfileResponseData> getData() {
        return data;
    }

    public void setData(List<EditProfileResponseData> data) {
        this.data = data;
    }
}
