package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting {
//    @PrimaryKey

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("order_limit")
    @Expose
    private Long orderLimit;
    @SerializedName("shipping_price")
    @Expose
    private Long shippingPrice;
    @SerializedName("tax")
    @Expose
    private Long tax;
    @SerializedName("payment_tasleem")
    @Expose
    private Long paymentTasleem;
    @SerializedName("discount")
    @Expose
    private Long discount;
    @SerializedName("hide_elec_payment")
    @Expose
    private Long hideElecPayment;
    @SerializedName("hide_tasleem_payment")
    @Expose
    private Long hideTasleemPayment;
    @SerializedName("hide_bank_payment")
    @Expose
    private Long hideBankPayment;
    @SerializedName("hide_delivery")
    @Expose
    private Long hideDelivery;
    @SerializedName("hide_branch_tasleem")
    @Expose
    private Long hideBranchTasleem;
    @SerializedName("hide_cupon")
    @Expose
    private Long hideCupon;
    @SerializedName("hide_visa")
    @Expose
    private Long hideVisa;
    @SerializedName("hide_mada")
    @Expose
    private Long hideMada;
    @SerializedName("hide_stc")
    @Expose
    private Long hideStc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Long orderLimit) {
        this.orderLimit = orderLimit;
    }

    public Long getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Long shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public Long getPaymentTasleem() {
        return paymentTasleem;
    }

    public void setPaymentTasleem(Long paymentTasleem) {
        this.paymentTasleem = paymentTasleem;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getHideElecPayment() {
        return hideElecPayment;
    }

    public void setHideElecPayment(Long hideElecPayment) {
        this.hideElecPayment = hideElecPayment;
    }

    public Long getHideTasleemPayment() {
        return hideTasleemPayment;
    }

    public void setHideTasleemPayment(Long hideTasleemPayment) {
        this.hideTasleemPayment = hideTasleemPayment;
    }

    public Long getHideBankPayment() {
        return hideBankPayment;
    }

    public void setHideBankPayment(Long hideBankPayment) {
        this.hideBankPayment = hideBankPayment;
    }

    public Long getHideDelivery() {
        return hideDelivery;
    }

    public void setHideDelivery(Long hideDelivery) {
        this.hideDelivery = hideDelivery;
    }

    public Long getHideBranchTasleem() {
        return hideBranchTasleem;
    }

    public void setHideBranchTasleem(Long hideBranchTasleem) {
        this.hideBranchTasleem = hideBranchTasleem;
    }

    public Long getHideCupon() {
        return hideCupon;
    }

    public void setHideCupon(Long hideCupon) {
        this.hideCupon = hideCupon;
    }

    public Long getHideVisa() {
        return hideVisa;
    }

    public void setHideVisa(Long hideVisa) {
        this.hideVisa = hideVisa;
    }

    public Long getHideMada() {
        return hideMada;
    }

    public void setHideMada(Long hideMada) {
        this.hideMada = hideMada;
    }

    public Long getHideStc() {
        return hideStc;
    }

    public void setHideStc(Long hideStc) {
        this.hideStc = hideStc;
    }
}
