package com.cloudsky.material.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudsky.material.dto.ReportStockInFilter;
import com.cloudsky.material.dto.ReportStockOutFilter;
import com.cloudsky.material.dto.TopReportStockInFilter;
import com.cloudsky.material.dto.TopReportStockOutFilter;
import com.cloudsky.material.service.ReportService;
import com.cloudsky.material.service.TopReportService;
import com.cloudsky.material.utility.ExcelUtil;
import com.cloudsky.material.utility.ReportPageData;
import com.cloudsky.material.utility.StringUtil;

@Controller
@RequestMapping("/material/export")
public class ExporterController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private TopReportService topReportService;
	
	@RequestMapping("/stockInReport")
	public void stockInReport(HttpServletRequest request, HttpServletResponse response, String projectId, ReportStockInFilter filter) throws Exception{


		//localhost:8081/material/export/stockInReport?projectId=1&categoryId=1&startDate=2018-12-17&endDate=&typeId=&itemKeyword=&supplierKeyword=
		filter.urlDecode();
		
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getStockInReportData(projectId, filter, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		String[] titles = {"年","月","日","类型","材料名称","规格型号","单位","供货单位","含税单价","不含税单价","数量","含税金额","不含税金额","物资送货单号","验收人员","备注"};
		String[] fields = {"year","month","day","type","name","spec","unit","supplierName","price","noTaxPrice","qty","amount","noTaxAmount","shippingNo","creator","remark"};
		int[] widths = {3000,3000,3000,3000,6000,4000,4000,3000,6000,3000,3000,3000,3000,3000,3000,3000,3000};
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		row = sheet.createRow(0);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+1);
			
			for(int k=0; k<fields.length; k++) {
				
				cell = row.createCell(k);
				cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "物资进场台账";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	
	
	
	
	@RequestMapping("/stockInTopReport")
	public void stockInTopReport(HttpServletRequest request, HttpServletResponse response, TopReportStockInFilter filter) throws Exception{


		//localhost:8081/material/export/stockInTopReport?projectId=1&categoryId=1&startDate=2018-12-17&endDate=&typeId=&itemKeyword=&supplierKeyword=
		filter.urlDecode();
		
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = topReportService.getStockInReportData(filter, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		String[] titles = {"项目名称","年","月","日","类型","材料名称","规格型号","单位","供货单位","含税单价","不含税单价","数量","含税金额","不含税金额","物资送货单号","验收人员","备注"};
		String[] fields = {"projectName","year","month","day","type","name","spec","unit","supplierName","price","noTaxPrice","qty","amount","noTaxAmount","shippingNo","creator","remark"};
		int[] widths = {6000,3000,3000,3000,3000,6000,4000,4000,3000,6000,3000,3000,3000,3000,3000,3000,3000,3000};
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		row = sheet.createRow(0);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+1);
			
			for(int k=0; k<fields.length; k++) {
				
				cell = row.createCell(k);
				cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "物资进场台账";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/stockOutReport")
	public void stockOutReport(HttpServletRequest request, HttpServletResponse response, String projectId, ReportStockOutFilter filter) throws Exception{


		//localhost:8081/material/export/stockInReport?projectIds=1,2&categoryId=1&startDate=2018-12-17&endDate=&typeId=&itemKeyword=&supplierKeyword=
		filter.urlDecode();
		
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getStockOutReportData(projectId, filter, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		String[] titles = {"年","月","日","类型","材料名称","规格型号","单位","领用单位","含税单价","不含税单价","数量","含税金额","不含税金额","领料人","备注"};
		String[] fields = {"year","month","day","type","name","spec","unit","consumerName","price","noTaxPrice","qty","amount","noTaxAmount","creator","remark"};
		int[] widths = {3000,3000,3000,3000,6000,4000,4000,3000,6000,3000,3000,3000,3000,3000,3000,3000};
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		row = sheet.createRow(0);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+1);
			
			for(int k=0; k<fields.length; k++) {
				
				cell = row.createCell(k);
				cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "物资出库台账";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/stockOutTopReport")
	public void stockOutTopReport(HttpServletRequest request, HttpServletResponse response, TopReportStockOutFilter filter) throws Exception{


		//localhost:8081/material/export/stockOutTopReport?projectIds=1&categoryId=1&startDate=2018-12-17&endDate=&typeId=&itemKeyword=&supplierKeyword=
		filter.urlDecode();
		
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = topReportService.getStockOutReportData(filter, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		String[] titles = {"项目名称","年","月","日","类型","材料名称","规格型号","单位","领用单位","含税单价","不含税单价","数量","含税金额","不含税金额","领料人","备注"};
		String[] fields = {"projectName","year","month","day","type","name","spec","unit","consumerName","price","noTaxPrice","qty","amount","noTaxAmount","creator","remark"};
		int[] widths = {6000,3000,3000,3000,3000,6000,4000,4000,3000,6000,3000,3000,3000,3000,3000,3000,3000};
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		row = sheet.createRow(0);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+1);
			
			for(int k=0; k<fields.length; k++) {
				
				cell = row.createCell(k);
				cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "物资出库台账";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	@RequestMapping("/monthSummaryReport")
	public void monthSummaryReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthSummaryReport?projectId=1&year=2018&month=12
		
		String projectId = request.getParameter("projectId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getMonthSummaryReport(projectId, year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//header
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(2);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,3,5,6,7,8,9,10,11,12,13,14,15,17,18,19,20,21,23};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,4,15));
		sheet.addMergedRegion(new CellRangeAddress(0,0,16,21));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		
		int[] emptyCol2s = {0,1,2,3,5,7,9,11,13,15,17,19,21,22,23};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,22,23));
		
		//table
		String[] titles = {"序号","材料类别","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"categoryName","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度汇总表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/monthSummaryReviseReport")
	public void monthSummaryReviseReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthSummaryReviseReport?projectId=1&year=2018&month=12
		
		String projectId = request.getParameter("projectId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getMonthSummaryReviseReport(projectId, year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//header
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(2);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,3,5,6,7,8,9,10,11,12,13,14,15,17,18,19,20,21,23};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,4,15));
		sheet.addMergedRegion(new CellRangeAddress(0,0,16,21));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		
		int[] emptyCol2s = {0,1,2,3,5,7,9,11,13,15,17,19,21,22,23};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,22,23));
		
		//table
		String[] titles = {"序号","材料类别","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"categoryName","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度汇总表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/monthSummaryTopReport")
	public void monthSummaryTopReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthSummaryTopReport?year=2018&month=12
		String projectIds = request.getParameter("projectIds");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = topReportService.getMonthSummaryReport(projectIds,year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//header
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(2);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,3,5,6,7,8,9,10,11,12,13,14,15,17,18,19,20,21,23};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,4,15));
		sheet.addMergedRegion(new CellRangeAddress(0,0,16,21));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		
		int[] emptyCol2s = {0,1,2,3,5,7,9,11,13,15,17,19,21,22,23};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,1));
		sheet.addMergedRegion(new CellRangeAddress(0,1,2,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,22,23));
		
		//table
		String[] titles = {"序号","材料类别","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"categoryName","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度汇总表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/monthDetailReport")
	public void monthDetailReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthDetailReport?projectId=1&categoryId=1&year=2018&month=12
		
		String projectId = request.getParameter("projectId");
		String categoryId = request.getParameter("categoryId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getMonthDetailReport(projectId, categoryId, year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//第一行
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(4);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(24);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,2,3,5,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,23,25};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,6,17));
		sheet.addMergedRegion(new CellRangeAddress(0,0,18,23));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		

		
		int[] emptyCol2s = {0,1,2,3,4,5,7,9,11,13,15,17,19,21,23,25};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		


		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		sheet.addMergedRegion(new CellRangeAddress(1,1,22,23));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(0,1,24,25));
		
		//table
		String[] titles = {"序号","材料名称","规格型号","单位","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"name","spec","unit","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度明细表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	
	@RequestMapping("/monthDetailReviseReport")
	public void monthDetailReviseReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthDetailReviseReport?projectId=1&categoryId=1&year=2018&month=12
		
		String projectId = request.getParameter("projectId");
		String categoryId = request.getParameter("categoryId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = reportService.getMonthDetailReviseReport(projectId, categoryId, year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//第一行
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(4);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(24);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,2,3,5,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,23,25};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,6,17));
		sheet.addMergedRegion(new CellRangeAddress(0,0,18,23));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		

		
		int[] emptyCol2s = {0,1,2,3,4,5,7,9,11,13,15,17,19,21,23,25};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		


		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		sheet.addMergedRegion(new CellRangeAddress(1,1,22,23));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(0,1,24,25));
		
		//table
		String[] titles = {"序号","材料名称","规格型号","单位","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"name","spec","unit","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度明细表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
	@RequestMapping("/monthDetailTopReport")
	public void monthDetailTopReport(HttpServletRequest request, HttpServletResponse response) throws Exception{


		//localhost:8081/material/export/monthDetailTopReport?categoryId=1&year=2018&month=12
		String projectIds = request.getParameter("projectIds");
		String categoryId = request.getParameter("categoryId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		int pageNo = 1;
		int pageSize = 999999;
		ReportPageData page = topReportService.getMonthDetailReport(projectIds, categoryId, year, month, pageNo, pageSize);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		
		//第一行
		row = sheet.createRow(0);
		row.setHeightInPoints(30);
		
		cell = row.createCell(4);
		cell.setCellValue("上月结存");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("本月收入");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("本月发出");
		cell.setCellStyle(style);
		
		cell = row.createCell(24);
		cell.setCellValue("本月结存");
		cell.setCellStyle(style);
		
		int[] emptyCols = {0,1,2,3,5,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,23,25};
		for(int index : emptyCols) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		

		
		sheet.addMergedRegion(new CellRangeAddress(0,0,6,17));
		sheet.addMergedRegion(new CellRangeAddress(0,0,18,23));
		
		//第二行
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("自购");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("局供");
		cell.setCellStyle(style);
		
		cell = row.createCell(10);
		cell.setCellValue("甲供");
		cell.setCellStyle(style);
		
		cell = row.createCell(12);
		cell.setCellValue("甲控");
		cell.setCellStyle(style);
		
		cell = row.createCell(14);
		cell.setCellValue("调入");
		cell.setCellStyle(style);
		
		cell = row.createCell(16);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		
		cell = row.createCell(18);
		cell.setCellValue("耗用");
		cell.setCellStyle(style);
		
		cell = row.createCell(20);
		cell.setCellValue("调出");
		cell.setCellStyle(style);
		
		cell = row.createCell(22);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		

		
		int[] emptyCol2s = {0,1,2,3,4,5,7,9,11,13,15,17,19,21,23,25};
		for(int index : emptyCol2s) {
			cell = row.createCell(index);
			cell.setCellValue("");
			cell.setCellStyle(style);
		}
		


		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
		sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
		sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
		sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
		sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
		sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
		sheet.addMergedRegion(new CellRangeAddress(1,1,22,23));
		
		
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,3));
		sheet.addMergedRegion(new CellRangeAddress(0,1,4,5));
		sheet.addMergedRegion(new CellRangeAddress(0,1,24,25));
		
		//table
		String[] titles = {"序号","材料名称","规格型号","单位","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额",};		
		int[] widths = {2000,4000,4000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000,3000};
		String[] fields = {"name","spec","unit","lastMonthQty","lastMonthAmount",
				"stockInQty1","stockInAmount1","stockInQty2","stockInAmount2","stockInQty3","stockInAmount3","stockInQty4","stockInAmount4","stockInQty5","stockInAmount5",
				"stockInTotalQty","stockInTotalAmount",
				"stockOutQty1","stockOutAmount1","stockOutQty2","stockOutAmount2","stockOutTotalQty","stockOutTotalAmount",
				"curMonthQty","curMonthAmount"};
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		for(int i=0; i<page.getRows().size(); i++) {
			row = sheet.createRow(i+3);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			for(int k=0; k<fields.length; k++) {
				cell = row.createCell(k+1);
				if(page.getRows().get(i).get(fields[k]).toString().equals("0.00")) {
					cell.setCellValue("");
				}
				else {
					cell.setCellValue(StringUtil.ifNull(page.getRows().get(i).get(fields[k])));
				}
				
				cell.setCellStyle(style);
			}
			
		}
		
		
		String exportFileName = "月度明细表";
		ExcelUtil.write(response, wb, exportFileName);
		
	}
	
	
	
}
