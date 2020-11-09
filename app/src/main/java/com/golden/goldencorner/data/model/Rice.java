package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rice {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("rice_id")
    @Expose
    private Long rice_id;
    @SerializedName("rice_name")
    @Expose
    private String rice_name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("isSelected")
    @Expose
    private boolean isSelected;

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRice_id() {
        return rice_id;
    }

    public void setRice_id(Long rice_id) {
        this.rice_id = rice_id;
    }

    public String getRice_name() {
        return rice_name;
    }

    public void setRice_name(String rice_name) {
        this.rice_name = rice_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
