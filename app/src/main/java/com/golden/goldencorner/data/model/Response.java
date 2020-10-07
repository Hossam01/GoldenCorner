package com.golden.goldencorner.data.model;

import java.util.List;

public class Response{
	private List<DataItem> data;
	private int status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}