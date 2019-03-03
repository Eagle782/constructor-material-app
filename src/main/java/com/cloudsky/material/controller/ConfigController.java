package com.cloudsky.material.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloudsky.material.dto.ExtendFormFieldsBo;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.entity.ExtendFormField;
import com.cloudsky.material.service.ConfigService;
import com.cloudsky.material.service.PermissionService;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/material/config")
public class ConfigController {

	@Autowired
	private ConfigService configService;
	
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("getExtendFormFields")
	public Response getExtendFormFields(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_custom_config";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String userId = jsonObj.getString("userId");
			
			boolean hasPermission = permissionService.verify(userId, menuCode);
			
			if(hasPermission) {
				List<Map<String,Object>> rows = configService.findExtendFormItemFields();
				response.setStatus(Response.SUCCESS);
				response.setData(rows);
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
	
	
	
	@RequestMapping("updateExtendFormFields")
	public Response updateExtendFormFields(@RequestBody String jsonStr) {
		
		Response response = new Response();
		final String menuCode = "enable_custom_config";
		
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Map<String,Object> classMap = new  HashMap<String,Object>(); 
			classMap.put("fields", ExtendFormField.class);
			ExtendFormFieldsBo bo = (ExtendFormFieldsBo)JSONObject.toBean(jsonObj, ExtendFormFieldsBo.class, classMap);
			
			boolean hasPermission = permissionService.verify(bo.getUserId(), menuCode);
			
			if(hasPermission) {
				boolean success = configService.updateExtendFormFields(bo.getFields());
				if(success) {
					response.setStatus(Response.SUCCESS);
				}
				else {
					response.setStatus(Response.FAIL);
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
