package com.java.robbins.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String size; //선택한 사이즈
	private int iceEa; //선택할 수 있는 아이스크림 개수
	private int price; //가격
	private List<String> selectedList = new ArrayList<>(); //선택한 아이스크림 목록
	private String coneCup; //콘&컵
	private String takeOut; //식사장소 : 매장&포장
	private int spoonEa; //스푼개수
	
	public OrderVO() {}
	public OrderVO(String size, int iceEa, int price) {
		this.size = size;
		this.iceEa = iceEa;
		this.price = price;
	}
	public OrderVO(String size, int iceEa, int price, List<String> selectedList, String coneCup, String takeOut, int spoonEa) {
		this.size = size;
		this.iceEa = iceEa;
		this.price = price;
		this.selectedList = selectedList;
		this.coneCup = coneCup;
		this.takeOut = takeOut;
		this.spoonEa = spoonEa;
	}
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getIceEa() {
		return iceEa;
	}
	public void setIceEa(int iceEa) {
		this.iceEa = iceEa;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<String> getSelectedList() {
		return selectedList;
	}
	public void setSelectedList(String selectedIce) {
		this.selectedList.add(selectedIce);
	}
	public String getConeCup() {
		return coneCup;
	}
	public void setConeCup(String coneCup) {
		this.coneCup = coneCup;
	}
	public String getTakeOut() {
		return takeOut;
	}
	public void setTakeOut(String takeOut) {
		this.takeOut = takeOut;
	}
	public int getSpoonEa() {
		return spoonEa;
	}
	public void setSpoonEa(int spoonEa) {
		this.spoonEa = spoonEa;
	}
	
	@Override
	public String toString() {
		return "IcecreamVO [size=" + size + ", iceEa=" + iceEa + ", price=" + price + ", selectedList=" + selectedList
				+ ", coneCup=" + coneCup + ", takeOut=" + takeOut + ", spoonEa=" + spoonEa + "]" +"\n";
	}
	
}
