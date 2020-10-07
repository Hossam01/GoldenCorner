package com.golden.goldencorner.data.model;

public class ExtensionItem{
	private int colorId;
	private double price;
	private int productId;
	private String extensionName;
	private int id;

	public void setColorId(int colorId){
		this.colorId = colorId;
	}

	public int getColorId(){
		return colorId;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setExtensionName(String extensionName){
		this.extensionName = extensionName;
	}

	public String getExtensionName(){
		return extensionName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}
