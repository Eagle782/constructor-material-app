package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.cloudsky.material.dto.TopReportStockInFilter;
import com.cloudsky.material.dto.TopReportStockOutFilter;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.ReportPageData;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class TopReportDao {

public ReportPageData getStockInReportData(TopReportStockInFilter filter, int pageNo, int pageSize){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select f.name projectName,DATE_FORMAT(b.apply_date,'%Y') year,DATE_FORMAT(b.apply_date,'%m') month,DATE_FORMAT(b.apply_date,'%d') day,");
		sql.append("c.value type, a.name,a.spec,a.unit,d.name supplierName,");
		sql.append("a.price,a.price-a.tax_price noTaxPrice,a.qty,a.amount,a.amount-a.tax_amount noTaxAmount,");
		sql.append("e.shipping_no shippingNo,e.creator,a.remark ");
		sql.append("from form_stock_in_detail a join form_stock_in b on a.form_id = b.id ");		
		sql.append("left join (select `key`,`value` from dictionary where category='stock-in-type') c on b.type = c.key ");
		sql.append("left join supplier d on b.supplier_id = d.id ");
		sql.append("left join form_check e on b.form_check_id = e.id ");
		sql.append("join project f on b.project_id = f.id ");
		sql.append("where b.is_delete=0"); 
		
		if(filter.getProjectIds()!=null && !filter.getProjectIds().isEmpty()) {
			sql.append(" and b.project_id in (" + filter.getProjectIds() + ")");
		}
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
	
	
	
	
    public ReportPageData getStockOutReportData(TopReportStockOutFilter filter, int pageNo, int pageSize){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select e.name projectName,DATE_FORMAT(b.apply_date,'%Y') year,DATE_FORMAT(b.apply_date,'%m') month,DATE_FORMAT(b.apply_date,'%d') day,");
		sql.append("c.value type, a.name,a.spec,a.unit,d.name consumerName,");
		sql.append("a.price,a.price-a.tax_price noTaxPrice,a.qty,a.amount,a.amount-a.tax_amount noTaxAmount,");
		sql.append("b.creator,a.remark ");
		sql.append("from form_stock_out_detail a join form_stock_out b on a.form_id = b.id ");
		sql.append("left join (select `key`,`value` from dictionary where category='stock-out-type') c on b.type = c.key ");
		sql.append("left join consumer d on b.consumer_id = d.id ");
		sql.append("join project e on b.project_id = e.id ");
		sql.append("where b.is_delete=0"); 
		
		if(filter.getProjectIds()!=null && !filter.getProjectIds().isEmpty()) {
			sql.append(" and b.project_id in (" + filter.getProjectIds() + ")");
		}
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
    
    
    public List<Map<String,Object>> getMonthSummaryReport(String projectIds, String year, String month) {
    	
    	String sql = "";
    	if(projectIds.isEmpty()) {
    		sql = "call get_month_summary_top_report('" + year + "','" + month + "',0,999999)";
    	}
    	else {
    		sql = "call get_month_summary_top_report_v2('" + projectIds + "','" + year + "','" + month + "',0,999999)";
    	}
    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
    }
    
    public ReportPageData getMonthSummaryReport(String projectIds, String year, String month, int pageNo, int pageSize) {
    	
    	int startIndex = (pageNo - 1) * pageSize;
    	String sql = "";
    	if(projectIds.isEmpty()) {
    		sql = "call get_month_summary_top_report('" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
    	}
    	else {
    		sql = "call get_month_summary_top_report_v2('" + projectIds + "','" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
    	}
    	
    	List<Map<String,Object>> rows = DBUtil.Query(sql);

    	ReportPageData page = new ReportPageData();
    	page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
    	page.setRows(rows);
    	return page;
    }
    
    
    
    
     public List<Map<String,Object>> getMonthDetailReport(String projectIds, String categoryId, String year, String month) {
    	
    	String sql = "";
     	if(projectIds.isEmpty()) {
     		sql = "call get_month_detail_top_report('" + projectIds + "'," + categoryId + ",'" + year + "','" + month + "',0,999999)";
     	}
     	else {
     		sql = "call get_month_detail_top_report_v2('" + projectIds + "'," + categoryId + ",'" + year + "','" + month + "',0,999999)";
     	}
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
    }
    
    public ReportPageData getMonthDetailReport(String projectIds, String categoryId, String year, String month, int pageNo, int pageSize) {
    	
    	int startIndex = (pageNo - 1) * pageSize;
    	String sql = "";
     	if(projectIds.isEmpty()) {
     		sql = "call get_month_detail_top_report('" + projectIds + "'," + categoryId + ",'" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
     	}
     	else {
     		sql = "call get_month_detail_top_report_v2('" + projectIds + "'," + categoryId + ",'" + year + "','" + month + "'," + startIndex + "," + pageSize + ")";
     	}
     	
     	
    	List<Map<String,Object>> rows = DBUtil.Query(sql);

    	ReportPageData page = new ReportPageData();
    	page.setRows(rows);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
    	page.setRows(rows);
    	return page;
    }
    
    
    
    
}
