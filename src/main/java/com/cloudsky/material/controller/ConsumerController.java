package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.Consumer;
import com.cloudsky.material.service.ConsumerService;
import com.cloudsky.material.service.PermissionService;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/consumer")
public class ConsumerController {

	@Autowired
	private ConsumerService consumerService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("listAll")
	public Response listAll(@RequestBody String jsonStr) {
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String projectId = jsonObj.getString("projectId");
			List<Map<String,Object>> consumers = consumerService.findAll(projectId);
			response.setStatus(Response.SUCCESS);
			response.setData(consumers);
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
		final String permissionCode = "E01";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			
			boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				PageData page = consumerService.getPage(projectId, pageNo, pageSize);
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
			Map<String,Object> consumer = consumerService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(consumer);
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
		final String permissionCode = "E02";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");
			Consumer consumer = (Consumer)JSONObject.toBean(jsonObj, Consumer.class);
			String projectId = consumer.getProjectId();
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				int result = consumerService.add(consumer);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("新增领料单位成功！");
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
		final String permissionCode = "E03";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String userId = jsonObj.getString("userId");			
			Consumer consumer = (Consumer)JSONObject.toBean(jsonObj, Consumer.class);
			String projectId = consumer.getProjectId();
			
            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {
				int result = consumerService.update(consumer);
				if(result==1) {
					response.setStatus(Response.SUCCESS);
					response.setMsg("修改领料单位成功！");
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
		final String permissionCode = "E04";
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String projectId = jsonObj.getString("projectId");
			
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);

            boolean hasPermission = permissionService.verify(userId, projectId, permissionCode);
			
			if(hasPermission) {

				int result = consumerService.delete(ids);
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
