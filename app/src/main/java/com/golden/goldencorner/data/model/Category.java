package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    @NotNull
    private String title;
    @SerializedName("text")
    @NotNull
    private String text;
    @SerializedName("image")
    @NotNull
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getText() {
        return text;
    }

    public void setText(@NotNull String text) {
        this.text = text;
    }

    @NotNull
    public String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        this.image = image;
    }
}
