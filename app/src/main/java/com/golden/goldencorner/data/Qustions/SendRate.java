package com.golden.goldencorner.data.Qustions;

public class SendRate {
    String question_id, rate, type;

    public SendRate(String question_id, String rate, String type) {
        this.question_id = question_id;
        this.rate = rate;
        this.type = type;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
