package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("totalCount")
    private int totalCount;
    @SerializedName("pageCount")
    private int pageCount;
    @SerializedName("currentPage")
    private int currentPage;
    @SerializedName("perPage")
    private int perPage;
    @SerializedName("NumberOfPage")
    private int numberOfPage;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
}
