package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.dto.RoleBo;
import com.cloudsky.material.service.RoleService;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping("listAll")
	public Response listAll() {
		Response response = new Response();
		try {
			List<Map<String,Object>> roles = roleService.findAll();
			response.setStatus(Response.SUCCESS);
			response.setData(roles);
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
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			PageData page = roleService.getPage(pageNo, pageSize);
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
	
	
	
	@RequestMapping("get")
	public Response get(@RequestBody String jsonStr) {
		
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String id = jsonObj.getString("id");
			
			RoleBo roleProfile = roleService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(roleProfile);
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
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			RoleBo roleProfile = (RoleBo)JSONObject.toBean(jsonObj, RoleBo.class);
			int result = roleService.add(roleProfile);
			if(result == 1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg(Response.SUCCESS_MSG);
			}
			else if(result == 2) {
				response.setStatus(2);
				response.setMsg("角色名称已经存在，请更换！");
			}
			else {
				response.setStatus(Response.FAIL);
				response.setMsg(Response.FAIL_MSG);
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
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			RoleBo roleProfile = (RoleBo)JSONObject.toBean(jsonObj, RoleBo.class);
			int result = roleService.update(roleProfile);
			if(result == 1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg(Response.SUCCESS_MSG);
			}
			else {
				response.setStatus(Response.FAIL);
				response.setMsg(Response.FAIL_MSG);
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
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String id = jsonObj.getString("id");
			int result = roleService.delete(id);
			if(result == 1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg(Response.SUCCESS_MSG);
			}
			else {
				response.setStatus(Response.FAIL);
				response.setMsg(Response.FAIL_MSG);
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
