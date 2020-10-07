package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class FavDataList {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("product")
    @Expose
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(@NotNull Product product) {
        this.product = product;
    }
}
