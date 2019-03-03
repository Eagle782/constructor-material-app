package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.UnitDao;
import com.cloudsky.material.entity.Unit;
import com.cloudsky.material.utility.PageData;

@Service
public class UnitService {

	@Autowired
	private UnitDao unitDao;
	
	
	public List<Map<String,Object>> findAll(){
		return unitDao.findAll();
	}
	
	public PageData getPage(int pageNo, int pageSize){
		return unitDao.getPage(pageNo, pageSize);
	}
	
	
	public Map<String,Object> get(String id){
		return unitDao.get(id);
	}
	
	
	public int add(Unit unit){
		int counts = unitDao.getCountsByName(unit.getName());
		if(counts>0) {
			return 2;
		}
		else {
			return unitDao.add(unit);
		}
	}
	
	
	public int update(Unit unit){
		int counts = unitDao.getCountsByNameAndExcludeId(unit.getName(), unit.getId());
		if(counts>0) {
			return 2;
		}
		else {
			return unitDao.update(unit);
		}
	}
	
	
	public int delete(List<String> ids){
		int result = 0;

		if(unitDao.delete(ids)) {
			result = 1;
		}
		
		return result;
	}
	
}
