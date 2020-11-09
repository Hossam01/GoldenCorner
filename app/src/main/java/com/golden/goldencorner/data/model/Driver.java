package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Driver implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getId() {
        if (id == null || id.equals("")) {
            return "0";
        } else
            return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (name == null || name.equals("")) {
            return "hossam";
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        if (mobile == null || mobile.equals("")) {
            return "012";
        } else {

            return mobile;
        }
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
