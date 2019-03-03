package com.cloudsky.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloudsky.material.utility.DBUtil;


@Component
public class SkuDao {

	public List<Map<String,Object>> findListByItemId(String itemId){
		StringBuilder sql = new StringBuilder();
		sql.append("select id,spec from sku where item_id=?");
		
		Object[] args={itemId};
		List<Map<String,Object>> rows = DBUtil.Query(sql.toString(), args);
		return rows;
	}
	
	
  
	
}
