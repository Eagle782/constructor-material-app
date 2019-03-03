package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloudsky.material.dto.FormCheckBo;
import com.cloudsky.material.dto.FormCheckFilter;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.FormCheckItem;
import com.cloudsky.material.service.ConfigService;
import com.cloudsky.material.service.FormCheckService;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.ProjectService;
import com.cloudsky.material.utility.ExcelUtil;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/material/check")
public class FormCheckController {
    
	@Autowired
	private FormCheckService formCheckService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("list")
	@ResponseBody
	public Response list(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "G01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			FormCheckFilter filter = (FormCheckFilter)JSONObject.toBean(jsonObj, FormCheckFilter.class);
			
			boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				PageData page = formCheckService.getPage(projectId, filter, pageNo, pageSize);
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
	
	
	
	@RequestMapping("get")
	@ResponseBody
	public Response get(@RequestBody String jsonStr) {
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String id = jsonObj.getString("id");
			FormCheckBo formCheckBo = formCheckService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(formCheckBo);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	@RequestMapping("add")
	@ResponseBody
	public Response add(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "G02";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("items", FormCheckItem.class);
			FormCheckBo formCheckBo = (FormCheckBo)JSONObject.toBean(jsonObj, FormCheckBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, formCheckBo.getProjectId(), permissionCode);
			
			if(hasPermission) {
				boolean success = formCheckService.add(formCheckBo);
				if(success) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增材料验收单成功！");
				}
				else {
					response.setStatus(Response.FAIL);
					response.setMsg(Response.FAIL_MSG);
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
	
	
	
	@RequestMapping("update")
	@ResponseBody
	public Response update(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "G03";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("items", FormCheckItem.class);
			FormCheckBo formCheckBo = (FormCheckBo)JSONObject.toBean(jsonObj, FormCheckBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, formCheckBo.getProjectId(), permissionCode);
			
			if(hasPermission) {
				boolean success = formCheckService.update(formCheckBo);
				if(success) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改材料验收单成功！");
				}
				else {
					response.setStatus(Response.FAIL);
					response.setMsg(Response.FAIL_MSG);
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
	
	
	@RequestMapping("delete")
	@ResponseBody
	public Response delete(@RequestBody String jsonStr) {
		
		final String permissionCode = "G04";
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				boolean success = formCheckService.delete(ids);
				if(success) {
					response.setStatus(Response.SUCCESS);
					response.setMsg(Response.SUCCESS_MSG);
				}	
				else {
					response.setStatus(Response.FAIL);
					response.setMsg(Response.FAIL_MSG);
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
	
	
	
	@RequestMapping("getExtendItemFields")
	@ResponseBody
	public Response ExtendItemFields() {
		Response response = new Response();
		
		try{
			List<Map<String,Object>> extendFields = configService.findEnabledExtendFormItemFields();
			response.setStatus(Response.SUCCESS);
			response.setData(extendFields);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	

	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String id) {
		
		//localhost:8081//material/plan/export?id=1
		FormCheckBo bo = formCheckService.get(id);
		String projectName = projectService.get(bo.getProjectId()).get("name").toString();
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		sheet.setDefaultColumnWidth(4000);
		sheet.setDefaultRowHeightInPoints(30);
		
		HSSFRow row = null;
		HSSFCell cell = null;
		
		//header
		row = sheet.createRow(0);
		row.setHeightInPoints(50);
		cell = row.createCell(0);
	    cell.setCellValue("中建四局珠海分公司贵阳分公司验收单");
		
		HSSFCellStyle headStyle = wb.createCellStyle();
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 20);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headfont);

		cell.setCellStyle(headStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		

		//top info
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		
		cell = row.createCell(0);
		cell.setCellValue("供货单位："+bo.getSupplierName());
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("单据编号："+ bo.getId());
		cell.setCellStyle(titleStyle);

		
		cell = row.createCell(6);
		cell.setCellValue("材料类别："+bo.getCategoryName());
		cell.setCellStyle(titleStyle);
		
		
		
		row = sheet.createRow(2);
		row.setHeightInPoints(30);
		cell = row.createCell(0);
		cell.setCellValue("项目名称：" + projectName);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("验收日期："+bo.getCheckDate());
		cell.setCellStyle(titleStyle);

		sheet.addMergedRegion(new CellRangeAddress(1,1,0,2));
		sheet.addMergedRegion(new CellRangeAddress(1,1,3,5));
		sheet.addMergedRegion(new CellRangeAddress(1,1,6,8));
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
		sheet.addMergedRegion(new CellRangeAddress(2,2,3,5));
		sheet.addMergedRegion(new CellRangeAddress(2,2,6,8));
		
		
		//table
		String[] titles = {"编号","品名","规格型号","单位","计划数量","实际数量","单价","金额","备注"};
		int[] widths = {3000,6000,5000,3000,4000,4000,4000,4000,4000};
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		row = sheet.createRow(3);
		row.setHeightInPoints(25);
		for(int i=0; i<titles.length; i++) {			
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			sheet.setColumnWidth(i, widths[i]);
			cell.setCellStyle(style);
		}
		
		
		
		for(int i=0; i<bo.getItems().size(); i++) {
			row = sheet.createRow(i+4);
			row.setHeightInPoints(30);
			
			cell = row.createCell(0);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue(bo.getItems().get(i).getName());
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue(bo.getItems().get(i).getSpec());
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue(bo.getItems().get(i).getUnit());
			cell.setCellStyle(style);
			
			cell = row.createCell(4);
			cell.setCellValue(bo.getItems().get(i).getPlanQty());
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue(bo.getItems().get(i).getQty());
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue(bo.getItems().get(i).getPrice());
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			cell.setCellValue(bo.getItems().get(i).getAmount());
			cell.setCellStyle(style);
			
			cell = row.createCell(8);
			cell.setCellValue(bo.getItems().get(i).getRemark());
			cell.setCellStyle(style);
		}
		
		
		//bottom
		int bottomRowIndex = bo.getItems().size() + 4;
		row = sheet.createRow(bottomRowIndex);
		row.setHeightInPoints(30);
		
		cell = row.createCell(0);
		cell.setCellValue("项目经理：");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("材料主管：");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(6);
		cell.setCellValue("验收人：");
		cell.setCellStyle(titleStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,0,2));
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,3,5));
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,6,8));
		
		

		String exportFileName = "材料验收单(" + id + ")";
		ExcelUtil.write(response, wb, exportFileName);
	}
	
	
	
}
