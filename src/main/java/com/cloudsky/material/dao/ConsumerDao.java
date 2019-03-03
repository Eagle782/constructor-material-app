package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.entity.Category;
import com.cloudsky.material.entity.Consumer;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class ConsumerDao {

	public PageData getPage(String projectId, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name from consumer where project_id=" + projectId);	
		
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
	

	public List<Map<String,Object>> findAll(String projectId){
		String sql = "SELECT id,name from consumer where project_id=" + projectId;   	  	
    	List<Map<String,Object>> projects = DBUtil.Query(sql);
    	return projects;
	}

	
	public Map<String,Object> get(String id){
		String sql = "SELECT id,name from consumer where id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> projects = DBUtil.Query(sql,args);
    	if(projects.size()>0){
    		return projects.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
    public int getCountsByProjectId(String projectId){
		
		String sql = "select count(*) from consumer where project_id= ?";
		Object[] args={projectId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    
    public int getCountsByName(String projectId, String name){
		
		String sql = "select count(*) from consumer where project_id= ? and name= ?";
		Object[] args={projectId, name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String projectId, String name, String excludeId){
		
		String sql = "select count(*) from consumer where project_id= ? and name= ? and id != ?";
		Object[] args={projectId, name, excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public int add(Consumer consumer){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into consumer (project_id, name)");	
		sql.append(" values (?,?)");
		
		Object[] args={
				consumer.getProjectId(),
				consumer.getName()};

		return DBUtil.update(sql.toString(), args);
	}
	
	
	
	public int update(Consumer consumer){

		StringBuilder sql=new StringBuilder();		
		sql.append("update consumer set name = ? ");	
		sql.append("where id=?");
		
		Object[] args={
				consumer.getName(),
				consumer.getId()};

		
		return DBUtil.update(sql.toString(), args);
	}
	
	
	public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
		for(String id : ids) {				
			sql.append("delete from consumer where id= " + id + ";");
		}
  
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
}
