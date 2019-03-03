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
import com.cloudsky.material.dto.FormPlanBo;
import com.cloudsky.material.dto.FormPlanFilter;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.FormPlanItem;
import com.cloudsky.material.service.FormPlanService;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.ProjectService;
import com.cloudsky.material.utility.ExcelUtil;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/material/plan")
public class FormPlanController {

	@Autowired
	private FormPlanService formPlanService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("list")
	@ResponseBody
	public Response list(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "F01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			FormPlanFilter filter = (FormPlanFilter)JSONObject.toBean(jsonObj, FormPlanFilter.class);
			
			boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				PageData page = formPlanService.getPage(projectId, filter, pageNo, pageSize);
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
			FormPlanBo formPlanBo = formPlanService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(formPlanBo);
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
		final String permissionCode = "F02";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("items", FormPlanItem.class);
			FormPlanBo formPlanBo = (FormPlanBo)JSONObject.toBean(jsonObj, FormPlanBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, formPlanBo.getProjectId(), permissionCode);
			
			if(hasPermission) {
				boolean success = formPlanService.add(formPlanBo);
				if(success) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增需求计划单成功！");
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
		final String permissionCode = "F03";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("items", FormPlanItem.class);
			FormPlanBo formPlanBo = (FormPlanBo)JSONObject.toBean(jsonObj, FormPlanBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, formPlanBo.getProjectId(), permissionCode);
			
			if(hasPermission) {
				boolean success = formPlanService.update(formPlanBo);
				if(success) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改需求计划单成功！");
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
		
		final String permissionCode = "F04";
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				boolean success = formPlanService.delete(ids);
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
	
	
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String id) {
		
		//localhost:8081//material/plan/export?id=1
		FormPlanBo bo = formPlanService.get(id);
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
	    cell.setCellValue("中建四局珠海分公司贵阳分公司需求计划单");
		
		HSSFCellStyle headStyle = wb.createCellStyle();
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 20);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headStyle.setFont(headfont);

		cell.setCellStyle(headStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
		

		//top info
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		row = sheet.createRow(1);
		row.setHeightInPoints(30);
		
		cell = row.createCell(0);
		cell.setCellValue("单据编号："+ bo.getId());
		cell.setCellStyle(titleStyle);

		
		cell = row.createCell(3);
		cell.setCellValue("材料类别："+bo.getCategoryName());
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(6);
		cell.setCellValue("流水编号："+bo.getSerialNo());
		cell.setCellStyle(titleStyle);
		
		row = sheet.createRow(2);
		row.setHeightInPoints(30);
		cell = row.createCell(0);
		cell.setCellValue("项目名称：" + projectName);
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(3);
		cell.setCellValue("计划日期："+bo.getPlanDate());
		cell.setCellStyle(titleStyle);

		sheet.addMergedRegion(new CellRangeAddress(1,1,0,2));
		sheet.addMergedRegion(new CellRangeAddress(1,1,3,5));
		sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
		sheet.addMergedRegion(new CellRangeAddress(2,2,3,5));
		sheet.addMergedRegion(new CellRangeAddress(2,2,6,7));
		
		
		//table
		String[] titles = {"编号","品名","规格型号","单位","计划数量","计划进场日期","使用部位","备注"};
		int[] widths = {3000,6000,5000,3000,4000,5000,4000,4000};
		
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
			cell.setCellValue(bo.getItems().get(i).getQty());
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue(bo.getItems().get(i).getPlanArrivalDate());
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue(bo.getItems().get(i).getUsage());
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
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
		cell.setCellValue("申请人：");
		cell.setCellStyle(titleStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,0,2));
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,3,5));
		sheet.addMergedRegion(new CellRangeAddress(bottomRowIndex,bottomRowIndex,6,7));
		
		

		String exportFileName = "需求计划单(" + id + ")";
		ExcelUtil.write(response, wb, exportFileName);
	}
	
	
	
}
