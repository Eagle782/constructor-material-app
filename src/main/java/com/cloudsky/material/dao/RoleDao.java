package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.dto.RoleBo;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class RoleDao {

	public PageData getPage(int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name from role");	
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
		List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
		Integer totalRecords = DBUtil.QueryForInt(countSql);
		
		PageData page = new PageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
		
		return page;
	}
	

	public List<Map<String,Object>> findAll(){
		String sql = "SELECT id,name from role";   	  	
		List<Map<String,Object>> roles = DBUtil.Query(sql);
    	return roles;
	}

	
	public Map<String,Object> get(String id){
		String sql = "SELECT id,name from role where id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> projects = DBUtil.Query(sql,args);
    	if(projects.size()>0){
    		return projects.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
	
	
	
	public List<Map<String,Object>> getPermissionList(String id){
		String sql = "SELECT id,permission_code from role_permission where role_id=?";   
		Object[] args ={id}; 
		List<Map<String,Object>> permissions = DBUtil.Query(sql,args);
    	return permissions;
	}
	
	
    public int getCountsByName(String name){
		
		String sql = "select count(*) from role where name= ?";
		Object[] args={name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public boolean add(RoleBo role){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into role (name) values ('" + SqlUtil.DeInjection(role.getName()) + "');");	
		sql.append("set @role_id=@@IDENTITY;");
		
		for(String permissionCode : role.getPermissions()) {
			sql.append("insert into role_permission (role_id,permission_code) values (@role_id,'" + permissionCode + "');");
		}
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
	public boolean update(RoleBo role){

		StringBuilder sql=new StringBuilder();		
		sql.append("update role set name = '" + SqlUtil.DeInjection(role.getName()) + "'");	
		sql.append(" where id=" + role.getId() + ";");
				
		sql.append("delete from role_permission where role_id = " + role.getId() + ";");
		for(String permissionCode : role.getPermissions()) {
			
			sql.append("insert into role_permission (role_id,permission_code) values (" + role.getId() + ",'" + permissionCode + "');");
		}

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean delete(String id){
		StringBuilder sql=new StringBuilder();	
		sql.append("delete from role where id= " + id + ";");
		sql.append("delete from role_permission where role_id= " + id + ";");
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
}
