package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.MonthReportDataBo;
import com.cloudsky.material.dto.ReportDataItem;
import com.cloudsky.material.dto.ReportStockInFilter;
import com.cloudsky.material.dto.ReportStockOutFilter;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.FormCheckItem;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.ReportService;
import com.cloudsky.material.utility.ReportPageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("stockin")
	public Response stockInReport(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "J01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			ReportStockInFilter filter = (ReportStockInFilter)JSONObject.toBean(jsonObj, ReportStockInFilter.class);
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getStockInReportData(projectId, filter, pageNo, pageSize);
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
		final String permissionCode = "K01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			ReportStockOutFilter filter = (ReportStockOutFilter)JSONObject.toBean(jsonObj, ReportStockOutFilter.class);
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getStockOutReportData(projectId, filter, pageNo, pageSize);
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
		final String permissionCode = "L01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthSummaryReport(projectId, year, month, pageNo, pageSize);
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
		final String permissionCode = "M01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			String categoryId = jsonObj.getString("categoryId");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthDetailReport(projectId, categoryId, year, month, pageNo, pageSize);
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
	
	
	
	@RequestMapping("monthSummayRevise")
	public Response monthSummayRevise(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "L03";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthSummaryReviseReport(projectId, year, month, pageNo, pageSize);
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
	
	
	
	@RequestMapping("monthDetailRevise")
	public Response monthDetailRevise(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "M03";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			String categoryId = jsonObj.getString("categoryId");
			String year = jsonObj.getString("year");
			String month = jsonObj.getString("month");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
            if(hasPermission) {
            	ReportPageData page = reportService.getMonthDetailReviseReport(projectId, categoryId, year, month, pageNo, pageSize);
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
	
	
	
	@RequestMapping("updateMonthSummayRevise")
	public Response updateMonthSummayRevise(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "L03";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("rows", ReportDataItem.class);
			MonthReportDataBo dataBo = (MonthReportDataBo)JSONObject.toBean(jsonObj, MonthReportDataBo.class, classMap);

			
			String userId = dataBo.getUserId();
			
			
            boolean hasPermission = permissionService.verify(userId, dataBo.getProjectId(), permissionCode);
			
            if(hasPermission) {
            	boolean sucess = reportService.updateMonthSummaryReviseReport(dataBo.getRows());

            	if(sucess) {
            		response.setStatus(Response.SUCCESS);
            	}
            	else {
            		response.setStatus(Response.FAIL);
            	}

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
	
	
	
	
	@RequestMapping("updateMonthDetailRevise")
	public Response updateMonthDeailRevise(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "L03";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("rows", ReportDataItem.class);
			MonthReportDataBo dataBo = (MonthReportDataBo)JSONObject.toBean(jsonObj, MonthReportDataBo.class, classMap);

			
			String userId = dataBo.getUserId();
			
			
            boolean hasPermission = permissionService.verify(userId, dataBo.getProjectId(), permissionCode);
			
            if(hasPermission) {
            	boolean sucess = reportService.updateMonthDetailReviseReport(dataBo.getRows());

            	if(sucess) {
            		response.setStatus(Response.SUCCESS);
            	}
            	else {
            		response.setStatus(Response.FAIL);
            	}

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
