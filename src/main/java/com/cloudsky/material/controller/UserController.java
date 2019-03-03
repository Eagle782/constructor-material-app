package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.dto.RoleBo;
import com.cloudsky.material.dto.UserBo;
import com.cloudsky.material.dto.UserProfile;
import com.cloudsky.material.entity.Project;
import com.cloudsky.material.entity.Role;
import com.cloudsky.material.entity.User;
import com.cloudsky.material.service.UserService;
import com.cloudsky.material.utility.JsonUtil;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




@RestController
@RequestMapping("/material/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public Response list(@RequestBody String jsonStr) {
		
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			int pageNo = jsonObj.getInt("pageNo");
			int pageSize = 20;
			PageData page = userService.getPage(pageNo, pageSize);
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
	
	
	@RequestMapping("login")
	public Response login(@RequestBody String jsonStr) {
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userName = jsonObj.getString("userName");
			String password = jsonObj.getString("password");
			
			UserProfile userProfile = userService.login(userName, password);
			if(userProfile!=null) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("登陆成功！");
				response.setData(userProfile);
			}
			else {
				response.setStatus(2);
				response.setMsg("用户名或密码错误！");
			}
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
		}
		
		
		return response;
	}
	
	
	@RequestMapping("updatePassword")
	public Response updatePassword(@RequestBody String jsonStr) {
		
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			String oldPassword = jsonObj.getString("oldPassword");
			String newPassword = jsonObj.getString("newPassword");
			
			int result = userService.updatePassword(userId, oldPassword, newPassword);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("修改密码成功！");
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("旧密码不正确！");
			}			
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
		}
		return response;
	}
	
	
	
	@RequestMapping("resetPassword")
	public Response resetPassword(@RequestBody String jsonStr) {
		
		Response response = new Response();
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			int result = userService.resetPassword(userId);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("重置密码成功，您的新密码为123456");
			}
			else {
				response.setStatus(Response.FAIL);
			}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			response.setStatus(Response.FAIL);
		}
		return response;
	}
	
	
	
	@RequestMapping("get")
	public Response get(@RequestBody String jsonStr) {
		
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String id = jsonObj.getString("id");
			
			UserBo userBo = userService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(userBo);
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
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("projects", Project.class);
			classMap.put("roles", Role.class);
			UserBo userBo = (UserBo)JSONObject.toBean(jsonObj, UserBo.class, classMap);
			
			int result = userService.add(userBo);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("新增用户成功！");
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("此用户名已经存在，请更换！");
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
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("projects", Project.class);
			classMap.put("roles", Role.class);
			UserBo userBo = (UserBo)JSONObject.toBean(jsonObj, UserBo.class, classMap);
			
			int result = userService.update(userBo);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("修改用户成功！");
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("此用户名已经存在，请更换！");
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
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			JSONArray idList = jsonObj.getJSONArray("idList");
			List<String> ids = JsonUtil.toList(idList);
			
			int result = userService.delete(ids);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg(Response.SUCCESS_MSG);
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
