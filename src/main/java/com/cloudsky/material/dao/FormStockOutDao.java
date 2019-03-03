package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.cloudsky.material.dto.FormStockOutBo;
import com.cloudsky.material.dto.FormStockOutFilter;
import com.cloudsky.material.entity.FormStockOutItem;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.DateUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class FormStockOutDao {

	public PageData getPage(String projectId, FormStockOutFilter filter, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,b.name category,a.apply_date applyDate,c.name consumer,d.value type, a.creator from form_stock_out a left join category b on a.category_id=b.id ");	
		sql.append("left join consumer c on a.consumer_id=c.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-out-type') d on a.type = d.key ");
		sql.append("where a.project_id=" + projectId + " and a.is_delete=0 ");
		
		if(filter.getCategoryId()!=null && !filter.getCategoryId().isEmpty()) {
			sql.append(" and a.category_id = " + filter.getCategoryId());
		}
		if(filter.getTypeId()!=null && !filter.getTypeId().isEmpty()) {
			sql.append(" and a.type = " + filter.getTypeId());
		}
		if(filter.getStartDate()!=null && !filter.getStartDate().isEmpty()) {
			sql.append(" and a.apply_date >= '" + filter.getStartDate() + "'");
		}
		if(filter.getEndDate()!=null && !filter.getEndDate().isEmpty()) {
			sql.append(" and a.apply_date <= '" + filter.getEndDate() + "'");
		}
		if(filter.getKeyword()!=null && !filter.getKeyword().isEmpty()) {
			sql.append(" and (a.id like '%" + filter.getKeyword() +  "%' or c.name like '%" + filter.getKeyword() + "%')");
		}
		
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		
		sql.append(" order by a.create_time desc");
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
		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,a.project_id,a.type,a.category_id,a.consumer_id,a.apply_date,a.remark,a.creator,b.name categoryName,c.name consumerName,d.value typeName ");  
		sql.append("from form_stock_out a left join category b on a.category_id=b.id ");
		sql.append("left join consumer c on a.consumer_id=c.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-out-type') d on a.type = d.key  where a.id = ?");
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString(),args);
    	
    	if(rows.size()>0){
    		return rows.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
	
    public int getCountsByItemId(String itemId){
		
		String sql = "select count(*) from form_stock_out_detail where item_id = ?";
		Object[] args={itemId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByCategoryId(String categoryId){
		
		String sql = "select count(*) from form_stock_out where category_id = ?";
		Object[] args={categoryId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public List<Map<String,Object>> getItemList(String id){
		String sql = "select item_id,name,spec,qty,unit,qty,price,amount,tax_price,tax_amount,remark from form_stock_out_detail where form_id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql,args);
    	return rows;
	}
	
	
	private String generateNextSeqNo() {
		
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		
		String sql = "select ifnull(max(seq_no),0)+1 nextNo from form_stock_out where id like 'O-" + year + month + "%'";
		String nextNo = DBUtil.queryFirstCell(sql, "nextNo");
		
		return nextNo;
	}
	
	
	
	public boolean add(FormStockOutBo formBo){

		String seqNo = generateNextSeqNo();
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		String id = "O-" + year + month + "-" + seqNo;
		
		StringBuilder sql=new StringBuilder();		
		sql.append("insert into form_stock_out (id,seq_no,project_id,category_id,consumer_id,type,apply_date,remark,creator,create_time) ");	
		sql.append("values ('" + id + "'," + seqNo +"," +formBo.getProjectId() + ",");
		sql.append(formBo.getCategoryId() + ",");
		sql.append(formBo.getConsumerId() + ",");
		sql.append(formBo.getType()+ ",'" + formBo.getApplyDate() + "','");
		sql.append(SqlUtil.DeInjection(formBo.getRemark()) + "','");
		sql.append(formBo.getCreator() + "',now());");
		
        for(FormStockOutItem item : formBo.getItems()) {
        	sql.append("insert into form_stock_out_detail (form_id,item_id,sku_id,name,spec,unit,qty,price,amount,tax_price,tax_amount,remark) values ('");
        	sql.append(id + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ","  +item.getTaxPrice() + ","  +item.getTaxAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
        

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean update(FormStockOutBo formBo){
		
		
		StringBuilder sql=new StringBuilder();		
		sql.append("update form_stock_out set category_id=" + formBo.getCategoryId() + ",");
		sql.append("consumer_id=" + formBo.getConsumerId() + ",");
		sql.append("type=" + formBo.getType() + ",");
		sql.append("apply_date='" + formBo.getApplyDate() + "',");
		sql.append("remark='" + SqlUtil.DeInjection(formBo.getRemark()) + "' ");
		sql.append("where id='" + formBo.getId() + "';");

		sql.append("delete from form_stock_out_detail where form_id='" + formBo.getId() + "';");
		
		for(FormStockOutItem item : formBo.getItems()) {
        	sql.append("insert into form_stock_out_detail (form_id,item_id,sku_id,name,spec,unit,qty,price,amount,tax_price,tax_amount,remark) values ('");
        	sql.append(formBo.getId() + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ","  +item.getTaxPrice() + ","  +item.getTaxAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
    public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("update form_stock_out set is_delete=1 where id='" + id + "';");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    
    
    
    public List<Map<String,Object>> getSummayPerCategory(String projectId, String endDate, int pageNo, int pageSize){
    	StringBuilder sql=new StringBuilder();		
    	sql.append("select a.id,a.name,ifnull(b.qty,0) stockOutQty,ifnull(b.amount,0) stockOutAmount ");
    	sql.append("from category a left join ");
		sql.append("(select c.category_id,sum(a.qty) qty,sum(a.amount) amount ");
		sql.append("from form_stock_out_detail a join form_stock_out b on a.form_id=b.id ");
		sql.append("join item c on a.item_id = c.id ");
		sql.append("where b.project_id = ? and b.is_delete=0 and b.apply_date<=? ");
		sql.append("group by c.category_id) b on a.id=b.category_id where a.project_id=? order by a.id");
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
		Object[] args ={projectId,
				endDate,
				projectId};    	
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString(),args);
    	return rows;
    }
    
    
    
public List<Map<String,Object>> getSummayPerCategory(String projectId, String year, String month, int pageNo, int pageSize){
    	
    	StringBuilder sql=new StringBuilder();		
    	sql.append("select a.id,a.name,");
    	
    	sql.append("ifnull(b.qty1,0) qty1,");
    	sql.append("ifnull(b.qty2,0) qty2,");
    	sql.append("ifnull(b.totalQty,0) totalQty,");
    	sql.append("ifnull(b.amount1,0) amount1,");
    	sql.append("ifnull(b.amount2,0) amount2,");
    	sql.append("ifnull(b.totalAmount,0) totalAmount ");
    	
    	sql.append("from category a left join ");
		sql.append("(select c.category_id,");
		
		sql.append("sum(if(b.type=1,a.qty,0)) qty1,");
		sql.append("sum(if(b.type=2,a.qty,0)) qty2,");
		sql.append("sum(a.qty) totalQty,");
		
		sql.append("sum(if(b.type=1,a.amount,0)) amount1,");
		sql.append("sum(if(b.type=2,a.amount,0)) amount2, ");
		sql.append("sum(a.amount) totalAmount ");
		
		sql.append("from form_stock_out_detail a join form_stock_out b on a.form_id=b.id ");
		sql.append("join item c on a.item_id = c.id ");
		sql.append("where b.project_id = ? and b.is_delete=0 ");
		sql.append("and DATE_FORMAT(b.apply_date,'%Y')=? and DATE_FORMAT(b.apply_date,'%m')=? ");
		sql.append("group by c.category_id) b on a.id=b.category_id where a.project_id=? order by a.id");
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
		String newMonth = "0"+month;
		newMonth = newMonth.substring(newMonth.length()-2, newMonth.length());
		
		Object[] args ={projectId,
				year,
				newMonth,
				projectId};    	
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString(),args);
    	return rows;
    }
    
    
}
