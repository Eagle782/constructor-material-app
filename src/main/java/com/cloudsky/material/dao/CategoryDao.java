package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.cloudsky.material.entity.Category;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class CategoryDao {

	public PageData getPage(int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select id,name from category");	
		
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
		String sql = "SELECT id,name from category";   	  	
    	List<Map<String,Object>> projects = DBUtil.Query(sql);
    	return projects;
	}

	
	public Map<String,Object> get(String id){
		String sql = "SELECT id,name from category where id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> projects = DBUtil.Query(sql,args);
    	if(projects.size()>0){
    		return projects.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
    public int getCounts(){
		
		String sql = "select count(*) from category";
		return DBUtil.QueryForInt(sql);
		
	}
    
    
    
    public int getCountsByName(String name){
		
		String sql = "select count(*) from category where name= ?";
		Object[] args={name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String name, String excludeId){
		
		String sql = "select count(*) from category where name= ? and id != ?";
		Object[] args={name, excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public int add(Category category){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into category (name)");	
		sql.append(" values (?)");
		
		Object[] args={
				category.getName()};

		return DBUtil.update(sql.toString(), args);
	}
	
	
	
	public int update(Category category){

		StringBuilder sql=new StringBuilder();		
		sql.append("update category set name = ? ");	
		sql.append("where id=?");
		
		Object[] args={
				category.getName(),
				category.getId()};

		
		return DBUtil.update(sql.toString(), args);
	}
	
	
	public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
		for(String id : ids) {				
			sql.append("delete from category where id= " + id + ";");
		}
  
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public PageData getInitQtyAndAmount(String projectId, int pageNo, int pageSize){
		
		StringBuilder sql=new StringBuilder();		
		sql.append("select a.id,a.name,ifnull(b.totalQty,0) initQty,ifnull(b.totalAmount,0) initAmount ");	 
		sql.append("from category a left join  ");
		sql.append("(select b.category_id,sum(a.init_qty) totalQty,sum(a.init_amount) totalAmount " );
		sql.append("from sku a join item b on a.item_id=b.id where b.project_id=" + projectId);
		sql.append(" group by b.category_id) b on a.id=b.category_id where a.project_id=" + projectId);
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		
		sql.append(" order by a.id");
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
	
}
