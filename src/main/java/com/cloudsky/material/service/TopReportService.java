package com.cloudsky.material.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.TopReportDao;
import com.cloudsky.material.dto.ReportDataItem;
import com.cloudsky.material.dto.TopReportStockInFilter;
import com.cloudsky.material.dto.TopReportStockOutFilter;
import com.cloudsky.material.utility.ReportPageData;

@Service
public class TopReportService {

	@Autowired
	private TopReportDao reportDao;
	
	
	
	
	public ReportPageData getStockInReportData(TopReportStockInFilter filter, int pageNo, int pageSize){
		ReportPageData data = reportDao.getStockInReportData(filter, pageNo, pageSize);
		Map<String,Object>  summary = new HashMap<String,Object>();
		
		Double qty = 0.00;
		Double amount = 0.00;
		Double totalNoTaxAmount = 0.00;
		
		for(int i=0; i<data.getRows().size(); i++) {
			qty += Double.parseDouble(data.getRows().get(i).get("qty").toString());
			amount += Double.parseDouble(data.getRows().get(i).get("amount").toString());
			totalNoTaxAmount += Double.parseDouble(data.getRows().get(i).get("noTaxAmount").toString());
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		summary.put("qty", df.format(qty));
		summary.put("amount", df.format(amount));
		summary.put("totalNoTaxAmount", df.format(totalNoTaxAmount));
		data.setSummary(summary);
		
		return data;
	}
	
	
	
	public ReportPageData getStockOutReportData(TopReportStockOutFilter filter, int pageNo, int pageSize){
		ReportPageData data = reportDao.getStockOutReportData(filter, pageNo, pageSize);
		Map<String,Object>  summary = new HashMap<String,Object>();
		
		Double qty = 0.00;
		Double amount = 0.00;
		Double totalNoTaxAmount = 0.00;
		
		for(int i=0; i<data.getRows().size(); i++) {
			qty += Double.parseDouble(data.getRows().get(i).get("qty").toString());
			amount += Double.parseDouble(data.getRows().get(i).get("amount").toString());
			totalNoTaxAmount += Double.parseDouble(data.getRows().get(i).get("noTaxAmount").toString());
		}
		
		DecimalFormat df = new DecimalFormat("0.00");
		summary.put("qty", df.format(qty));
		summary.put("amount", df.format(amount));
		summary.put("totalNoTaxAmount", df.format(totalNoTaxAmount));
		data.setSummary(summary);
		
		return data;
	}
	
	
	
public ReportPageData getMonthSummaryReport(String projectIds, String year, String month, int pageNo, int pageSize) {
		
        ReportPageData data = reportDao.getMonthSummaryReport(projectIds, year, month, pageNo, pageSize);
		
		Map<String,Object> summary = new HashMap<String,Object>();
		DecimalFormat df = new DecimalFormat("0.00");
		
		Double initQty = 0.00;
		Double initAmount = 0.00;
		Double pastInQty = 0.00;
		Double pastInAmount = 0.00;
		Double pastOutQty = 0.00;
		Double pastOutAmount = 0.00;
		
		
		Double curInQty = 0.00;
		Double curInAmount = 0.00;
		Double curOutQty = 0.00;
		Double curOutAmount = 0.00;
		
		Double stockInSubTotal1 = 0.00;
		Double stockInSubTotal2 = 0.00;
		Double stockInSubTotal3 = 0.00;
		Double stockInSubTotal4 = 0.00;
		Double stockInSubTotal5 = 0.00;
		Double stockInTotal = 0.00;
		Double stockOutSubTotal1 = 0.00;
		Double stockOutSubTotal2 = 0.00;
		Double stockOutTotal = 0.00;
		

    	for(int i=0; i<data.getRows().size(); i++) {
    		
    		initQty = Double.parseDouble(data.getRows().get(i).get("init_qty").toString());
			initAmount = Double.parseDouble(data.getRows().get(i).get("init_amount").toString());
			pastInQty = Double.parseDouble(data.getRows().get(i).get("init_in_qty").toString());
			pastInAmount = Double.parseDouble(data.getRows().get(i).get("init_in_amount").toString());
			pastOutQty = Double.parseDouble(data.getRows().get(i).get("init_out_qty").toString());
			pastOutAmount = Double.parseDouble(data.getRows().get(i).get("init_out_amount").toString());
			
			data.getRows().get(i).put("lastMonthQty", df.format(initQty + pastInQty - pastOutQty));
			data.getRows().get(i).put("lastMonthAmount", df.format(initAmount + pastInAmount - pastOutAmount));
			data.getRows().get(i).remove("init_qty");
			data.getRows().get(i).remove("init_amount");
			data.getRows().get(i).remove("init_in_qty");
			data.getRows().get(i).remove("init_in_amount");
			data.getRows().get(i).remove("init_out_qty");
			data.getRows().get(i).remove("init_out_amount");
			
			
			curInQty = Double.parseDouble(data.getRows().get(i).get("stockInTotalQty").toString());
			curInAmount = Double.parseDouble(data.getRows().get(i).get("stockInTotalAmount").toString());
			curOutQty = Double.parseDouble(data.getRows().get(i).get("stockOutTotalQty").toString());
			curOutAmount = Double.parseDouble(data.getRows().get(i).get("stockOutTotalAmount").toString());
			data.getRows().get(i).put("curMonthQty",df.format(initQty + pastInQty - pastOutQty + curInQty - curOutQty));
			data.getRows().get(i).put("curMonthAmount",df.format(initAmount + pastInAmount - pastOutAmount + curInAmount - curOutAmount));
			
			
			stockInSubTotal1 += Double.parseDouble(data.getRows().get(i).get("stockInAmount1").toString());
			stockInSubTotal2 += Double.parseDouble(data.getRows().get(i).get("stockInAmount2").toString());
			stockInSubTotal3 += Double.parseDouble(data.getRows().get(i).get("stockInAmount3").toString());
			stockInSubTotal4 += Double.parseDouble(data.getRows().get(i).get("stockInAmount4").toString());
			stockInSubTotal5 += Double.parseDouble(data.getRows().get(i).get("stockInAmount5").toString());			
			stockInTotal += Double.parseDouble(data.getRows().get(i).get("stockInTotalAmount").toString());
			
			stockOutSubTotal1 += Double.parseDouble(data.getRows().get(i).get("stockOutAmount1").toString());
			stockOutSubTotal2 += Double.parseDouble(data.getRows().get(i).get("stockOutAmount2").toString());			
			stockOutTotal += Double.parseDouble(data.getRows().get(i).get("stockOutTotalAmount").toString());

    	}
    	
    	
    	summary.put("stockInSubTotal1", df.format(stockInSubTotal1));
		summary.put("stockInSubTotal2", df.format(stockInSubTotal2));
		summary.put("stockInSubTotal3", df.format(stockInSubTotal3));
		summary.put("stockInSubTotal4", df.format(stockInSubTotal4));
		summary.put("stockInSubTotal5", df.format(stockInSubTotal5));
		summary.put("stockInTotal", df.format(stockInTotal));
		summary.put("stockOutSubTotal1", df.format(stockOutSubTotal1));
		summary.put("stockOutSubTotal2", df.format(stockOutSubTotal2));
		summary.put("stockOutTotal", df.format(stockOutTotal));
		
		List<Map<String,Object>> allRows = reportDao.getMonthSummaryReport(projectIds, year, month);
		data.setTotalRecords(allRows.size());
		data.setSummary(summary);
		
		return data;
	}
	
	
	
	
	public ReportPageData getMonthDetailReport(String projectIds, String categoryId, String year, String month, int pageNo, int pageSize) {
		
		ReportPageData data = reportDao.getMonthDetailReport(projectIds, categoryId, year, month, pageNo, pageSize);
		
		Map<String,Object> summary = new HashMap<String,Object>();
		DecimalFormat df = new DecimalFormat("0.00");
		
		Double initQty = 0.00;
		Double initAmount = 0.00;
		Double pastInQty = 0.00;
		Double pastInAmount = 0.00;
		Double pastOutQty = 0.00;
		Double pastOutAmount = 0.00;
		
		
		Double curInQty = 0.00;
		Double curInAmount = 0.00;
		Double curOutQty = 0.00;
		Double curOutAmount = 0.00;
		
		Double stockInSubTotal1 = 0.00;
		Double stockInSubTotal2 = 0.00;
		Double stockInSubTotal3 = 0.00;
		Double stockInSubTotal4 = 0.00;
		Double stockInSubTotal5 = 0.00;
		Double stockInTotal = 0.00;
		Double stockOutSubTotal1 = 0.00;
		Double stockOutSubTotal2 = 0.00;
		Double stockOutTotal = 0.00;
		

    	for(int i=0; i<data.getRows().size(); i++) {
    		
    		initQty = Double.parseDouble(data.getRows().get(i).get("init_qty").toString());
			initAmount = Double.parseDouble(data.getRows().get(i).get("init_amount").toString());
			pastInQty = Double.parseDouble(data.getRows().get(i).get("init_in_qty").toString());
			pastInAmount = Double.parseDouble(data.getRows().get(i).get("init_in_amount").toString());
			pastOutQty = Double.parseDouble(data.getRows().get(i).get("init_out_qty").toString());
			pastOutAmount = Double.parseDouble(data.getRows().get(i).get("init_out_amount").toString());
			
			data.getRows().get(i).put("lastMonthQty", df.format(initQty + pastInQty - pastOutQty));
			data.getRows().get(i).put("lastMonthAmount", df.format(initAmount + pastInAmount - pastOutAmount));
			data.getRows().get(i).remove("init_qty");
			data.getRows().get(i).remove("init_amount");
			data.getRows().get(i).remove("init_in_qty");
			data.getRows().get(i).remove("init_in_amount");
			data.getRows().get(i).remove("init_out_qty");
			data.getRows().get(i).remove("init_out_amount");
			
			
			curInQty = Double.parseDouble(data.getRows().get(i).get("stockInTotalQty").toString());
			curInAmount = Double.parseDouble(data.getRows().get(i).get("stockInTotalAmount").toString());
			curOutQty = Double.parseDouble(data.getRows().get(i).get("stockOutTotalQty").toString());
			curOutAmount = Double.parseDouble(data.getRows().get(i).get("stockOutTotalAmount").toString());
			data.getRows().get(i).put("curMonthQty",df.format(initQty + pastInQty - pastOutQty + curInQty - curOutQty));
			data.getRows().get(i).put("curMonthAmount",df.format(initAmount + pastInAmount - pastOutAmount + curInAmount - curOutAmount));
			
			
			stockInSubTotal1 += Double.parseDouble(data.getRows().get(i).get("stockInAmount1").toString());
			stockInSubTotal2 += Double.parseDouble(data.getRows().get(i).get("stockInAmount2").toString());
			stockInSubTotal3 += Double.parseDouble(data.getRows().get(i).get("stockInAmount3").toString());
			stockInSubTotal4 += Double.parseDouble(data.getRows().get(i).get("stockInAmount4").toString());
			stockInSubTotal5 += Double.parseDouble(data.getRows().get(i).get("stockInAmount5").toString());			
			stockInTotal += Double.parseDouble(data.getRows().get(i).get("stockInTotalAmount").toString());
			
			stockOutSubTotal1 += Double.parseDouble(data.getRows().get(i).get("stockOutAmount1").toString());
			stockOutSubTotal2 += Double.parseDouble(data.getRows().get(i).get("stockOutAmount2").toString());			
			stockOutTotal += Double.parseDouble(data.getRows().get(i).get("stockOutTotalAmount").toString());

    	}
    	
    	
    	summary.put("stockInSubTotal1", df.format(stockInSubTotal1));
		summary.put("stockInSubTotal2", df.format(stockInSubTotal2));
		summary.put("stockInSubTotal3", df.format(stockInSubTotal3));
		summary.put("stockInSubTotal4", df.format(stockInSubTotal4));
		summary.put("stockInSubTotal5", df.format(stockInSubTotal5));
		summary.put("stockInTotal", df.format(stockInTotal));
		summary.put("stockOutSubTotal1", df.format(stockOutSubTotal1));
		summary.put("stockOutSubTotal2", df.format(stockOutSubTotal2));
		summary.put("stockOutTotal", df.format(stockOutTotal));
		
		List<Map<String,Object>> allRows = reportDao.getMonthDetailReport(projectIds, categoryId, year, month);
		data.setTotalRecords(allRows.size());
		data.setSummary(summary);
		
		return data;
	}
	
	
	
	
	
	private String getStartDate(String year, String month) {
		int yearNo = Integer.parseInt(year);
		int monthNo = Integer.parseInt(month);
		if(monthNo == 1) {
			yearNo = yearNo -1;
			monthNo = 12;
		}
		else {
			monthNo = monthNo -1;
		}
		
		return yearNo + "-" + monthNo + "-31";
	}
	
	
	
	
}
