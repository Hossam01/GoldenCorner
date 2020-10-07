package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AuthResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    @NotNull
    private List<AuthResponseData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NotNull
    public List<AuthResponseData> getData() {
        return data;
    }

    public void setData(@NotNull List<AuthResponseData> data) {
        this.data = data;
    }
}
