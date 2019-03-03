package com.cloudsky.material.dto;

import java.util.List;


import com.cloudsky.material.entity.FormStockInItem;

public class FormStockInBo {

	private String id;
	private String projectId;
	private String formCheckId;
	private String categoryId;
	private String categoryName;
	private String supplierId;
	private String supplierName;
	private String type;
	private String typeName;
	private String applyDate;
	private String creator;
	private List<FormStockInItem> items;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public String getFormCheckId() {
		return formCheckId;
	}
	public void setFormCheckId(String formCheckId) {
		this.formCheckId = formCheckId;
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public List<FormStockInItem> getItems() {
		return items;
	}
	public void setItems(List<FormStockInItem> items) {
		this.items = items;
	}
}
