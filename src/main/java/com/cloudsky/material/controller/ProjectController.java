package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.Project;
import com.cloudsky.material.service.ProjectService;
import com.cloudsky.material.utility.PageData;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("listAll")
	public Response listAll() {
		Response response = new Response();
		try {
			List<Project> projects = projectService.findAll();
			response.setStatus(Response.SUCCESS);
			response.setData(projects);
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
			PageData page = projectService.getPage(pageNo, pageSize);
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
		
		try{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);	
			String id = jsonObj.getString("id");
			Map<String,Object> project = projectService.get(id);
			response.setStatus(Response.SUCCESS);
			response.setData(project);
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
			String name = jsonObj.getString("name");
			
			int result = projectService.add(name);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("新增项目成功！");
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("此项目名称已经存在，请更换名称！");
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
			String id = jsonObj.getString("id");
			String name = jsonObj.getString("name");
			
			int result = projectService.update(id, name);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg("修改项目成功！");
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("此项目名称已经存在，请更换名称！");
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
			String id = jsonObj.getString("id");
			
			int result = projectService.delete(id);
			if(result==1) {
				response.setStatus(Response.SUCCESS);
				response.setMsg(Response.SUCCESS_MSG);
			}
			else if(result==2) {
				response.setStatus(2);
				response.setMsg("此项目存在关联数据，暂时无法删除！");
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
