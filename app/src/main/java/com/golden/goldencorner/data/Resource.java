package com.golden.goldencorner.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resource<T> {

    @Nullable
    private T data;
    @NonNull
    private Status status;
    @Nullable
    private String message;
    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount = 0;

    public static <T> Resource<T> success (@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@NonNull String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.data       = data;
        this.status     = status;
        this.message    = message;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}