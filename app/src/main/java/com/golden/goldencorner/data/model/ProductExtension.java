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
    @SerializedName("name")
    @Expose
    private String Name;
    @SerializedName("select")
    @Expose
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @SerializedName("extension_name")
    @Expose
    private String extensionName;
    @SerializedName("price")
    @Expose
    private String price;

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
        if (extensionName == null || extensionName.equals("")) {
            return "rice";
        } else {
            return extensionName;
        }
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getPrice() {
        return price.replace(",","");
    }

    public void setPrice(String price) {
        this.price = price;
    }

}