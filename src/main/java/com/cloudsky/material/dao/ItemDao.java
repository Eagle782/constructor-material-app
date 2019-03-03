package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.cloudsky.material.dto.ItemBo;
import com.cloudsky.material.entity.Sku;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class ItemDao {
	
	public PageData getPage(String categoryId, String keyword, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,a.name,a.unit,b.name category from item a left join category b on a.category_id = b.id where 1=1");	
		
		if(!categoryId.isEmpty()) {
			sql.append(" and a.category_id = " + categoryId );
		}
		if(!keyword.isEmpty()) {
			sql.append(" and a.name like '%" + keyword + "%'" );
		}
		
		sql.append(" order by a.id desc");
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
		System.out.println(sql.toString());
		
		List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
		Integer totalRecords = DBUtil.QueryForInt(countSql);
		
		PageData page = new PageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
		
		return page;
	}
	
	
	public List<Map<String,Object>> query(String categoryId, String keyword){
		StringBuilder sql = new StringBuilder();
		sql.append("select id,name,unit from item where category_id=" + categoryId);
		sql.append(" and (code like '%" + keyword + "%' or name like '%" + keyword + "%')");
		List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
		return rows;
	}
	
	
	 public Map<String,Object> get(String id){
			String sql = "SELECT id,category_id,code,name,unit from item where id = ?";   	
	    	Object[] args ={id};    	
	    	List<Map<String,Object>> rows = DBUtil.Query(sql,args);
	    	if(rows.size()>0){
	    		return rows.get(0); 
	    	}
	    	else{
	    		return null;
	    	}
	}

    public int getCountsByCategoryId(String categoryId){
		
		String sql = "select count(*) from item where category_id= ?";
		Object[] args={categoryId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByName(String name){
		
		String sql = "select count(*) from item where name= ?";
		Object[] args={name};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByNameAndExcludeId(String name, String excludeId){
		
		String sql = "select count(*) from item where name= ? and id != ?";
		Object[] args={name, excludeId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    
    
    public boolean add(ItemBo item){

		StringBuilder sql=new StringBuilder();		
		sql.append("insert into item (category_id,code,name,unit) values (");	
		sql.append(item.getCategoryId() + ",'" + SqlUtil.DeInjection(item.getCode()) + "','" + SqlUtil.DeInjection(item.getName()) + "','" + item.getUnit() + "');");
		sql.append("set @item_id=@@IDENTITY;");
		
		for(Sku sku : item.getSkus()) {
			sql.append("insert into sku (item_id,spec) values (@item_id,'" + SqlUtil.DeInjection(sku.getSpec()) + "');");
		}
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    
    
    
    public boolean update(ItemBo item){

		StringBuilder sql=new StringBuilder();		
		sql.append("update item set category_id = " + item.getCategoryId() + ",code='" + SqlUtil.DeInjection(item.getCode()) + "',");	
		sql.append("name='" + item.getName() + "',unit='" + item.getUnit()+"'");
		sql.append(" where id=" + item.getId() + ";");
		
		sql.append("delete from sku where item_id=" + item.getId() + ";");
		for(Sku sku : item.getSkus()) {
			sql.append("insert into sku (item_id,spec) values (" + item.getId() + ",'" + SqlUtil.DeInjection(sku.getSpec()) + "');");
        	
        }
	
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    
    
    
    public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("delete from sku where item_id=" + id + ";");
    		sql.append("delete from item where id=" + id + ";");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}

    
}
