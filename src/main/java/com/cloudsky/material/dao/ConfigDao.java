package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.entity.ExtendFormField;
import com.cloudsky.material.utility.DBUtil;
import com.cloudsky.material.utility.SqlUtil;

@Component
public class ConfigDao {

	public List<Map<String,Object>> findExtendFormItemFields(){
		String sql = "SELECT no,enable,ifnull(field_name,'') fieldName from extend_form_field";   	  	
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
	}
	
	
	public List<Map<String,Object>> findEnabledExtendFormItemFields(){
		String sql = "SELECT no,ifnull(field_name,'') fieldName from extend_form_field where enable=1";   	  	
    	List<Map<String,Object>> rows = DBUtil.Query(sql);
    	return rows;
	}
	
	
	public boolean updateExtendFormFields(List<ExtendFormField> fields) {
		
		StringBuilder sql=new StringBuilder();	
		
		for(ExtendFormField field : fields) {
			sql.append("update extend_form_field set enable=" + field.getEnable() + ",field_name='" + SqlUtil.DeInjection(field.getFieldName()) + "' ");
			sql.append("where no=" + field.getNo() + ";");
		}
		
		return DBUtil.batchUpdate(sql.toString().split(";"));
		
	}
	
	
}
