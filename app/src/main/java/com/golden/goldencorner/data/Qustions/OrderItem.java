package com.golden.goldencorner.data.Qustions;

import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("title_en")
    private String titleEn;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private int type;

    @SerializedName("status")
    private int status;

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}