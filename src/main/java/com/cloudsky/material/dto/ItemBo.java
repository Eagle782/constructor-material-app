package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.Sku;

public class ItemBo {

	private String id;
	private String categoryId;
	private String name;
	private String code;
	private String unit;
	private List<Sku> skus;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public List<Sku> getSkus() {
		return skus;
	}
	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
}
