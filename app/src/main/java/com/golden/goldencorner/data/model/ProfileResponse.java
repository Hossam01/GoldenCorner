package com.golden.goldencorner.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("status")
	private int status;

	public List<DataItem> getData(){
		return data;
	}

	public int getStatus(){
		return status;
	}
}