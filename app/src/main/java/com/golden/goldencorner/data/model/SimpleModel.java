package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class SimpleModel {

    @SerializedName("field")
    private int field;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
