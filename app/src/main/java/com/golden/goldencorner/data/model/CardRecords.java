package com.golden.goldencorner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardRecords implements Parcelable {

    public static final Creator<CardRecords> CREATOR = new Creator<CardRecords>() {
        @Override
        public CardRecords createFromParcel(Parcel in) {
            return new CardRecords(in);
        }

        @Override
        public CardRecords[] newArray(int size) {
            return new CardRecords[size];
        }
    };
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("card_number")
    @Expose
    public String cardNumber;
    @SerializedName("expired")
    @Expose
    public String expired;
    @SerializedName("type")
    @Expose
    public Long type;
    @SerializedName("user_id")
    @Expose
    public Long userId;
    @SerializedName("image")
    @Expose
    public String image;

    protected CardRecords(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        cardNumber = in.readString();
        expired = in.readString();
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        image = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(name);
        parcel.writeString(cardNumber);
        parcel.writeString(expired);
        if (type == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(type);
        }
        if (userId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(userId);
        }
        parcel.writeString(image);
    }
}
