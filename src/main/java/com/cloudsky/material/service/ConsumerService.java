package com.cloudsky.material.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.ConsumerDao;
import com.cloudsky.material.entity.Consumer;
import com.cloudsky.material.utility.PageData;

@Service
public class ConsumerService {

	@Autowired
	private ConsumerDao consumerDao;
	
	
	public List<Map<String,Object>> findAll(String projectId){
		return consumerDao.findAll(projectId);
	}
	
	public PageData getPage(String projectId, int pageNo, int pageSize){
		return consumerDao.getPage(projectId, pageNo, pageSize);
	}
	
	
	public Map<String,Object> get(String id){
		return consumerDao.get(id);
	}
	
	
	public int add(Consumer consumer){
		int counts = consumerDao.getCountsByName(consumer.getProjectId(), consumer.getName());
		if(counts>0) {
			return 2;
		}
		else {
			return consumerDao.add(consumer);
		}
	}
	
	
	public int update(Consumer consumer){
		int counts = consumerDao.getCountsByNameAndExcludeId(consumer.getProjectId(), consumer.getName(), consumer.getId());
		if(counts>0) {
			return 2;
		}
		else {
			return consumerDao.update(consumer);
		}
	}
	
	
	public int delete(List<String> ids){
		int result = 0;
		/*for(String id : ids) {
			if(itemDao.getCountsByCategoryId(id)>0) {
				result = 2;
				break;
			}
		}*/
		
		if(result !=2 && consumerDao.delete(ids)) {
			result = 1;
		}
		
		return result;
	}
	
	
}
