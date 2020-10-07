package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryResponseData {

    @SerializedName("items")
    @Expose
    private List<SubCategory> items = null;
    @SerializedName("_meta")
    @Expose
    private Meta meta;

    public List<SubCategory> getItems() {
        return items;
    }

    public void setItems(List<SubCategory> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
