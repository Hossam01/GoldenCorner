package com.golden.goldencorner.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTerms {

	@SerializedName("data")
	private List<DataItemTerms> data;

	@SerializedName("status")
	private int status;

	public void setData(List<DataItemTerms> data){
		this.data = data;
	}

	public List<DataItemTerms> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}