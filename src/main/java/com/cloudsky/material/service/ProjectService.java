package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.FormCheckDao;
import com.cloudsky.material.dao.ProjectDao;
import com.cloudsky.material.entity.Project;
import com.cloudsky.material.utility.PageData;


@Service
public class ProjectService {

	@Autowired
	private ProjectDao porjectDao;
	
	@Autowired
	private FormCheckDao formCheckDao;
	
	
	public List<Project> findAll(){
		return porjectDao.findAll();
	}
	
	public PageData getPage(int pageNo, int pageSize){
		return porjectDao.getPage(pageNo, pageSize);
	}
	
	
	public Map<String,Object> get(String id){
		return porjectDao.get(id);
	}
	
	
	public int add(String name){
		int counts = porjectDao.getCountsByName(name);
		if(counts==0) {
			return porjectDao.add(name);
		}
		else {
			return 2;
		}
	}
	
	
	public int update(String id, String name){
		int counts = porjectDao.getCountsByNameAndExcludeId(name, id);
		if(counts==0) {
			return porjectDao.update(id, name);
		}
		else {
			return 2;
		}
	}
	
	
	public int delete(String id){
		int result = 0;
		if(formCheckDao.getCountsByProject(id)>0) {
			result = 2;
		}
		else {
			result = porjectDao.delete(id);
		}
		return result;
	}
}
