package com.golden.goldencorner.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LimitResponseModel implements Parcelable {

    public static final Creator<LimitResponseModel> CREATOR = new Creator<LimitResponseModel>() {
        @Override
        public LimitResponseModel createFromParcel(Parcel in) {
            return new LimitResponseModel(in);
        }

        @Override
        public LimitResponseModel[] newArray(int size) {
            return new LimitResponseModel[size];
        }
    };
    @SerializedName("order_limit")
    private double order_limit;
    @SerializedName("id")
    private int id;
    @SerializedName("shipping_price")
    private double shipping_price;
    @SerializedName("tax")
    private double tax;
    @SerializedName("payment_tasleem")
    private int payment_tasleem;
    @SerializedName("discount")
    private double discount;
    @SerializedName("hide_elec_payment")
    private int hide_elec_payment;
    @SerializedName("hide_tasleem_payment")
    private int hide_tasleem_payment;
    @SerializedName("hide_bank_payment")
    private int hide_bank_payment;
    @SerializedName("hide_delivery")
    private int hide_delivery;
    @SerializedName("hide_branch_tasleem")
    private int hide_branch_tasleem;
    @SerializedName("hide_cupon")
    private int hide_cupon;
    @SerializedName("hide_visa")
    private int hide_visa;
    @SerializedName("hide_mada")
    private int hide_mada;
    @SerializedName("hide_stc")
    private int hide_stc;

    public LimitResponseModel() {
    }

    public LimitResponseModel(int id, double order_limit, double shipping_price, int tax, int payment_tasleem, double discount, int hide_elec_payment, int hide_tasleem_payment, int hide_bank_payment, int hide_delivery, int hide_branch_tasleem, int hide_cupon, int hide_visa, int hide_mada, int hide_stc) {
        this.id = id;
        this.order_limit = order_limit;
        this.shipping_price = shipping_price;
        this.tax = tax;
        this.payment_tasleem = payment_tasleem;
        this.discount = discount;
        this.hide_elec_payment = hide_elec_payment;
        this.hide_tasleem_payment = hide_tasleem_payment;
        this.hide_bank_payment = hide_bank_payment;
        this.hide_delivery = hide_delivery;
        this.hide_branch_tasleem = hide_branch_tasleem;
        this.hide_cupon = hide_cupon;
        this.hide_visa = hide_visa;
        this.hide_mada = hide_mada;
        this.hide_stc = hide_stc;
    }

    protected LimitResponseModel(Parcel in) {
        id = in.readInt();
        order_limit = in.readDouble();
        shipping_price = in.readDouble();
        tax = in.readDouble();
        payment_tasleem = in.readInt();
        discount = in.readDouble();
        hide_elec_payment = in.readInt();
        hide_tasleem_payment = in.readInt();
        hide_bank_payment = in.readInt();
        hide_delivery = in.readInt();
        hide_branch_tasleem = in.readInt();
        hide_cupon = in.readInt();
        hide_visa = in.readInt();
        hide_mada = in.readInt();
        hide_stc = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOrder_limit() {
        return order_limit;
    }

    public void setOrder_limit(double order_limit) {
        this.order_limit = order_limit;
    }

    public double getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(double shipping_price) {
        this.shipping_price = shipping_price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getPayment_tasleem() {
        return payment_tasleem;
    }

    public void setPayment_tasleem(int payment_tasleem) {
        this.payment_tasleem = payment_tasleem;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getHide_elec_payment() {
        return hide_elec_payment;
    }

    public void setHide_elec_payment(int hide_elec_payment) {
        this.hide_elec_payment = hide_elec_payment;
    }

    public int getHide_tasleem_payment() {
        return hide_tasleem_payment;
    }

    public void setHide_tasleem_payment(int hide_tasleem_payment) {
        this.hide_tasleem_payment = hide_tasleem_payment;
    }

    public int getHide_bank_payment() {
        return hide_bank_payment;
    }

    public void setHide_bank_payment(int hide_bank_payment) {
        this.hide_bank_payment = hide_bank_payment;
    }

    public int getHide_delivery() {
        return hide_delivery;
    }

    public void setHide_delivery(int hide_delivery) {
        this.hide_delivery = hide_delivery;
    }

    public int getHide_branch_tasleem() {
        return hide_branch_tasleem;
    }

    public void setHide_branch_tasleem(int hide_branch_tasleem) {
        this.hide_branch_tasleem = hide_branch_tasleem;
    }

    public int getHide_cupon() {
        return hide_cupon;
    }

    public void setHide_cupon(int hide_cupon) {
        this.hide_cupon = hide_cupon;
    }

    public int getHide_visa() {
        return hide_visa;
    }

    public void setHide_visa(int hide_visa) {
        this.hide_visa = hide_visa;
    }

    public int getHide_mada() {
        return hide_mada;
    }

    public void setHide_mada(int hide_mada) {
        this.hide_mada = hide_mada;
    }

    public int getHide_stc() {
        return hide_stc;
    }

    public void setHide_stc(int hide_stc) {
        this.hide_stc = hide_stc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(order_limit);
        parcel.writeDouble(shipping_price);
        parcel.writeDouble(tax);
        parcel.writeInt(payment_tasleem);
        parcel.writeDouble(discount);
        parcel.writeInt(hide_elec_payment);
        parcel.writeInt(hide_tasleem_payment);
        parcel.writeInt(hide_bank_payment);
        parcel.writeInt(hide_delivery);
        parcel.writeInt(hide_branch_tasleem);
        parcel.writeInt(hide_cupon);
        parcel.writeInt(hide_visa);
        parcel.writeInt(hide_mada);
        parcel.writeInt(hide_stc);
    }
}
