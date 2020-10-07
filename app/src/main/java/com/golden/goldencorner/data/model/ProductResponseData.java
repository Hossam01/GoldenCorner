package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductResponseData {

    @SerializedName("items")
    @NotNull
    private ArrayList<Product> items;

    @SerializedName("_meta")
    @NotNull
    private Meta meta;

    @NotNull
    public ArrayList<Product> getItems() {
        return items;
    }

    public void setItems(@NotNull ArrayList<Product> items) {
        this.items = items;
    }

    @NotNull
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(@NotNull Meta meta) {
        this.meta = meta;
    }
}
