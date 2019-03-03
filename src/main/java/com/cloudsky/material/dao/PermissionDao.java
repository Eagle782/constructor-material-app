package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.utility.DBUtil;

@Component
public class PermissionDao {

   public List<Map<String,Object>> getCategoryList(){
		
		String sql = "select distinct category from permission";		
		return DBUtil.Query(sql);
		
	}
   
   
   public List<Map<String,Object>> getListByCategory(String category){
		
		String sql = "select id,code,action from permission where category=?";
		Object[] args ={category}; 
		return DBUtil.Query(sql,args);
		
	}
   
   
   public int getCounts(String userId, String projectId, String code) {
	   StringBuilder sql=new StringBuilder();		
	   sql.append("select count(1) counts from user_role a join user_project b on a.user_id=b.user_id ");
	   sql.append("join role_permission c on a.role_id=c.role_id ");
	   sql.append("where a.user_id=? and b.project_id = ? and c.permission_code=?");
	   
	   Object[] args ={userId, projectId, code}; 
	   
	   return DBUtil.QueryForInt(sql.toString(), args);
   }
   
   
}
