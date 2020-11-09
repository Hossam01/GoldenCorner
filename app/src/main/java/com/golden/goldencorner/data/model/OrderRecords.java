package com.golden.goldencorner.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRecords {

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("branch")
    @Expose
    public BranchRecords branch;
    @SerializedName("reference_no")
    @Expose
    public String referenceNo;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("tax")
    @Expose
    public long tax;
    @SerializedName("shipping")
    @Expose
    public Long shipping;
    @SerializedName("payment_tasleem")
    @Expose
    public Long paymentTasleem;
    @SerializedName("discount")
    @Expose
    public Long discount;
    @SerializedName("total_price")
    @Expose
    public double totalPrice;
    @SerializedName("tax_price")
    @Expose
    public double taxPrice;
    @SerializedName("discount_price")
    @Expose
    public double discountPrice;

    @SerializedName("discount_code_persent")
    @Expose
    public double discountPricePersent;

    @SerializedName("discount_code_price")
    @Expose
    public double discountCodePrice;
    @SerializedName("driver")
    @Expose
    public Driver driver;
    @SerializedName("is_rate")
    @Expose
    public Long is_rate;

    public double getDiscountCodePrice() {
        return discountCodePrice;
    }

    public void setDiscountCodePrice(double discountCodePrice) {
        this.discountCodePrice = discountCodePrice;
    }

    @SerializedName("grand_total")
    @Expose
    public double grandTotal;
    @SerializedName("number_of_items")
    @Expose
    public Long numberOfItems;
    @SerializedName("payment_method")
    @Expose
    public Long paymentMethod;

    public double getDiscountPricePersent() {
        return discountPricePersent;
    }

    @SerializedName("payment_method_name")
    @Expose
    public String paymentMethodName;
    @SerializedName("payment_method_name_en")
    @Expose
    public String paymentMethodNameEn;
    @SerializedName("delivery_status")
    @Expose
    public Long deliveryStatus;
    @SerializedName("delivery_status_name")
    @Expose
    public String deliveryStatusName;
    @SerializedName("delivery_status_name_en")
    @Expose
    public String deliveryStatusNameEn;
    @SerializedName("total_quantity")
    @Expose
    public Long totalQuantity;
    @SerializedName("status")
    @Expose
    public Long status;
    @SerializedName("status_name")
    @Expose
    public String statusName;
    @SerializedName("order_time")
    @Expose
    public String orderTime;
    @SerializedName("product")
    @Expose
    public List<Product> product = null;

    public void setDiscountPricePersent(double discountPricePersent) {
        this.discountPricePersent = discountPricePersent;
    }

    public Long getIs_rate() {
        return is_rate;
    }

    public void setIs_rate(Long is_rate) {
        this.is_rate = is_rate;
    }

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

    public BranchRecords getBranch() {
        return branch;
    }

    public void setBranch(BranchRecords branch) {
        this.branch = branch;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public Long getShipping() {
        return shipping;
    }

    public void setShipping(Long shipping) {
        this.shipping = shipping;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Long getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getPaymentMethodNameEn() {
        return paymentMethodNameEn;
    }

    public void setPaymentMethodNameEn(String paymentMethodNameEn) {
        this.paymentMethodNameEn = paymentMethodNameEn;
    }

    public Long getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Long deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatusName() {
        return deliveryStatusName;
    }

    public void setDeliveryStatusName(String deliveryStatusName) {
        this.deliveryStatusName = deliveryStatusName;
    }

    public String getDeliveryStatusNameEn() {
        return deliveryStatusNameEn;
    }

    public void setDeliveryStatusNameEn(String deliveryStatusNameEn) {
        this.deliveryStatusNameEn = deliveryStatusNameEn;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
