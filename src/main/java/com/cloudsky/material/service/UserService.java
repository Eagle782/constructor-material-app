package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.ProjectDao;
import com.cloudsky.material.dao.UserDao;
import com.cloudsky.material.dto.UserBo;
import com.cloudsky.material.dto.UserProfile;
import com.cloudsky.material.entity.Project;
import com.cloudsky.material.entity.Role;
import com.cloudsky.material.utility.AppProperties;
import com.cloudsky.material.utility.EncryptUtil;
import com.cloudsky.material.utility.PageData;


@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	
	@Autowired
	private AppProperties appProperties;
	
	public UserProfile login(String userName, String password) {
		
		UserProfile userProfile = null;
		if(userName.equals(appProperties.getAppAdminUserName())) {
			if(password.equals(appProperties.getAppAdminPassword())) {
				userProfile = new UserProfile();
				userProfile.setUserId("0");
				userProfile.setUserName(userName);
				userProfile.setName("系统管理员");
				userProfile.setEnableTopReport(1);
				userProfile.setEnableSysConfig(1);
				userProfile.setToken("fdfjud885ferer457hgde");
				userProfile.setFileUrlPrefix(appProperties.getFileUrlPrefix());
				
				List<Project> projects = projectDao.findAll();
				userProfile.setProjects(projects);
			}
		}
		else {
			Map<String,Object> user = userDao.findByUserNameAndPassword(userName, EncryptUtil.encryptByMd5(password));
			if(user!=null) {
				userProfile = new UserProfile();
				userProfile.setUserId(user.get("id").toString());
				userProfile.setUserName(userName);
				userProfile.setName(user.get("name").toString());
				userProfile.setEnableTopReport(Integer.parseInt(user.get("enable_top_report").toString()));
				
				int counts = 0;
				counts += Integer.parseInt(user.get("enable_project").toString());
				counts += Integer.parseInt(user.get("enable_category").toString());
				counts += Integer.parseInt(user.get("enable_unit").toString());
				counts += Integer.parseInt(user.get("enable_item").toString());
				counts += Integer.parseInt(user.get("enable_role").toString());
				counts += Integer.parseInt(user.get("enable_user").toString());
				counts += Integer.parseInt(user.get("enable_custom_config").toString());
				
				if(counts>0) {
					userProfile.setEnableSysConfig(1);
				}
				else {
					userProfile.setEnableSysConfig(0);
				}
				
				userProfile.setToken("fdfjud885ferer457hgde");
				userProfile.setFileUrlPrefix(appProperties.getFileUrlPrefix());
				
				List<Project> projects = projectDao.findListByUserId(userProfile.getUserId());
				userProfile.setProjects(projects);
			}
			
		}
		
		return userProfile;
		
	}
	

	
	public PageData getPage(int pageNo, int pageSize){
		return userDao.getPage(pageNo,pageSize);
	}
	
	
	public UserBo get(String id){
		
		
		
		Map<String,Object> user = userDao.get(id);
		if(user!=null) {
			UserBo userBo = new UserBo();
			
			userBo.setId(user.get("id").toString());
			userBo.setUserName(user.get("user_name").toString());
			userBo.setName(user.get("name").toString());
			userBo.setEnableTopReport(Integer.parseInt(user.get("enable_top_report").toString()));
			userBo.setEnableProject(Integer.parseInt(user.get("enable_project").toString()));
			userBo.setEnableCategory(Integer.parseInt(user.get("enable_category").toString()));
			userBo.setEnableUnit(Integer.parseInt(user.get("enable_unit").toString()));
			userBo.setEnableItem(Integer.parseInt(user.get("enable_item").toString()));
			userBo.setEnableRole(Integer.parseInt(user.get("enable_role").toString()));
			userBo.setEnableUser(Integer.parseInt(user.get("enable_user").toString()));
			userBo.setEnableCustomConfig(Integer.parseInt(user.get("enable_custom_config").toString()));
			
			List<Project> projects = new ArrayList<Project>();
			List<Map<String,Object>> projectList = userDao.getProjectList(id);
			for(int i=0; i<projectList.size(); i++) {
				Project project = new Project();
				project.setId(projectList.get(i).get("project_id").toString());
				projects.add(project);
			}
			userBo.setProjects(projects);
			
			
			List<Role> roles = new ArrayList<Role>();
			List<Map<String,Object>> roleList = userDao.getRoleList(id);
			for(int i=0; i<roleList.size(); i++) {
				Role role = new Role();
				role.setId(roleList.get(i).get("role_id").toString());
				roles.add(role);
			}
			userBo.setRoles(roles);
			
			return userBo;
		}
		else {
			return null;
		}
		

	}
	
	
	public int updatePassword(String id, String oldPassword, String newPassword){
		int result = 0;
		int counts = userDao.getCountsByUserIdAndPassword(id, EncryptUtil.encryptByMd5(oldPassword));
		if(counts>0) {
			result = userDao.updatePassword(id, EncryptUtil.encryptByMd5(newPassword));
		}
		else {
			result = 2;
		}
		
		return result;
	}
	
	
	public int resetPassword(String id){
		final String defaultPassword = "123456";
		return userDao.updatePassword(id, EncryptUtil.encryptByMd5(defaultPassword));
	}
	
	
	public int add(UserBo user){
		int result = 0;
		int counts = userDao.getCountsByUserName(user.getUserName());
		if(counts>0) {
			result = 2;
		}
		else {
			user.setPassword(EncryptUtil.encryptByMd5(user.getPassword())); 
			if(userDao.add(user)) {
				result = 1;
			}
		}
		
		return result;
	}
	
	
	
	public int update(UserBo user){
		int result = 0;
		int counts = userDao.getCountsByUserNameAndExcludeId(user.getUserName(), user.getId());
		if(counts>0) {
			result = 2;
		}
		else {
			if(userDao.update(user)) {
				result = 1;
			}
		}
		
		return result;
	}
	
	
	
	public int delete(List<String> ids){

		int result = 0;
		if(userDao.delete(ids)) {
			result = 1;
		}
		return result;
	}
	
	
}
