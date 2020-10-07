package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListData {
    @SerializedName("city")
    @Expose
    private List<City> city = null;
    @SerializedName("region")
    @Expose
    private List<Region> region = null;
    @SerializedName("setting")
    @Expose
    private List<Setting> setting = null;
    @SerializedName("sale_status_ar")
    @Expose
    private SaleStatusAr saleStatusAr;
    @SerializedName("sale_status_en")
    @Expose
    private SaleStatusEn saleStatusEn;
    @SerializedName("paymentmethod_ar")
    @Expose
    private PaymentmethodAr paymentmethodAr;
    @SerializedName("paymentmethod_en")
    @Expose
    private PaymentmethodEn paymentmethodEn;

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

    public List<Setting> getSetting() {
        return setting;
    }

    public void setSetting(List<Setting> setting) {
        this.setting = setting;
    }

    public SaleStatusAr getSaleStatusAr() {
        return saleStatusAr;
    }

    public void setSaleStatusAr(SaleStatusAr saleStatusAr) {
        this.saleStatusAr = saleStatusAr;
    }

    public SaleStatusEn getSaleStatusEn() {
        return saleStatusEn;
    }

    public void setSaleStatusEn(SaleStatusEn saleStatusEn) {
        this.saleStatusEn = saleStatusEn;
    }

    public PaymentmethodAr getPaymentmethodAr() {
        return paymentmethodAr;
    }

    public void setPaymentmethodAr(PaymentmethodAr paymentmethodAr) {
        this.paymentmethodAr = paymentmethodAr;
    }

    public PaymentmethodEn getPaymentmethodEn() {
        return paymentmethodEn;
    }

    public void setPaymentmethodEn(PaymentmethodEn paymentmethodEn) {
        this.paymentmethodEn = paymentmethodEn;
    }
}
