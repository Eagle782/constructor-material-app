package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.DictionaryDao;

@Service
public class DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;
	
	public List<Map<String,Object>> findList(String category){
		return dictionaryDao.findList(category);
	}
}
