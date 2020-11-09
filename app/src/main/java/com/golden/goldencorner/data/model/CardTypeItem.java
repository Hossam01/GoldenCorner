package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class CardTypeItem {

    @SerializedName("image")
    private String image;

    @SerializedName("title_en")
    private String titleEn;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
}