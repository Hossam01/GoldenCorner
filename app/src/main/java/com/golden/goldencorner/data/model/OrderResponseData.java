package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class OrderResponseData {

    @SerializedName("items")
    @NotNull
    private List<OrderRecords> items;
    @SerializedName("_meta")
    @NotNull
    private Meta meta;

    @NotNull
    public List<OrderRecords> getItems() {
        return items;
    }

    public void setItems(@NotNull List<OrderRecords> items) {
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
