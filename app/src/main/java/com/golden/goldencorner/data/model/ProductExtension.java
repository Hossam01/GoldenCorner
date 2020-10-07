package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductExtension {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("color_id")
    @Expose
    private Long colorId;
    @SerializedName("extension_name")
    @Expose
    private String extensionName;
    @SerializedName("price")
    @Expose
    private Long price;

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

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

}