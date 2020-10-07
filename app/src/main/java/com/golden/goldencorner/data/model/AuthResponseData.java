package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuthResponseData {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    @NotNull
    private String message;
    @SerializedName("field")
    @Nullable
    private String field;
    @SerializedName("access_token")
    @Nullable
    private String accessToken;
    @SerializedName("data")
    @Nullable
    private User data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    @Nullable
    public String getField() {
        return field;
    }

    public void setField(@Nullable String field) {
        this.field = field;
    }

    @Nullable
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@Nullable String accessToken) {
        this.accessToken = accessToken;
    }

    @Nullable
    public User getData() {
        return data;
    }

    public void setData(@Nullable User data) {
        this.data = data;
    }

}
