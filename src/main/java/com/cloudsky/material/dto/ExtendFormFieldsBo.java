package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.ExtendFormField;

public class ExtendFormFieldsBo {

	private String userId;
	private List<ExtendFormField> fields;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<ExtendFormField> getFields() {
		return fields;
	}
	public void setFields(List<ExtendFormField> fields) {
		this.fields = fields;
	}
}
