package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressRecords implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_default")
    @Expose
    private Long isDefault;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("map_location")
    @Expose
    private String mapLocation;
    @SerializedName("late")
    @Expose
    private String late;
    @SerializedName("lang")
    @Expose
    private String lang;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Long isDefault) {
        this.isDefault = isDefault;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(String mapLocation) {
        this.mapLocation = mapLocation;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
