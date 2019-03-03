package com.cloudsky.material.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cloudsky.material.entity.Project;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;




@Component
public class ProjectDao {

	public PageData getPage(int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name from project");	
		
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
	

	public List<Project> findAll(){
		String sql = "SELECT id,name from project";   	  	
    	List<Project> projects = DBUtil.getJdbcTemplate().query(sql, new ProjectRowMapper());
    	return projects;
	}

	
	public Map<String,Object> get(String id){
		String sql = "SELECT id,name from project where id = ?";   	
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
		
		String sql = "select count(*) from project where name= ?";
		Object[] args={name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String name, String excludeId){
		
		String sql = "select count(*) from project where name= ? and id != ?";
		Object[] args={name,excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public int add(String name){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into project (name)");	
		sql.append(" values (?)");
		
		Object[] args={name};

		return DBUtil.update(sql.toString(), args);
	}
	
	
	
	public int update(String id, String name){

		StringBuilder sql=new StringBuilder();		
		sql.append("update project set name = ? ");	
		sql.append("where id=?");
		
		Object[] args={
				name,
				id};

		
		return DBUtil.update(sql.toString(), args);
	}
	
	
	public int delete(String id){
		String sql = "delete from project where id= ?";
		Object[] args ={id};   
		return DBUtil.update(sql, args);
	}
	
	
    public List<Project> findListByUserId(String userId){
		
		String sql = "select b.id,b.name from user_project a join project b on a.project_id = b.id where a.user_id = ?";   	
    	Object[] args ={userId};    
  
    	List<Project> projects = DBUtil.getJdbcTemplate().query(sql, args, new ProjectRowMapper());
    	return projects;
	}
	
	
	public class ProjectRowMapper implements RowMapper<Project> {  
		  
        @Override  
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {  
        	Project project = new Project();  
        	project.setId(rs.getString("id"));
        	project.setName(rs.getString("name"));  
            return project;  
        }  
          
    }  
	
	
	
}
