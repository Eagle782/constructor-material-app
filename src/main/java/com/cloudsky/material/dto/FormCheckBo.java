package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.FormCheckItem;


public class FormCheckBo {

	private String id;
	private String projectId;	
	private String categoryId;
	private String categoryName;
	private String supplierId;
	private String supplierName;
	private String checkDate;
	private String shippingNo;
	private String remark;
	private String creator;
	private List<String> attachFiles;
	private List<FormCheckItem> items;
	
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getShippingNo() {
		return shippingNo;
	}
	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public List<String> getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(List<String> attachFiles) {
		this.attachFiles = attachFiles;
	}
	public List<FormCheckItem> getItems() {
		return items;
	}
	public void setItems(List<FormCheckItem> items) {
		this.items = items;
	}
}
