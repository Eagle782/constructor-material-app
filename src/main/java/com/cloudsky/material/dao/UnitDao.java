package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.entity.Unit;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class UnitDao {

	public PageData getPage(int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name from unit");	
		
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
		String sql = "SELECT id,name from unit";   	  	
    	List<Map<String,Object>> projects = DBUtil.Query(sql);
    	return projects;
	}

	
	public Map<String,Object> get(String id){
		String sql = "SELECT id,name from unit where id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> projects = DBUtil.Query(sql,args);
    	if(projects.size()>0){
    		return projects.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	

    
    public int getCountsByName(String name){
		
		String sql = "select count(*) from unit where name= ?";
		Object[] args={name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String name, String excludeId){
		
		String sql = "select count(*) from unit where name= ? and id != ?";
		Object[] args={name, excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public int add(Unit unit){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into unit (name)");	
		sql.append(" values (?)");
		
		Object[] args={
				unit.getName()};

		return DBUtil.update(sql.toString(), args);
	}
	
	
	
	public int update(Unit unit){

		StringBuilder sql=new StringBuilder();		
		sql.append("update unit set name = ? ");	
		sql.append("where id=?");
		
		Object[] args={
				unit.getName(),
				unit.getId()};

		
		return DBUtil.update(sql.toString(), args);
	}
	
	
	public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
		for(String id : ids) {				
			sql.append("delete from unit where id= " + id + ";");
		}
  
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
}
