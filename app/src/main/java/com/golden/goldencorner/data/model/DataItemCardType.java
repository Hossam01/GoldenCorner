package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItemCardType {

    @SerializedName("CardType")
    private List<CardTypeItem> cardType;

    public List<CardTypeItem> getCardType() {
        return cardType;
    }

    public void setCardType(List<CardTypeItem> cardType) {
        this.cardType = cardType;
    }
}