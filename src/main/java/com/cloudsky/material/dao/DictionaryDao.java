package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.utility.DBUtil;

@Component
public class DictionaryDao {

	public List<Map<String,Object>> findList(String category){
		String sql = "SELECT `key`,`value` from dictionary where category=?";   	 
		Object[] args ={category};    
    	List<Map<String,Object>> rows = DBUtil.Query(sql, args);
    	return rows;
	}
}
