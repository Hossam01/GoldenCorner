package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivateUserRecords {

    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("login_link")
    @Expose
    private String loginLink;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(String loginLink) {
        this.loginLink = loginLink;
    }

}
