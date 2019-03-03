package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.ConfigDao;
import com.cloudsky.material.entity.ExtendFormField;


@Service
public class ConfigService {

	@Autowired
	private ConfigDao configDao;
	
	public List<Map<String,Object>> findExtendFormItemFields(){
		return configDao.findExtendFormItemFields();
	}
	
	
	public List<Map<String,Object>> findEnabledExtendFormItemFields(){
		return configDao.findEnabledExtendFormItemFields();
	}
	
	
	public boolean updateExtendFormFields(List<ExtendFormField> fields) {
		return configDao.updateExtendFormFields(fields);
	}
}
