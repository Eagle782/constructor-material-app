package com.cloudsky.material.dao;

import org.springframework.stereotype.Component;

import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class UploadFileDao {

    public int getMaxId(){
		
		String sql = "select ifnull(max(id),0) id from upload_file";		
		return DBUtil.QueryForInt(sql);
		
	}
    
    
    public int add(String fileName) {
    	String sql = "insert into upload_file (file_name) values (?)";
    	Object[] args={fileName};
    	return DBUtil.update(sql, args);
    }
    
    
    public boolean delete(String fileName) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("delete from upload_file where file_name='"+ SqlUtil.DeInjection(fileName) + "';");
    	sql.append("update form_plan set attach_files = replace(attach_files,'"+ SqlUtil.DeInjection(fileName) + "','');");
    	sql.append("update form_check set attach_files = replace(attach_files,'"+ SqlUtil.DeInjection(fileName) + "','')");


    	return DBUtil.batchUpdate(sql.toString().split(";"));
    }
    
}
