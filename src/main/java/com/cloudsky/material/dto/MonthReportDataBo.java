package com.cloudsky.material.dto;

import java.util.List;

public class MonthReportDataBo {

	private String userId;
	private String projectId;	
	private List<ReportDataItem> rows;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<ReportDataItem> getRows() {
		return rows;
	}
	public void setRows(List<ReportDataItem> rows) {
		this.rows = rows;
	}
}
