package com.golden.goldencorner.data.Qustions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem {

    @SerializedName("driver")
    private List<DriverItem> driver;

    @SerializedName("answer")
    private Answer answer;

    @SerializedName("order")
    private List<OrderItem> order;

    public List<DriverItem> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverItem> driver) {
        this.driver = driver;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<OrderItem> getOrder() {
        return order;
    }

    public void setOrder(List<OrderItem> order) {
        this.order = order;
    }
}