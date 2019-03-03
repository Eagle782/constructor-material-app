package com.cloudsky.material.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.Supplier;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.SupplierService;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("listAll")
	public Response listAll(@RequestBody String jsonStr) {
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String projectId = jsonObj.getString("projectId");
			List<Supplier> suppliers = supplierService.findAll(projectId);
			response.setStatus(Response.SUCCESS);
			response.setData(suppliers);
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
		
	}
	
	@RequestMapping("list")
	public Response list(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "D01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				Page<Supplier> page = supplierService.getPage(projectId, pageNo, pageSize);
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
	public Response get(@RequestBody String jsonStr) {
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String id = jsonObj.getString("id");
			Supplier supplier = supplierService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(supplier);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	@RequestMapping("add")
	public Response add(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "D02";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");
			Supplier supplier = (Supplier)JSONObject.toBean(jsonObj, Supplier.class);
			String projectId = supplier.getProjectId();
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				int result = supplierService.add(supplier);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增供应商成功！");
				}
				else if(result==2) {
					response.setStatus(2);
					response.setMsg("此名称已经存在，请更换名称！");
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
	public Response update(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "D03";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");			
			Supplier supplier = (Supplier)JSONObject.toBean(jsonObj, Supplier.class);
			String projectId = supplier.getProjectId();
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				int result = supplierService.update(supplier);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改供应商成功！");
				}
				else if(result==2) {
					response.setStatus(2);
					response.setMsg("此名称已经存在，请更换名称！");
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
	public Response delete(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String permissionCode = "D04";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);

            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {

				int result = supplierService.delete(ids);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg(Response.SUCCESS_MSG);
				}
				else if(result==2) {
					response.setStatus(2);
					response.setMsg("此项目存在关联数据，暂时无法删除！");
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
	
	
}
