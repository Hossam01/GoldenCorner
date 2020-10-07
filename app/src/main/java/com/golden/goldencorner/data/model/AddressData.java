package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressData {

    @SerializedName("items")
    @Expose
    private List<AddressRecords> items = null;
    @SerializedName("_meta")
    @Expose
    private Meta meta;

    public List<AddressRecords> getItems() {
        return items;
    }

    public void setItems(List<AddressRecords> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
