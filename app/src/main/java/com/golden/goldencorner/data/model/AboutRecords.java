package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutRecords {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("text_en")
    @Expose
    private String textEn;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("image")
    @Expose
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
