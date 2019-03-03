package com.cloudsky.material.dao;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.cloudsky.material.dto.ReportDataItem;
import com.cloudsky.material.dto.ReportStockInFilter;
import com.cloudsky.material.dto.ReportStockOutFilter;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.ReportPageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class ReportDao {

	public ReportPageData getStockInReportData(String projectId, ReportStockInFilter filter, int pageNo, int pageSize){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select DATE_FORMAT(b.apply_date,'%Y') year,DATE_FORMAT(b.apply_date,'%m') month,DATE_FORMAT(b.apply_date,'%d') day,");
		sql.append("c.value type, a.name,a.spec,a.unit,d.name supplierName,");
		sql.append("a.price,a.price-a.tax_price noTaxPrice,a.qty,a.amount,a.amount-a.tax_amount noTaxAmount,");
		sql.append("e.shipping_no shippingNo,e.creator,a.remark ");
		sql.append("from form_stock_in_detail a join form_stock_in b on a.form_id = b.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-in-type') c on b.type = c.key ");
		sql.append("left join supplier d on b.supplier_id = d.id ");
		sql.append("left join form_check e on b.form_check_id = e.id ");
		sql.append("where b.project_id=" + projectId + " and b.is_delete=0"); 
		
		if(filter.getCategoryId()!=null && !filter.getCategoryId().isEmpty()) {
			sql.append(" and b.category_id=" + filter.getCategoryId());
		}
		if(filter.getStartDate()!=null && !filter.getStartDate().isEmpty()) {
			sql.append(" and b.apply_date >= '" + filter.getStartDate() + "'");
		}
		if(filter.getEndDate()!=null && !filter.getEndDate().isEmpty()) {
			sql.append(" and b.apply_date <= '" + filter.getEndDate() + "'");
		}
		if(filter.getTypeId()!=null && !filter.getTypeId().isEmpty()) {
			sql.append(" and b.type = " + filter.getTypeId());
		}
		if(filter.getItemKeyword()!=null && !filter.getItemKeyword().isEmpty()) {
			sql.append(" and CONCAT(a.name,a.spec) like '%" + filter.getItemKeyword() +  "%'");
		}
		if(filter.getSupplierKeyword()!=null && !filter.getSupplierKeyword().isEmpty()) {
			sql.append(" and d.name like '%" + filter.getSupplierKeyword() +  "%'");
		}
		
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" order by b.apply_date");
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
        Integer totalRecords = DBUtil.QueryForInt(countSql);
		
        ReportPageData page = new ReportPageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
		
		return page;
    	
	}
	
	
	
	
    public ReportPageData getStockOutReportData(String projectId, ReportStockOutFilter filter, int pageNo, int pageSize){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select DATE_FORMAT(b.apply_date,'%Y') year,DATE_FORMAT(b.apply_date,'%m') month,DATE_FORMAT(b.apply_date,'%d') day,");
		sql.append("c.value type, a.name,a.spec,a.unit,d.name consumerName,");
		sql.append("a.price,a.price-a.tax_price noTaxPrice,a.qty,a.amount,a.amount-a.tax_amount noTaxAmount,");
		sql.append("b.creator,a.remark ");
		sql.append("from form_stock_out_detail a join form_stock_out b on a.form_id = b.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-out-type') c on b.type = c.key ");
		sql.append("left join consumer d on b.consumer_id = d.id ");
		sql.append("where b.project_id=" + projectId + " and b.is_delete=0"); 
		
		if(filter.getCategoryId()!=null && !filter.getCategoryId().isEmpty()) {
			sql.append(" and b.category_id=" + filter.getCategoryId());
		}
		if(filter.getStartDate()!=null && !filter.getStartDate().isEmpty()) {
			sql.append(" and b.apply_date >= '" + filter.getStartDate() + "'");
		}
		if(filter.getEndDate()!=null && !filter.getEndDate().isEmpty()) {
			sql.append(" and b.apply_date <= '" + filter.getEndDate() + "'");
		}
		if(filter.getTypeId()!=null && !filter.getTypeId().isEmpty()) {
			sql.append(" and b.type = " + filter.getTypeId());
		}
		if(filter.getItemKeyword()!=null && !filter.getItemKeyword().isEmpty()) {
			sql.append(" and CONCAT(a.name,a.spec) like '%" + filter.getItemKeyword() +  "%'");
		}
		if(filter.getConsumerKeyword()!=null && !filter.getConsumerKeyword().isEmpty()) {
			sql.append(" and d.name like '%" + filter.getConsumerKeyword() +  "%'");
		}
		
		
		String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" order by b.apply_date");
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
        Integer totalRecords = DBUtil.QueryForInt(countSql);
		
        ReportPageData page = new ReportPageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
		
		return page;
    	
	}
    
    
    public List<Map<String,Object>> getMonthSummaryReport(String projectId, String year, String month) {
    	
    	String sql = "call get_month_summary_report(" +projectId + ",'" + year + "','" + month + "',0,999999)";
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
    }
    
    public ReportPageData getMonthSummaryReport(String projectId, String year, String month, int pageNo, int pageSize) {
    	
    	int startIndex = (pageNo - 1) * pageSize;
    	String sql = "call get_month_summary_report(" +projectId  + ",'" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
    	List<Map<String,Object>> rows = DBUtil.Query(sql);

    	ReportPageData page = new ReportPageData();
    	page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
    	page.setRows(rows);
    	return page;
    }
    
    
    public ReportPageData getMonthSummaryReviseReport(String projectId, String year, String month, int pageNo, int pageSize) {

    	StringBuilder sql = new StringBuilder();
    	sql.append("select id,"); 
    	sql.append("category_id categoryId,");
    	sql.append("category_name categoryName,");
    	sql.append("last_month_qty lastMonthQty,");
    	sql.append("last_month_amount lastMonthAmount,");
    	sql.append("stock_in_qty_1 stockInQty1,");
    	sql.append("stock_in_qty_2 stockInQty2,");
    	sql.append("stock_in_qty_3 stockInQty3,");
    	sql.append("stock_in_qty_4 stockInQty4,");
    	sql.append("stock_in_qty_5 stockInQty5,");
    	sql.append("stock_in_amount_1 stockInAmount1,");
    	sql.append("stock_in_amount_2 stockInAmount2,");
    	sql.append("stock_in_amount_3 stockInAmount3,");
    	sql.append("stock_in_amount_4 stockInAmount4,");
    	sql.append("stock_in_amount_5 stockInAmount5,");
    	sql.append("stock_in_total_qty stockInTotalQty,");
    	sql.append("stock_in_total_amount stockInTotalAmount,");
 
    	sql.append("stock_out_qty_1 stockOutQty1,");
    	sql.append("stock_out_qty_2 stockOutQty2,");
    	sql.append("stock_out_amount_1 stockOutAmount1,");
    	sql.append("stock_out_amount_2 stockOutAmount2,");
    	sql.append("stock_out_total_qty stockOutTotalQty,");
    	sql.append("stock_out_total_amount stockOutTotalAmount");

    	sql.append(" from report_monthly_summary");
    	sql.append(" where project_id=" + projectId);
    	sql.append(" and year=" + year + " and month=" + month);
    	
    	String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
        Integer totalRecords = DBUtil.QueryForInt(countSql);
		
        ReportPageData page = new ReportPageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
    	
    	return page;
    }
    
     public List<Map<String,Object>> getMonthDetailReport(String projectId, String categoryId, String year, String month) {
    	
    	String sql = "call get_month_detail_report(" +projectId + "," + categoryId + ",'" + year + "','" + month + "',0,999999)";
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
    }
    
    public ReportPageData getMonthDetailReport(String projectId, String categoryId, String year, String month, int pageNo, int pageSize) {
    	
    	int startIndex = (pageNo - 1) * pageSize;
    	String sql = "call get_month_detail_report(" +projectId + "," + categoryId + ",'" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
    	List<Map<String,Object>> rows = DBUtil.Query(sql);

    	ReportPageData page = new ReportPageData();
    	page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
    	page.setRows(rows);
    	return page;
    }
    
    
    
    public ReportPageData getMonthDetailReviseReport(String projectId, String categoryId, String year, String month, int pageNo, int pageSize) {

    	StringBuilder sql = new StringBuilder();
    	sql.append("select id,name,spec,unit,"); 
    	
    	sql.append("ifnull(last_month_qty,0) lastMonthQty,");
    	sql.append("ifnull(last_month_amount,0) lastMonthAmount,");
    	sql.append("ifnull(stock_in_qty_1,0) stockInQty1,");
    	sql.append("ifnull(stock_in_qty_2,0) stockInQty2,");
    	sql.append("ifnull(stock_in_qty_3,0) stockInQty3,");
    	sql.append("ifnull(stock_in_qty_4,0) stockInQty4,");
    	sql.append("ifnull(stock_in_qty_5,0) stockInQty5,");
    	sql.append("ifnull(stock_in_amount_1,0) stockInAmount1,");
    	sql.append("ifnull(stock_in_amount_2,0) stockInAmount2,");
    	sql.append("ifnull(stock_in_amount_3,0) stockInAmount3,");
    	sql.append("ifnull(stock_in_amount_4,0) stockInAmount4,");
    	sql.append("ifnull(stock_in_amount_5,0) stockInAmount5,");
    	sql.append("ifnull(stock_in_total_qty,0) stockInTotalQty,");
    	sql.append("ifnull(stock_in_total_amount,0) stockInTotalAmount,");
 
    	sql.append("ifnull(stock_out_qty_1,0) stockOutQty1,");
    	sql.append("ifnull(stock_out_qty_2,0) stockOutQty2,");
    	sql.append("ifnull(stock_out_amount_1,0) stockOutAmount1,");
    	sql.append("ifnull(stock_out_amount_2,0) stockOutAmount2,");
    	sql.append("ifnull(stock_out_total_qty,0) stockOutTotalQty,");
    	sql.append("ifnull(stock_out_total_amount,0) stockOutTotalAmount");

    	sql.append(" from report_monthly_detail");
    	sql.append(" where project_id=" + projectId);
    	sql.append(" and year=" + year + " and month=" + month + " and category_id=" + categoryId);
    	
    	String countSql = SqlUtil.genCountSql(sql.toString());
		sql.append(" LIMIT  " + (pageNo - 1) * pageSize  + "," + pageSize);
		
    	List<Map<String,Object>> rows = DBUtil.Query(sql.toString());
        Integer totalRecords = DBUtil.QueryForInt(countSql);
		
        ReportPageData page = new ReportPageData();
		page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalRecords(totalRecords);
    	
    	return page;
    }
    
    
    public int getMonthSummaryReportRows(String projectId, String year, String month) {
    	String sql = "select count(*) counts from report_monthly_summary where project_id=? and year=? and month=?";
    	Object[] args ={projectId, year, month};    	
    	return DBUtil.QueryForInt(sql, args);
    }
    
    
    public int getMonthDetailReportRows(String projectId, String year, String month, String categoryId) {
    	String sql = "select count(*) counts from report_monthly_detail where project_id=? and year=? and month=? and category_id=?";
    	Object[] args ={projectId, year, month, categoryId};    	
    	return DBUtil.QueryForInt(sql, args);
    }
    
    
    public int genMonthSummaryReportData(String projectId, String year, String month) {
    	String sql = "call gen_monthly_summary_report_data (" +projectId +  ",'" + year + "','" + month + "')";
    	return DBUtil.update(sql);
    }
    
    
    public int genMonthDetailReportData(String projectId, String year, String month, String categoryId) {
    	String sql = "call gen_monthly_detail_report_data (" +projectId +  "," + categoryId + ",'" + year + "','" + month + "')";
    	return DBUtil.update(sql);
    }
    
    
    public boolean updateMonthSummaryReviseReport(List<ReportDataItem> rows) {
    	
    	StringBuilder sql = new StringBuilder();
    	for(ReportDataItem row : rows) {
    		sql.append("update report_monthly_summary set ");
    		sql.append("stock_in_qty_1=" + row.getStockInQty1() + ",");
    		sql.append("stock_in_qty_2=" + row.getStockInQty2() + ",");
    		sql.append("stock_in_qty_3=" + row.getStockInQty3() + ",");
    		sql.append("stock_in_qty_4=" + row.getStockInQty4() + ",");
    		sql.append("stock_in_qty_5=" + row.getStockInQty5() + ",");
    		sql.append("stock_in_amount_1=" + row.getStockInAmount1() + ",");
    		sql.append("stock_in_amount_2=" + row.getStockInAmount2() + ",");
    		sql.append("stock_in_amount_3=" + row.getStockInAmount3() + ",");
    		sql.append("stock_in_amount_4=" + row.getStockInAmount4() + ",");
    		sql.append("stock_in_amount_5=" + row.getStockInAmount5() + ",");
    		sql.append("stock_in_total_qty=" + row.getStockInTotalQty() + ",");
    		sql.append("stock_in_total_amount=" + row.getStockInTotalAmount() + ",");
    		
    		sql.append("stock_out_qty_1=" + row.getStockOutQty1() + ",");
    		sql.append("stock_out_qty_2=" + row.getStockOutQty2() + ",");
    		sql.append("stock_out_amount_1=" + row.getStockOutAmount1() + ",");
    		sql.append("stock_out_amount_2=" + row.getStockOutAmount2() + ",");
    		sql.append("stock_out_total_qty=" + row.getStockOutTotalQty() + ",");
    		sql.append("stock_out_total_amount=" + row.getStockOutTotalAmount());
    		sql.append(" where id=" + row.getId() + ";");
    	}
    	
    	
    	return DBUtil.batchUpdate(sql.toString().split(";"));
    }
    
    
    
    public boolean updateMonthDetailReviseReport(List<ReportDataItem> rows) {
    	
    	StringBuilder sql = new StringBuilder();
    	for(ReportDataItem row : rows) {
    		sql.append("update report_monthly_detail set ");
    		sql.append("stock_in_qty_1=" + row.getStockInQty1() + ",");
    		sql.append("stock_in_qty_2=" + row.getStockInQty2() + ",");
    		sql.append("stock_in_qty_3=" + row.getStockInQty3() + ",");
    		sql.append("stock_in_qty_4=" + row.getStockInQty4() + ",");
    		sql.append("stock_in_qty_5=" + row.getStockInQty5() + ",");
    		sql.append("stock_in_amount_1=" + row.getStockInAmount1() + ",");
    		sql.append("stock_in_amount_2=" + row.getStockInAmount2() + ",");
    		sql.append("stock_in_amount_3=" + row.getStockInAmount3() + ",");
    		sql.append("stock_in_amount_4=" + row.getStockInAmount4() + ",");
    		sql.append("stock_in_amount_5=" + row.getStockInAmount5() + ",");
    		sql.append("stock_in_total_qty=" + row.getStockInTotalQty() + ",");
    		sql.append("stock_in_total_amount=" + row.getStockInTotalAmount() + ",");
    		
    		sql.append("stock_out_qty_1=" + row.getStockOutQty1() + ",");
    		sql.append("stock_out_qty_2=" + row.getStockOutQty2() + ",");
    		sql.append("stock_out_amount_1=" + row.getStockOutAmount1() + ",");
    		sql.append("stock_out_amount_2=" + row.getStockOutAmount2() + ",");
    		sql.append("stock_out_total_qty=" + row.getStockOutTotalQty() + ",");
    		sql.append("stock_out_total_amount=" + row.getStockOutTotalAmount());
    		sql.append(" where id=" + row.getId() + ";");
    	}
    	
    	
    	return DBUtil.batchUpdate(sql.toString().split(";"));
    }
    	
    
	
}
