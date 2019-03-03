package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.dto.FormPlanBo;
import com.cloudsky.material.dto.FormPlanFilter;
import com.cloudsky.material.entity.FormPlanItem;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.DateUtil;
import com.cloudsky.material.utility.ListUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class FormPlanDao {

	public PageData getPage(String projectId, FormPlanFilter filter, int pageNo, int pageSize){

		StringBuilder sql = new StringBuilder();
		sql.append("select a.id,b.name category,a.plan_date planDate,a.creator,a.serial_no serialNo from form_plan a left join category b on a.category_id=b.id ");	
		sql.append("where a.project_id=" + projectId + " and a.is_delete=0 ");
		
		if(filter.getCategoryId()!=null && !filter.getCategoryId().isEmpty()) {
			sql.append(" and a.category_id = " + filter.getCategoryId());
		}
		if(filter.getStartDate()!=null && !filter.getStartDate().isEmpty()) {
			sql.append(" and a.plan_date >= '" + filter.getStartDate() + "'");
		}
		if(filter.getEndDate()!=null && !filter.getEndDate().isEmpty()) {
			sql.append(" and a.plan_date <= '" + filter.getEndDate() + "'");
		}
		if(filter.getKeyword()!=null && !filter.getKeyword().isEmpty()) {
			sql.append(" and (a.id like '%" + filter.getKeyword() + "%' or a.creator like '%" + filter.getKeyword() + "%')");
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
		sql.append("select a.id,a.project_id,a.category_id,a.plan_date,a.serial_no,a.attach_files,a.creator,b.name categoryName ");   
		sql.append("from form_plan a left join category b on a.category_id=b.id where a.id = ?");
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
		
		String sql = "select count(*) from form_plan_detail where item_id = ?";
		Object[] args={itemId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
    
    
    public int getCountsByCategoryId(String categoryId){
		
		String sql = "select count(*) from form_plan where category_id = ?";
		Object[] args={categoryId};
		
		return DBUtil.QueryForInt(sql, args);
		
	}
	
	
	
	public List<Map<String,Object>> getItemList(String id){
		String sql = "select item_id,name,spec,qty,unit,plan_arrival_date,`usage`,remark from form_plan_detail where form_id = ?";   	
    	Object[] args ={id};    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql,args);
    	return rows;
	}
	
	
	private String generateNextSeqNo() {
		
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		
		String sql = "select ifnull(max(seq_no),0)+1 nextNo from form_plan where id like 'P-" + year + month + "%'";
		String nextNo = DBUtil.queryFirstCell(sql, "nextNo");
		
		
		
		return nextNo;
	}
	
	
	
	public boolean add(FormPlanBo formPlan){

		String seqNo = generateNextSeqNo();
		String year = DateUtil.getToday().split("-")[0];
		String month = DateUtil.getToday().split("-")[1];
		String id = "P-" + year + month + "-" + seqNo;
		String attachFiles = ListUtil.toString(formPlan.getAttachFiles());
		
		StringBuilder sql=new StringBuilder();		
		sql.append("insert into form_plan (id,seq_no,project_id,category_id,plan_date,serial_no,attach_files,creator,create_time) ");	
		sql.append("values ('" + id + "',"+seqNo+"," +formPlan.getProjectId() + "," + formPlan.getCategoryId() + ",'");
		sql.append(formPlan.getPlanDate() + "','" + SqlUtil.DeInjection(formPlan.getSerialNo()) + "','");
		sql.append(attachFiles + "','" + formPlan.getCreator() + "',now());");
		
        for(FormPlanItem item : formPlan.getItems()) {
        	sql.append("insert into form_plan_detail (form_id,item_id,sku_id,name,spec,qty,unit,plan_arrival_date,`usage`,remark) values ('");
        	sql.append(id + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "'," + item.getQty() + ",'" + item.getUnit() + "','" + item.getPlanArrivalDate() + "','");
        	sql.append(SqlUtil.DeInjection(item.getUsage()) + "','" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
        

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	public boolean update(FormPlanBo formPlan){
		
		String attachFiles = ListUtil.toString(formPlan.getAttachFiles());
		
		StringBuilder sql=new StringBuilder();		
		sql.append("update form_plan set category_id=" + formPlan.getCategoryId() + ",");
		sql.append("plan_date='" + formPlan.getPlanDate() + "',");
		sql.append("serial_no='" + formPlan.getSerialNo() + "',");
		sql.append("attach_files='" + attachFiles + "' where id='" + formPlan.getId() + "';");

		sql.append("delete from form_plan_detail where form_id='" + formPlan.getId() + "';");
		
		for(FormPlanItem item : formPlan.getItems()) {
        	sql.append("insert into form_plan_detail (form_id,item_id,sku_id,name,spec,qty,unit,plan_arrival_date,`usage`,remark) values ('");
        	sql.append(formPlan.getId() + "'," + item.getItemId() + "," + item.getSpecId() + ",'" + SqlUtil.DeInjection(item.getName()) + "','");
        	sql.append(SqlUtil.DeInjection(item.getSpec()) + "'," + item.getQty() + ",'" + item.getUnit() + "','" + item.getPlanArrivalDate() + "','");
        	sql.append(SqlUtil.DeInjection(item.getUsage()) + "','" + SqlUtil.DeInjection(item.getRemark()) + "');");
        }
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
	
    public boolean delete(List<String> ids){
		
		StringBuilder sql=new StringBuilder();	
        for(String id : ids) {
        	sql.append("update form_plan set is_delete=1 where id='" + id + "';");
		}
		

		return DBUtil.batchUpdate(sql.toString().split(";"));
	}
	
	
}
