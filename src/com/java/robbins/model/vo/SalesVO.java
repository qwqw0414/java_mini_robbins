package com.java.robbins.model.vo;

import java.io.Serializable;

public class SalesVO implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	private String iceName;
	private int iceSales;

	public SalesVO() {}
	public SalesVO(String iceName, int iceStock) {
		this.iceName = iceName;
		this.iceSales = iceStock;
	}
	
	/**
	 * 아이스크림 이름이 같다면 동일한 객체로 취급
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesVO other = (SalesVO) obj;
		if (iceName == null) {
			if (other.iceName != null)
				return false;
		} else if (!iceName.equals(other.iceName))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iceName == null) ? 0 : iceName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Stock [iceName=" + iceName + ", iceStock=" + iceSales + "]";
	}

	public String getIceName() {
		return iceName;
	}

	public void setIceName(String iceName) {
		this.iceName = iceName;
	}

	public int getIceStock() {
		return iceSales;
	}

	public void setIceStock(int iceStock) {
		this.iceSales = iceStock;
	}

}
