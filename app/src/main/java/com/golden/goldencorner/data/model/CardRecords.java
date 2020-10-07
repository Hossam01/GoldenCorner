package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardRecords {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("expired")
    @Expose
    private String expired;
    @SerializedName("type")
    @Expose
    private Long type;
    @SerializedName("user_id")
    @Expose
    private Long userId;
    @SerializedName("image")
    @Expose
    private String image;

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getCardNumber() {
        return cardNumber;
    }

    private void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private String getExpired() {
        return expired;
    }

    private void setExpired(String expired) {
        this.expired = expired;
    }

    private Long getType() {
        return type;
    }

    private void setType(Long type) {
        this.type = type;
    }

    private Long getUserId() {
        return userId;
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private String getImage() {
        return image;
    }

    private void setImage(String image) {
        this.image = image;
    }
}
