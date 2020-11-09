package com.golden.goldencorner.data.Qustions;

import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("1")
    private String jsonMember1;

    @SerializedName("2")
    private String jsonMember2;

    @SerializedName("3")
    private String jsonMember3;

    @SerializedName("4")
    private String jsonMember4;

    @SerializedName("5")
    private String jsonMember5;

    public String getJsonMember1() {
        return jsonMember1;
    }

    public void setJsonMember1(String jsonMember1) {
        this.jsonMember1 = jsonMember1;
    }

    public String getJsonMember2() {
        return jsonMember2;
    }

    public void setJsonMember2(String jsonMember2) {
        this.jsonMember2 = jsonMember2;
    }

    public String getJsonMember3() {
        return jsonMember3;
    }

    public void setJsonMember3(String jsonMember3) {
        this.jsonMember3 = jsonMember3;
    }

    public String getJsonMember4() {
        return jsonMember4;
    }

    public void setJsonMember4(String jsonMember4) {
        this.jsonMember4 = jsonMember4;
    }

    public String getJsonMember5() {
        return jsonMember5;
    }

    public void setJsonMember5(String jsonMember5) {
        this.jsonMember5 = jsonMember5;
    }
}