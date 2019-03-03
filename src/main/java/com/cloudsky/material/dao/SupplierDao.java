package com.cloudsky.material.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.cloudsky.material.entity.Supplier;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.Page;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class SupplierDao {

	public Page<Supplier> getPage(String projectId, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name,primary_product,contactor,phone_no from Supplier where project_id=" + projectId);	
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
		List<Supplier> rows = DBUtil.getJdbcTemplate().query(sql.toString(), new SupplierRowMapper());
		Integer totalRecords = DBUtil.QueryForInt(countSql);
		
		Page<Supplier> page = new Page<Supplier>();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
		
		return page;
	}
	

	public List<Supplier> findAll(String projectId){
		String sql = "SELECT id,name,primary_product,contactor,phone_no from Supplier where project_id=" + projectId;   	  	
    	List<Supplier> suppliers = DBUtil.getJdbcTemplate().query(sql.toString(), new SupplierRowMapper());
    	return suppliers;
	}

	
	public Supplier get(String id){
		String sql = "SELECT id,name,primary_product,contactor,phone_no from Supplier where id = ?";   	
    	Object[] args ={id};    	
    	List<Supplier> suppliers = DBUtil.getJdbcTemplate().query(sql.toString(), args, new SupplierRowMapper());
    	if(suppliers.size()>0){
    		return suppliers.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
    public int getCountsByProjectId(String projectId){
		
		String sql = "select count(*) from Supplier where project_id= ?";
		Object[] args={projectId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    
    public int getCountsByName(String projectId, String name){
		
		String sql = "select count(*) from Supplier where project_id= ? and name= ?";
		Object[] args={projectId, name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String projectId, String name, String excludeId){
		
		String sql = "select count(*) from Supplier where project_id= ? and name= ? and id != ?";
		Object[] args={projectId, name, excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public int add(Supplier supplier){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into Supplier (project_id, name, primary_product, contactor, phone_no)");	
		sql.append(" values (?,?,?,?,?)");
		
		Object[] args={
				supplier.getProjectId(),
				supplier.getName(),
				supplier.getPrimaryProduct(),
				supplier.getContactor(),
				supplier.getPhoneNo()};

		return DBUtil.update(sql.toString(), args);
	}
	
	
	
	public int update(Supplier supplier){

		StringBuilder sql=new StringBuilder();		
		sql.append("update supplier set name = ?, primary_product=?, contactor=?, phone_no=? ");	
		sql.append("where id=?");
		
		Object[] args={
				supplier.getName(),
				supplier.getPrimaryProduct(),
				supplier.getContactor(),
				supplier.getPhoneNo(),
				supplier.getId()};

		
		return DBUtil.update(sql.toString(), args);
	}
	
	
	public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
		for(String id : ids) {				
			sql.append("delete from supplier where id= " + id + ";");
		}
  
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
	public class SupplierRowMapper implements RowMapper<Supplier> {  
		  
        @Override  
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {  
        	Supplier supplier = new Supplier();  
        	supplier.setId(rs.getString("id"));
        	supplier.setName(rs.getString("name"));  
        	supplier.setPrimaryProduct(rs.getString("primary_product"));
        	supplier.setContactor(rs.getString("contactor"));
        	supplier.setPhoneNo(rs.getString("phone_no"));
            return supplier;  
        }  
          
    }  
	
	
	
}
