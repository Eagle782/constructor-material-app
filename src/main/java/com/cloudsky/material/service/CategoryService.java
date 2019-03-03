package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.CategoryDao;
import com.cloudsky.material.dao.FormCheckDao;
import com.cloudsky.material.dao.FormPlanDao;
import com.cloudsky.material.dao.FormStockInDao;
import com.cloudsky.material.dao.FormStockOutDao;
import com.cloudsky.material.dao.ItemDao;
import com.cloudsky.material.entity.Category;
import com.cloudsky.material.utility.PageData;

@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private FormPlanDao formPlanDao;
	
	@Autowired
	private FormCheckDao formCheckDao;
	
	@Autowired
	private FormStockInDao formStockInDao;
	
	@Autowired
	private FormStockOutDao formStockOutDao;
	
	
	public List<Map<String,Object>> findAll(){
		return categoryDao.findAll();
	}
	
	public PageData getPage(int pageNo, int pageSize){
		return categoryDao.getPage(pageNo, pageSize);
	}
	
	
	public Map<String,Object> get(String id){
		return categoryDao.get(id);
	}
	
	
	public int add(Category category){
		int counts = categoryDao.getCountsByName(category.getName());
		if(counts>0) {
			return 2;
		}
		else {
			return categoryDao.add(category);
		}
	}
	
	
	public int update(Category category){
		int counts = categoryDao.getCountsByNameAndExcludeId(category.getName(), category.getId());
		if(counts>0) {
			return 2;
		}
		else {
			return categoryDao.update(category);
		}
	}
	
	
	public int delete(List<String> ids){
		int result = 0;
		for(String id : ids) {
			if(itemDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
			else if(formPlanDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
			else if(formCheckDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
			else if(formStockInDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
			else if(formStockOutDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
		}
		
		if(result !=2 && categoryDao.delete(ids)) {
			result = 1;
		}
		
		return result;
	}
	
}
