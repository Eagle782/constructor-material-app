package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.Category;
import com.cloudsky.material.service.CategoryService;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("listAll")
	public Response listAll() {
		Response response = new Response();
		try {

			List<Map<String,Object>> categorys = categoryService.findAll();
			response.setStatus(Response.SUCCESS);
			response.setData(categorys);
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
		final String menuCode = "enable_category";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				PageData page = categoryService.getPage(pageNo, pageSize);
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
			Map<String,Object> category = categoryService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(category);
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
		final String menuCode = "enable_category";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");
			Category category = (Category)JSONObject.toBean(jsonObj, Category.class);

            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				int result = categoryService.add(category);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增材料类别成功！");
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
		final String menuCode = "enable_category";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");		
			Category category = (Category)JSONObject.toBean(jsonObj, Category.class);
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				int result = categoryService.update(category);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改材料类别成功！");
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
		final String menuCode = "enable_category";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);

            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {

				int result = categoryService.delete(ids);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg(Response.SUCCESS_MSG);
				}
				else if(result==2) {
					response.setStatus(2);
					response.setMsg("此材料类别存在关联数据，暂时无法删除！");
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
