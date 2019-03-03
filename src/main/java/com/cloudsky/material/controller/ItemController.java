package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.ItemBo;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.Sku;
import com.cloudsky.material.service.ItemService;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.service.SkuService;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@Autowired
	private SkuService skuService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("list")
	public Response list(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_item";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String categoryId = jsonObj.getString("categoryId");
			String keyword = jsonObj.getString("keyword");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				PageData page = itemService.getPage(categoryId, keyword, pageNo, pageSize);
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
	
	
	
	@RequestMapping("listByCategory")
	public Response listByCategory(@RequestBody String jsonStr) {
		
		Response response = new Response();

		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);

			String categoryId = jsonObj.getString("categoryId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = jsonObj.getInt("pageSize");
            String keyword = "";
			
			PageData page = itemService.getPage(categoryId, keyword, pageNo, pageSize);
			page.makeTages();
			response.setStatus(Response.SUCCESS);
			response.setData(page);
						
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	@RequestMapping("query")
	public Response query(@RequestBody String jsonStr) {
		
		Response response = new Response();
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String categoryId = jsonObj.getString("categoryId");
			String keyword = jsonObj.getString("keyword");
					
			List<Map<String,Object>> items = itemService.query(categoryId, keyword);
			response.setStatus(Response.SUCCESS);
			response.setData(items);
	
			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
	}
	
	
	
	@RequestMapping("findSpecList")
	public Response findSpecList(@RequestBody String jsonStr) {
		
		Response response = new Response();
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String itemId = jsonObj.getString("itemId");
					
			List<Map<String,Object>> rows = skuService.findListByItemId(itemId);
			response.setStatus(Response.SUCCESS);
			response.setData(rows);
	
			
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
			ItemBo itemBo = itemService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(itemBo);
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
		final String menuCode = "enable_item";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("skus", Sku.class);
			ItemBo itemBo = (ItemBo)JSONObject.toBean(jsonObj, ItemBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				int result = itemService.add(itemBo);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增材料成功！");
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
		final String menuCode = "enable_item";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("skus", Sku.class);
			ItemBo itemBo = (ItemBo)JSONObject.toBean(jsonObj, ItemBo.class, classMap);
			
            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				int result = itemService.update(itemBo);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改材料成功！");
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
		final String menuCode = "enable_item";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);

            boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {

				int result = itemService.delete(ids);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg(Response.SUCCESS_MSG);
				}
				else if(result==2) {
					response.setStatus(2);
					response.setMsg("此材料存在关联数据，暂时无法删除！");
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
