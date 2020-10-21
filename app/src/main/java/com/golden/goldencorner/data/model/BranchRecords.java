package com.golden.goldencorner.data.model;

import com.golden.goldencorner.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchRecords {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("distance")
    @Expose
    private String distance = null;
    @SerializedName("isOpen")
    @Expose
    private boolean isOpen = false;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lang")
    @Expose
    private Double lang;
    @SerializedName("complaint_phone")
    @Expose
    private String complaintPhone;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("delivery_phone")
    @Expose
    private String deliveryPhone;
    @SerializedName("open_time")
    @Expose
    private String openTime;
    @SerializedName("closed_time")
    @Expose
    private String closedTime;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isSelected")
    @Expose
    private boolean isSelected;

    int background= R.drawable.stroke_bg_red;

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public String getComplaintPhone() {
        return complaintPhone;
    }

    public void setComplaintPhone(String complaintPhone) {
        this.complaintPhone = complaintPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getOpenTime() {

        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(String closedTime) {
        this.closedTime = closedTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
