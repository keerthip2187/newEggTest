package com.newegg.qa.data;

public class Data {

	private String item;
	private String price;
	
	public Data(String item, String price){
		this.item=item;
		this.price=price;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
