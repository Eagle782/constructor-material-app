package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloudsky.material.dto.MonthReportDataBo;
import com.cloudsky.material.dto.ReportDataItem;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.dto.TopReportStockInFilter;
import com.cloudsky.material.dto.TopReportStockOutFilter;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.TopReportService;
import com.cloudsky.material.utility.ReportPageData;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/topReport")
public class TopReportController {

	@Autowired
	private TopReportService reportService;
	
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("stockin")
	public Response stockInReport(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_top_report";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			TopReportStockInFilter filter = (TopReportStockInFilter)JSONObject.toBean(jsonObj, TopReportStockInFilter.class);
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getStockInReportData(filter, pageNo, pageSize);
				page.makeTages();
				response.setStatus(Response.SUCCESS);
				response.setData(page);
			}
			else {
				response.setStatus(Response.NO_PERMISSION);
				response.setMsg(Response.NO_PERMISSION_MSG);
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	
	@RequestMapping("stockout")
	public Response stockOutReport(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_top_report";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			TopReportStockOutFilter filter = (TopReportStockOutFilter)JSONObject.toBean(jsonObj, TopReportStockOutFilter.class);
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getStockOutReportData(filter, pageNo, pageSize);
				page.makeTages();
				response.setStatus(Response.SUCCESS);
				response.setData(page);
			}
			else {
				response.setStatus(Response.NO_PERMISSION);
				response.setMsg(Response.NO_PERMISSION_MSG);
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	@RequestMapping("monthSummay")
	public Response monthSummay(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_top_report";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectIds = jsonObj.getString("projectIds");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthSummaryReport(projectIds, year, month, pageNo, pageSize);
				page.makeTages();
				response.setStatus(Response.SUCCESS);
				response.setData(page);
			}
			else {
				response.setStatus(Response.NO_PERMISSION);
				response.setMsg(Response.NO_PERMISSION_MSG);
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	@RequestMapping("monthDetail")
	public Response monthDetail(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_top_report";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectIds = jsonObj.getString("projectIds");
			String categoryId = jsonObj.getString("categoryId");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthDetailReport(projectIds, categoryId, year, month, pageNo, pageSize);
				page.makeTages();
				response.setStatus(Response.SUCCESS);
				response.setData(page);
			}
			else {
				response.setStatus(Response.NO_PERMISSION);
				response.setMsg(Response.NO_PERMISSION_MSG);
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	
}
