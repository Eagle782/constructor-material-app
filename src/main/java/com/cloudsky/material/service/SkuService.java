package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.SkuDao;

@Service
public class SkuService {

	@Autowired
	private SkuDao skuDao;
	
	
	public List<Map<String,Object>> findListByItemId(String itemId){
		return skuDao.findListByItemId(itemId);
	}
	
	
}
