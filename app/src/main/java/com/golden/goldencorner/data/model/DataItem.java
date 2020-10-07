package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("model")
	private User model;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public User getModel(){
		return model;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}