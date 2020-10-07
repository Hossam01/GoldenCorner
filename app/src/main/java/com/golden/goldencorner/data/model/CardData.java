package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardData {

    @SerializedName("items")
    @Expose
    private List<CardRecords> items = null;
    @SerializedName("_meta")
    @Expose
    private Meta meta;

    public List<CardRecords> getItems() {
        return items;
    }

    public void setItems(List<CardRecords> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
