package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.PermissionDao;
import com.cloudsky.material.dao.UserDao;


@Service
public class PermissionService {

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private UserDao userDao;
	
	public List<Map<String,Object>> getTreeList(){
		
		List<Map<String,Object>> categorys = permissionDao.getCategoryList();
		for(int i=0; i<categorys.size(); i++) {
			String category = categorys.get(i).get("category").toString();
			List<Map<String,Object>> permissions = permissionDao.getListByCategory(category);
			categorys.get(i).put("permissions", permissions);
		}
		
		return categorys;
	}
	
	
	public boolean verify(String userId, String projectId, String permissionCode) {
		
		boolean pass = false;
		if(userId.equals("0")) {  //super admin
			pass = true;
		}
		else {
			int counts = permissionDao.getCounts(userId, projectId, permissionCode);
			if(counts>0) {
				pass = true;
			}
		}
		
		return pass;
	}
	
	
	public boolean verify(String userId, String menuCode) {
		boolean pass = false;
		if(userId.equals("0")) {  //super admin
			pass = true;
		}
		else {
			Map<String,Object> user = userDao.get(userId);
			if(user!=null) {
				if(user.get(menuCode).toString().equals("1")) {
					pass = true;
				}
			}
		}
		
		
		return pass;
	}
	
	
}
