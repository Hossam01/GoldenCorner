package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("category_id")
    @Expose
    private long categoryId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("text_en")
    @Expose
    private String textEn;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("total_price")
    @Expose
    private double totalPrice;
    @SerializedName("calorie")
    @Expose
    private long calorie;
    @SerializedName("discount_price")
    @Expose
    private long discountPrice;
    @SerializedName("quantity")
    @Expose
    private float quantity;
    @SerializedName("offer")
    @Expose
    private long offer;
    @SerializedName("popular")
    @Expose
    private long popular;
    @SerializedName("new")
    @Expose
    private long _new;
    @SerializedName("view")
    @Expose
    private long view;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("images")
    @Expose
    private List<ProductImages> images = new ArrayList<>();
    @SerializedName("quantity_less_one")
    @Expose
    private long quantityLessOne;
    @SerializedName("extension")
    @Expose
    private List<ProductExtension> productExtension = null;
    @SerializedName("size")
    @Expose
    private List<ProductSize> productSize = null;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("is_favorite")
    @Expose
    private long isFavorite;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long get_new() {
        return _new;
    }

    public void set_new(long _new) {
        this._new = _new;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCalorie() {
        return calorie;
    }

    public void setCalorie(long calorie) {
        this.calorie = calorie;
    }

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public long getOffer() {
        return offer;
    }

    public void setOffer(long offer) {
        this.offer = offer;
    }

    public long getPopular() {
        return popular;
    }

    public void setPopular(long popular) {
        this.popular = popular;
    }

    public long getNew() {
        return _new;
    }

    public void setNew(long _new) {
        this._new = _new;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductImages> getImages() {
        return images;
    }

    public void setImages(List<ProductImages> images) {
        this.images = images;
    }

    public long getQuantityLessOne() {
        return quantityLessOne;
    }

    public void setQuantityLessOne(long quantityLessOne) {
        this.quantityLessOne = quantityLessOne;
    }

    public List<ProductExtension> getProductExtension() {
        return productExtension;
    }

    public void setProductExtension(List<ProductExtension> productExtension) {
        this.productExtension = productExtension;
    }

    public List<ProductSize> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<ProductSize> productSize) {
        this.productSize = productSize;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(long isFavorite) {
        this.isFavorite = isFavorite;
    }

}