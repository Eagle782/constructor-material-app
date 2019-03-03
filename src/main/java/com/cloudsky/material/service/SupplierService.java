package com.cloudsky.material.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.SupplierDao;
import com.cloudsky.material.entity.Supplier;
import com.cloudsky.material.utility.Page;

@Service
public class SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	public List<Supplier> findAll(String projectId){
		return supplierDao.findAll(projectId);
	}
	
	public Page<Supplier> getPage(String projectId, int pageNo, int pageSize){
		return supplierDao.getPage(projectId, pageNo, pageSize);
	}
	
	
	public Supplier get(String id){
		return supplierDao.get(id);
	}
	
	
	public int add(Supplier supplier){
		int counts = supplierDao.getCountsByName(supplier.getProjectId(), supplier.getName());
		if(counts>0) {
			return 2;
		}
		else {
			return supplierDao.add(supplier);
		}
	}
	
	
	public int update(Supplier supplier){
		int counts = supplierDao.getCountsByNameAndExcludeId(supplier.getProjectId(), supplier.getName(), supplier.getId());
		if(counts>0) {
			return 2;
		}
		else {
			return supplierDao.update(supplier);
		}
	}
	
	
	public int delete(List<String> ids){
		int result = 0;
		/*for(String id : ids) {
			if(supplierDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
		}*/
		
		if(result !=2 && supplierDao.delete(ids)) {
			result = 1;
		}
		
		return result;
	}
	
	
}
