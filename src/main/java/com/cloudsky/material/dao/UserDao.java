package com.cloudsky.material.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.dto.UserBo;
import com.cloudsky.material.entity.Project;
import com.cloudsky.material.entity.Role;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class UserDao {
	
    public PageData getPage(int pageNo, int pageSize){
		
    	StringBuilder sql=new StringBuilder();
    	sql.append("select a.id,a.name,a.user_name userName,group_concat(c.name) roles ");   
    	sql.append("from user a left join user_role b on a.id=b.user_id ");   	
    	sql.append("left join role c on b.role_id=c.id ");   
    	sql.append("group by a.id");   
    	
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
    
    
    public Map<String,Object> get(String id){
		String sql = "SELECT id,user_name,name,enable_top_report,enable_project,enable_category,enable_unit,enable_item,enable_role,enable_user,enable_custom_config from user where id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql,args);
    	if(rows.size()>0){
    		return rows.get(0); 
    	}
    	else{
    		return null;
    	}
	}

	public Map<String,Object> findByUserNameAndPassword(String userName, String password){
		
		String sql = "select id,name,user_name,password,enable_top_report,enable_project,enable_category,enable_unit,enable_item,enable_role,enable_user,enable_custom_config from user where user_name= ? and password=?";   	
    	Object[] args ={userName,password};    	
    	List<Map<String,Object>> users = DBUtil.Query(sql,args);
    	if(users.size()>0) {
    		return users.get(0);  
    	}
    	else {
    		return null;
    	} 
	}
	
	
	public int getCountsByUserIdAndPassword(String id, String password){
		
		String sql = "select count(*) from user where id= ? and password=?";
		Object[] args={id,
				password};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
    public int getCountsByUserName(String userName){
		
		String sql = "select count(*) from user where user_name= ?";
		Object[] args={userName};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByUserNameAndExcludeId(String userName, String excludeId){
		
		String sql = "select count(*) from user where user_name= ? and id != ?";
		Object[] args={userName,excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    public List<Map<String,Object>> getProjectList(String id){
		String sql = "SELECT id,project_id from user_project where user_id=?";   
		Object[] args ={id}; 
		List<Map<String,Object>> projects = DBUtil.Query(sql,args);
		return projects;
    }
    
    
    public List<Map<String,Object>> getRoleList(String id){
		String sql = "SELECT id,role_id from user_role where user_id=?";   
		Object[] args ={id}; 
		List<Map<String,Object>> roles = DBUtil.Query(sql,args);
		return roles;
    }
	
	
    public int updatePassword(String id, String password){
		
		StringBuilder sql=new StringBuilder();		
		sql.append("update user set password=? where id=?");	
		
		Object[] args={password,
				id};
		
		return DBUtil.update(sql.toString(), args);
	}
    
    
    public boolean add(UserBo user){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into user (user_name,password,name,enable_top_report,enable_project,enable_category,enable_unit,enable_item,");	
		sql.append("enable_role,enable_user,enable_custom_config,create_time)");
		sql.append(" values ('" + SqlUtil.DeInjection(user.getUserName()) + "','" + user.getPassword() + "','" + SqlUtil.DeInjection(user.getName()) + "',");
		sql.append(user.getEnableTopReport() + ",");
		sql.append(user.getEnableProject() + ",");
		sql.append(user.getEnableCategory() + ",");
		sql.append(user.getEnableUnit() + ",");
		sql.append(user.getEnableItem() + ",");
		sql.append(user.getEnableUser() + ",");
		sql.append(user.getEnableRole() + ",");
		sql.append(user.getEnableCustomConfig() + ",now());");
		sql.append("set @user_id=@@IDENTITY;");
		
        for(Role role : user.getRoles()) {
        	sql.append("insert into user_role (user_id,role_id) values (@user_id," + role.getId() + ");");
        }
        
        for(Project project : user.getProjects()) {
        	sql.append("insert into user_project (user_id,project_id) values (@user_id," + project.getId() + ");");
        }

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
	public boolean update(UserBo user){

		StringBuilder sql=new StringBuilder();		
		sql.append("update user set user_name = '" + SqlUtil.DeInjection(user.getUserName()) + "',");	
		sql.append("name='" + SqlUtil.DeInjection(user.getName()) + "',");
		sql.append("enable_top_report=" + user.getEnableTopReport() + ",");
		sql.append("enable_project=" + user.getEnableProject() + ",");
		sql.append("enable_category=" + user.getEnableCategory() + ",");
		sql.append("enable_unit=" + user.getEnableUnit() + ",");
		sql.append("enable_item=" + user.getEnableItem() + ",");
		sql.append("enable_role=" + user.getEnableRole() + ",");
		sql.append("enable_user=" + user.getEnableUser() + ",");
		sql.append("enable_custom_config=" + user.getEnableCustomConfig());
		sql.append(" where id=" + user.getId() + ";");
		
		sql.append("delete from user_role where user_id=" + user.getId() + ";");
		
		for(Role role : user.getRoles()) {
        	sql.append("insert into user_role (user_id,role_id) values (" + user.getId() +"," + role.getId() + ");");
        }
		
		sql.append("delete from user_project where user_id=" + user.getId() + ";");
		
		for(Project project : user.getProjects()) {
        	sql.append("insert into user_project (user_id,project_id) values (" +user.getId() + ","+ project.getId() + ");");
        }

		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("delete from user_role where user_id=" + id + ";");
        	sql.append("delete from user_project where user_id=" + id + ";");
    		sql.append("delete from user where id=" + id + ";");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    
    
   
	
}
