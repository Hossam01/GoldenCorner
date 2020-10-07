package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class FavModel {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    @NotNull
    private User user;
    @SerializedName("product")
    @NotNull
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(@NotNull Product product) {
        this.product = product;
    }
}
