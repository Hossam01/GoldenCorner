package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductDetailData {

    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
