package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("dish_id")
    @Expose
    private Long dish_id;
    @SerializedName("dish_name")
    @Expose
    private String dish_name;
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

    public Long getDish_id() {
        return dish_id;
    }

    public void setDish_id(Long dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
