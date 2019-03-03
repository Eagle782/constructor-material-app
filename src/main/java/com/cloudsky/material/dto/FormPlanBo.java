package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.FormPlanItem;

public class FormPlanBo {

	private String id;
	private String projectId;
	private String planDate;
	private String categoryId;
	private String categoryName;
	private String serialNo;
	private String creator;
	private List<String> attachFiles;
	private List<FormPlanItem> items;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
	public List<FormPlanItem> getItems() {
		return items;
	}
	public void setItems(List<FormPlanItem> items) {
		this.items = items;
	}
}
