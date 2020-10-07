package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSize {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("size_id")
    @Expose
    private Long sizeId;
    @SerializedName("size_name")
    @Expose
    private String sizeName;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("dis_price")
    @Expose
    private Long disPrice;
    @SerializedName("quantity")
    @Expose
    private float quantity;
    @SerializedName("calorie")
    @Expose
    private Long calorie;
    @SerializedName("is_default")
    @Expose
    private Long isDefault;
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

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDisPrice() {
        return disPrice;
    }

    public void setDisPrice(Long disPrice) {
        this.disPrice = disPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Long getCalorie() {
        return calorie;
    }

    public void setCalorie(Long calorie) {
        this.calorie = calorie;
    }

    public Long getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Long isDefault) {
        this.isDefault = isDefault;
    }
}
