package com.cloudsky.material.utility;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

	public static void write(HttpServletResponse response,HSSFWorkbook wb,String fileName){
		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(response.getOutputStream());
			response.setCharacterEncoding("UTF-8");
			// 设置返回的头信息
			response.setHeader(
					"Content-disposition",
					"attachment;filename="
					+ URLEncoder.encode(
					(((null == fileName) || ("".equals(fileName.trim()))) ? ((new Date().getTime()) + "") : fileName.trim()) + ".xls","UTF-8"));
			// 写入客户端
			wb.write(stream);
			stream.flush();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
