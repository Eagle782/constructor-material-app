package com.cloudsky.material.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.cloudsky.material.dto.Response;
import com.cloudsky.material.service.FileService;
import com.cloudsky.material.utility.OSSFileUtil;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/material/file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@RequestMapping("upload")
	@ResponseBody
	public Response upload(@RequestParam("file") MultipartFile file) {
		
		Response response = new Response();
		try {
			byte[] bytes = file.getBytes();
			if (bytes != null && bytes.length > 0) {

				String suffix = getFileSuffix(file.getOriginalFilename());
				String tarFileName = "附件"+fileService.getNextFileId() + "." + suffix;	
				String tarFilePath = tarFileName;
				OSSFileUtil.uploadFile(bytes, tarFilePath);
				fileService.add(tarFileName);
				
				response.setStatus(Response.SUCCESS);
				response.setData(tarFileName);
			}
			else {
				response.setStatus(Response.FAIL);
			}
			
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(ex.getMessage());
		}
		
		return response;
	}
	
	
	
	@RequestMapping("delete")
	@ResponseBody
	public Response delete(@RequestBody String jsonStr) {
		
		Response response = new Response();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			String fileName = jsonObj.getString("fileName");
			boolean rsult = fileService.delete(fileName);
			if(rsult) {
				OSSFileUtil.deleteFile(fileName);
				response.setStatus(Response.SUCCESS);
			}
			else {
				response.setStatus(Response.FAIL);
			}
			
			
		}
		catch(Exception ex){
			response.setStatus(Response.FAIL);
			response.setMsg(ex.getMessage());
		}
		
		return response;
	}
	
	
	public static String getFileSuffix(String fileName){

		String[] strs = fileName.split("\\.");
		return strs[strs.length-1];
	}
	
}
