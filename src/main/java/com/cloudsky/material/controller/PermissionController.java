package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.service.PermissionService;


@RestController
@RequestMapping("/material/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("listAll")
	public Response listAll() {
		Response response = new Response();
		try {
			List<Map<String,Object>> permissions = permissionService.getTreeList();
			response.setStatus(Response.SUCCESS);
			response.setData(permissions);
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
		
	}
	
	
}
