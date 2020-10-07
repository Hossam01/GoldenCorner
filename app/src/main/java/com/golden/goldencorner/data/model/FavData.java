package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavData {

    @SerializedName("items")
    @Expose
    private List<FavDataList> items = null;
    @SerializedName("_meta")
    @Expose
    private Meta meta;

    public List<FavDataList> getItems() {
        return items;
    }

    public void setItems(List<FavDataList> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
