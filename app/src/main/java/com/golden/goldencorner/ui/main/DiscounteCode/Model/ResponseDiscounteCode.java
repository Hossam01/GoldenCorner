package com.golden.goldencorner.ui.main.DiscounteCode.Model;

import java.util.List;

public class ResponseDiscounteCode {
    private List<DataItem> data;
    private int status;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}