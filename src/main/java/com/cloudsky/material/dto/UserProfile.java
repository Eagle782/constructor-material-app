package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.Project;

public class UserProfile {

	private String userId;
	private String userName;
	private String name;
	private String token;
	private int enableTopReport;	
	private int enableSysConfig;
	private String fileUrlPrefix;
	private List<Project> projects;
	
	public String getFileUrlPrefix() {
		return fileUrlPrefix;
	}
	public void setFileUrlPrefix(String fileUrlPrefix) {
		this.fileUrlPrefix = fileUrlPrefix;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public int getEnableTopReport() {
		return enableTopReport;
	}
	public void setEnableTopReport(int enableTopReport) {
		this.enableTopReport = enableTopReport;
	}
	public int getEnableSysConfig() {
		return enableSysConfig;
	}
	public void setEnableSysConfig(int enableSysConfig) {
		this.enableSysConfig = enableSysConfig;
	}
	
}
