package com.golden.goldencorner.data.model;

public class SizeItem{
	private String sizeName;
	private int quantity;
	private int price;
	private int productId;
	private int disPrice;
	private int calorie;
	private int sizeId;
	private int id;
	private int isDefault;

	public void setSizeName(String sizeName){
		this.sizeName = sizeName;
	}

	public String getSizeName(){
		return sizeName;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setDisPrice(int disPrice){
		this.disPrice = disPrice;
	}

	public int getDisPrice(){
		return disPrice;
	}

	public void setCalorie(int calorie){
		this.calorie = calorie;
	}

	public int getCalorie(){
		return calorie;
	}

	public void setSizeId(int sizeId){
		this.sizeId = sizeId;
	}

	public int getSizeId(){
		return sizeId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIsDefault(int isDefault){
		this.isDefault = isDefault;
	}

	public int getIsDefault(){
		return isDefault;
	}
}
