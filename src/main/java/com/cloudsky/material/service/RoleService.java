package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.RoleDao;
import com.cloudsky.material.dto.RoleBo;
import com.cloudsky.material.utility.PageData;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public PageData getPage(int pageNo, int pageSize){
		return roleDao.getPage(pageNo, pageSize);
	}
	
	
	public List<Map<String,Object>> findAll(){
		return roleDao.findAll();
	}
	
	public RoleBo get(String id){
		RoleBo roleBo = new RoleBo();
		
		Map<String,Object> role = roleDao.get(id);
		List<Map<String,Object>> permissionList = roleDao.getPermissionList(id);
		
		roleBo.setId(id);
		roleBo.setName(role.get("name").toString());

		List<String> permissions = new ArrayList<String>();
		for(int i=0; i<permissionList.size(); i++) {
			permissions.add(permissionList.get(i).get("permission_code").toString());
		}
		
		roleBo.setPermissions(permissions);
		
		return roleBo;
	}
	
	
	public int add(RoleBo role){
		int result = 0;
		int counts = roleDao.getCountsByName(role.getName());
		if(counts>0) {
			result = 2;
		}
		else {
			boolean success = roleDao.add(role);
			if(success) {
				result = 1;
			}
		}
		return result;
	}
	
	
	public int update(RoleBo role){
		int result = 0;
		boolean success = roleDao.update(role);
		if(success) {
			result = 1;
		}
		return result;
	}
	
	
	public int delete(String id){
		int result = 0;
		boolean success = roleDao.delete(id);
		if(success) {
			result = 1;
		}
		return result;
	}
}
