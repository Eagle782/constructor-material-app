package com.cloudsky.material.dto;

import java.util.List;

import com.cloudsky.material.entity.Project;
import com.cloudsky.material.entity.Role;

public class UserBo {

	private String id;
	private String name;
	private String userName;
	private String password;	
	private List<Role> roles;
	private List<Project> projects;	
	private int enableTopReport;
	private int enableProject;
	private int enableCategory;
	private int enableUnit;
	private int enableItem;
	private int enableRole;
	private int enableUser;
	private int enableCustomConfig;
	
	public int getEnableTopReport() {
		return enableTopReport;
	}
	public void setEnableTopReport(int enableTopReport) {
		this.enableTopReport = enableTopReport;
	}
	public int getEnableItem() {
		return enableItem;
	}
	public void setEnableItem(int enableItem) {
		this.enableItem = enableItem;
	}
	public int getEnableProject() {
		return enableProject;
	}
	public void setEnableProject(int enableProject) {
		this.enableProject = enableProject;
	}
	public int getEnableCategory() {
		return enableCategory;
	}
	public void setEnableCategory(int enableCategory) {
		this.enableCategory = enableCategory;
	}
	public int getEnableUnit() {
		return enableUnit;
	}
	public void setEnableUnit(int enableUnit) {
		this.enableUnit = enableUnit;
	}
	public int getEnableRole() {
		return enableRole;
	}
	public void setEnableRole(int enableRole) {
		this.enableRole = enableRole;
	}
	public int getEnableUser() {
		return enableUser;
	}
	public void setEnableUser(int enableUser) {
		this.enableUser = enableUser;
	}
	public int getEnableCustomConfig() {
		return enableCustomConfig;
	}
	public void setEnableCustomConfig(int enableCustomConfig) {
		this.enableCustomConfig = enableCustomConfig;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	

	
}
