package com.cloudsky.material.dto;

import java.net.URLDecoder;

public class ReportStockInFilter {

	private String categoryId;
	private String startDate;
	private String endDate;
	private String typeId;
	private String itemKeyword;
	private String supplierKeyword;
	
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getItemKeyword() {
		return itemKeyword;
	}
	public void setItemKeyword(String itemKeyword) {
		this.itemKeyword = itemKeyword;
	}
	public String getSupplierKeyword() {
		return supplierKeyword;
	}
	public void setSupplierKeyword(String supplierKeyword) {
		this.supplierKeyword = supplierKeyword;
	}
	
	
	public void urlDecode() throws Exception {
		if(this.itemKeyword!=null) {
			this.itemKeyword = URLDecoder.decode(this.itemKeyword, "UTF-8");
		}
		
		if(this.supplierKeyword!=null) {
			this.supplierKeyword = URLDecoder.decode(this.supplierKeyword, "UTF-8");
		}
	}
	
}
