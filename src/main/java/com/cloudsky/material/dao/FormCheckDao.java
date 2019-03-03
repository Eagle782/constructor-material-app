package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.dto.FormCheckBo;
import com.cloudsky.material.dto.FormCheckFilter;
import com.cloudsky.material.entity.FormCheckItem;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.DateUtil;
import com.cloudsky.material.utility.ListUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class FormCheckDao {

	public PageData getPage(String projectId, FormCheckFilter filter, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,b.name category,a.check_date checkDate,c.name supplier,a.creator from form_check a left join category b on a.category_id=b.id ");	
		sql.append("left join supplier c on a.supplier_id=c.id ");
		sql.append("where a.project_id=" + projectId + " and a.is_delete=0 ");
		
		if(filter.getCategoryId()!=null && !filter.getCategoryId().isEmpty()) {
			sql.append(" and a.category_id = " + filter.getCategoryId());
		}
		if(filter.getStartDate()!=null && !filter.getStartDate().isEmpty()) {
			sql.append(" and a.check_date >= '" + filter.getStartDate() + "'");
		}
		if(filter.getEndDate()!=null && !filter.getEndDate().isEmpty()) {
			sql.append(" and a.check_date <= '" + filter.getEndDate() + "'");
		}
		if(filter.getKeyword()!=null && !filter.getKeyword().isEmpty()) {
			sql.append(" and (a.id like '%" + filter.getKeyword() + "%' or a.creator like '%" + filter.getKeyword() + "%' or c.name like '%" + filter.getKeyword() + "%')");
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
		sql.append("select a.id,a.project_id,a.category_id,a.supplier_id,a.check_date,a.shipping_no,a.attach_files,a.remark,a.creator,b.name categoryName,c.name supplierName ");   
		sql.append("from form_check a left join category b on a.category_id=b.id ");
		sql.append("left join supplier c on a.supplier_id=c.id where a.id = ?");
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString(),args);
    	if(rows.size()>0){
    		return rows.get(0); 
    	}
    	else{
    		return null;
    	}
	}
	
	
	
	public List<Map<String,Object>> getItemList(String id){
		
		StringBuilder sql=new StringBuilder();		
		sql.append("select item_id,name,spec,qty,unit,plan_qty,qty,price,amount,remark,");   
		sql.append("extend_1,extend_2,extend_3,extend_4,extend_5,extend_6,extend_7,extend_8,extend_9,extend_10,");
    	sql.append("extend_11,extend_12,extend_13,extend_14,extend_15,extend_16,extend_17,extend_18,extend_19,extend_20 ");
    	sql.append("from form_check_detail where form_id = ?");

    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString(),args);
    	return rows;
	}
	
	
    public int getCountsByProject(String projectId){
		
		String sql = "select count(*) from form_check where project_id = ? and is_delete=0";
		Object[] args={projectId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    
    public int getCountsByItemId(String itemId){
		
		String sql = "select count(*) from form_check_detail where item_id = ?";
		Object[] args={itemId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByCategoryId(String categoryId){
		
		String sql = "select count(*) from form_check where category_id = ?";
		Object[] args={categoryId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	private String generateNextSeqNo() {
		
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		
		String sql = "select ifnull(max(seq_no),0)+1 nextNo from form_check where id like 'V-" + year + month + "%'";
		String nextNo = DBUtil.queryFirstCell(sql, "nextNo");

		
		return nextNo;
	}
	
	
	
	public boolean add(FormCheckBo formCheck){

		String seqNo = generateNextSeqNo();
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		String id = "V-" + year + month + "-" + seqNo;
		String attachFiles = ListUtil.toString(formCheck.getAttachFiles());
		
		StringBuilder sql=new StringBuilder();		
		sql.append("insert into form_check (id,seq_no,project_id,category_id,supplier_id,check_date,shipping_no,remark,attach_files,creator,create_time) ");	
		sql.append("values ('" + id + "'," +seqNo+ "," +formCheck.getProjectId() + "," + formCheck.getCategoryId() + "," + formCheck.getSupplierId() + ",'");
		sql.append(formCheck.getCheckDate() + "','" + SqlUtil.DeInjection(formCheck.getShippingNo()) + "','");
		sql.append(SqlUtil.DeInjection(formCheck.getRemark()) + "','" + attachFiles + "','" + formCheck.getCreator() + "',now());");
		
        for(FormCheckItem item : formCheck.getItems()) {
        	sql.append("insert into form_check_detail (form_id,item_id,sku_id,name,spec,unit,plan_qty,qty,price,amount,remark,");
        	sql.append("extend_1,extend_2,extend_3,extend_4,extend_5,extend_6,extend_7,extend_8,extend_9,extend_10,");
        	sql.append("extend_11,extend_12,extend_13,extend_14,extend_15,extend_16,extend_17,extend_18,extend_19,extend_20) values ('");
        	sql.append(id + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getPlanQty() + "," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "',");
        	
        	sql.append(SqlUtil.ifNull(item.getExtend1()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend2()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend3()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend4()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend5()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend6()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend7()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend8()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend9()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend10()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend11()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend12()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend13()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend14()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend15()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend16()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend17()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend18()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend19()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend20()) + ");");

        }
        

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean update(FormCheckBo formCheck){
		
		String attachFiles = ListUtil.toString(formCheck.getAttachFiles());
		
		StringBuilder sql=new StringBuilder();		
		sql.append("update form_check set category_id=" + formCheck.getCategoryId() + ",");
		sql.append("supplier_id=" + formCheck.getSupplierId() + ",");
		sql.append("check_date='" + formCheck.getCheckDate() + "',");
		sql.append("shipping_no='" + SqlUtil.DeInjection(formCheck.getShippingNo()) + "',");
		sql.append("remark='" + SqlUtil.DeInjection(formCheck.getRemark()) + "',");
		sql.append("attach_files='" + attachFiles + "' where id='" + formCheck.getId() + "';");

		sql.append("delete from form_check_detail where form_id='" + formCheck.getId() + "';");
		
		for(FormCheckItem item : formCheck.getItems()) {
        	sql.append("insert into form_check_detail (form_id,item_id,sku_id,name,spec,unit,plan_qty,qty,price,amount,remark,");
        	sql.append("extend_1,extend_2,extend_3,extend_4,extend_5,extend_6,extend_7,extend_8,extend_9,extend_10,");
        	sql.append("extend_11,extend_12,extend_13,extend_14,extend_15,extend_16,extend_17,extend_18,extend_19,extend_20) values ('");
        	sql.append(formCheck.getId() + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "','" + item.getUnit() + "'," + item.getPlanQty() + "," + item.getQty() + ",");
        	sql.append(item.getPrice() + "," +item.getAmount() + ",'" + SqlUtil.DeInjection(item.getRemark()) + "',");
        	
        	sql.append(SqlUtil.ifNull(item.getExtend1()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend2()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend3()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend4()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend5()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend6()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend7()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend8()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend9()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend10()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend11()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend12()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend13()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend14()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend15()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend16()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend17()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend18()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend19()) + ",");
        	sql.append(SqlUtil.ifNull(item.getExtend20()) + ");");
        }
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
    public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("update form_check set is_delete=1 where id='" + id + "';");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
    

}
