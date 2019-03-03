package com.cloudsky.material.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudsky.material.dto.Response;
import com.cloudsky.material.service.DictionaryService;


@RestController
@RequestMapping("/material/dict")
public class DictController {

	@Autowired
	private DictionaryService dictionaryService;
	
	
	@RequestMapping("stockInTypeList")
	public Response stockInTypeList() {
		Response response = new Response();
		try {
			String category = "stock-in-type";
			List<Map<String,Object>> rows = dictionaryService.findList(category);
			response.setStatus(Response.SUCCESS);
			response.setData(rows);
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
		
	}
	
	
	@RequestMapping("stockOutTypeList")
	public Response stockOutTypeList() {
		Response response = new Response();
		try {
			String category = "stock-out-type";
			List<Map<String,Object>> rows = dictionaryService.findList(category);
			response.setStatus(Response.SUCCESS);
			response.setData(rows);
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(Response.FAIL_MSG);
		}
		return response;
		
	}
	
}
