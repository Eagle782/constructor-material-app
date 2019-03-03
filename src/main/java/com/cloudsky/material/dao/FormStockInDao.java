package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.cloudsky.material.dto.FormStockInBo;
import com.cloudsky.material.dto.FormStockInFilter;
import com.cloudsky.material.entity.FormStockInItem;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.DateUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class FormStockInDao {

	public PageData getPage(String projectId, FormStockInFilter filter, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,b.name category,a.apply_date applyDate,c.name supplier,d.value type, a.creator from form_stock_in a left join category b on a.category_id=b.id ");	
		sql.append("left join supplier c on a.supplier_id=c.id ");
		sql.append("join (select `key`,`value` from dictionary where category='stock-in-type') d on a.type = d.key ");
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
		sql.append("select a.id,a.project_id,a.form_check_id,a.type,a.category_id,a.supplier_id,a.apply_date,a.creator,b.name categoryName,c.name supplierName,d.value typeName ");   
		sql.append("from form_stock_in a left join category b on a.category_id=b.id ");
		sql.append("left join supplier c on a.supplier_id=c.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-in-type') d on a.type = d.key  where a.id = ?");
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
		
		String sql = "select count(*) from form_stock_in_detail where item_id = ?";
		Object[] args={itemId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByCategoryId(String categoryId){
		
		String sql = "select count(*) from form_stock_in where category_id = ?";
		Object[] args={categoryId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public List<Map<String,Object>> getItemList(String id){
		String sql = "select item_id,name,spec,qty,unit,qty,price,amount,tax_price,tax_amount,remark from form_stock_in_detail where form_id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql,args);
    	return rows;
	}
	
	
	private String generateNextSeqNo() {
		
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		
		String sql = "select ifnull(max(seq_no),0)+1 nextNo from form_stock_in where id like 'I-" + year + month + "%'";
		String nextNo = DBUtil.queryFirstCell(sql, "nextNo");
		
		return nextNo;
	}
	
	
	
	public boolean add(FormStockInBo formBo){

		String seqNo = generateNextSeqNo();
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		String id = "I-" + year + month + "-" + seqNo;
		
		StringBuilder sql=new StringBuilder();		
		sql.append("insert into form_stock_in (id,seq_no,project_id,form_check_id,category_id,supplier_id,type,apply_date,creator,create_time) ");	
		sql.append("values ('" + id + "'," +seqNo +","+formBo.getProjectId() + ",");
		sql.append(SqlUtil.ifNull(formBo.getFormCheckId()) + ",");
		sql.append(formBo.getCategoryId() + ",");
		sql.append(formBo.getSupplierId() + ",");
		sql.append(formBo.getType()+ ",'" + formBo.getApplyDate() + "','");
		sql.append(formBo.getCreator() + "',now());");
		
        for(FormStockInItem item : formBo.getItems()) {
        	sql.append("insert into form_stock_in_detail (form_id,item_id,sku_id,name,spec,unit,qty,price,amount,tax_price,tax_amount,remark) values ('");
        	sql.append(id + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ","  +item.getTaxPrice() + ","  +item.getTaxAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
        

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean update(FormStockInBo formBo){
		
		
		StringBuilder sql=new StringBuilder();		
		sql.append("update form_stock_in set category_id=" + formBo.getCategoryId() + ",");
		sql.append("supplier_id=" + formBo.getSupplierId() + ",");
		sql.append("type=" + formBo.getType() + ",");
		sql.append("apply_date='" + formBo.getApplyDate() + "' ");
		sql.append("where id='" + formBo.getId() + "';");

		sql.append("delete from form_stock_in_detail where form_id='" + formBo.getId() + "';");
		
		for(FormStockInItem item : formBo.getItems()) {
        	sql.append("insert into form_stock_in_detail (form_id,item_id,sku_id,name,spec,unit,qty,price,amount,tax_price,tax_amount,remark) values ('");
        	sql.append(formBo.getId() + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ","  +item.getTaxPrice() + ","  +item.getTaxAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
    public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("update form_stock_in set is_delete=1 where id='" + id + "';");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    
    
    public List<Map<String,Object>> getSummayPerCategory(String projectId, String endDate, int pageNo, int pageSize){
    	StringBuilder sql=new StringBuilder();		
    	sql.append("select a.id,a.name,ifnull(b.totalQty,0) stockInQty,ifnull(b.amount,0) stockInAmount ");
    	sql.append("from category a left join ");
		sql.append("(select c.category_id,sum(a.qty) totalQty,sum(a.amount) amount ");
		sql.append("from form_stock_in_detail a join form_stock_in b on a.form_id=b.id ");
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
    	sql.append("ifnull(b.qty3,0) qty3,");
    	sql.append("ifnull(b.qty4,0) qty4,");
    	sql.append("ifnull(b.qty5,0) qty5,");
    	sql.append("ifnull(b.totalQty,0) totalQty,");
    	sql.append("ifnull(b.amount1,0) amount1,");
    	sql.append("ifnull(b.amount2,0) amount2,");
    	sql.append("ifnull(b.amount3,0) amount3,");
    	sql.append("ifnull(b.amount4,0) amount4,");
    	sql.append("ifnull(b.amount5,0) amount5,");
    	sql.append("ifnull(b.totalAmount,0) totalAmount ");
    	
    	sql.append("from category a left join ");
		sql.append("(select c.category_id,");
		
		sql.append("sum(if(b.type=1,a.qty,0)) qty1,");
		sql.append("sum(if(b.type=2,a.qty,0)) qty2,");
		sql.append("sum(if(b.type=3,a.qty,0)) qty3,");
		sql.append("sum(if(b.type=4,a.qty,0)) qty4,");
		sql.append("sum(if(b.type=5,a.qty,0)) qty5,");
		sql.append("sum(a.qty) totalQty,");
		sql.append("sum(if(b.type=1,a.amount,0)) amount1,");
		sql.append("sum(if(b.type=2,a.amount,0)) amount2,");
		sql.append("sum(if(b.type=3,a.amount,0)) amount3,");
		sql.append("sum(if(b.type=4,a.amount,0)) amount4,");
		sql.append("sum(if(b.type=5,a.amount,0)) amount5,");
		sql.append("sum(a.amount) totalAmount ");
		
		sql.append("from form_stock_in_detail a join form_stock_in b on a.form_id=b.id ");
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
