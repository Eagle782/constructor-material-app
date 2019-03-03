package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.FormCheckDao;
import com.cloudsky.material.dao.FormPlanDao;
import com.cloudsky.material.dao.FormStockInDao;
import com.cloudsky.material.dao.FormStockOutDao;
import com.cloudsky.material.dao.ItemDao;
import com.cloudsky.material.dao.SkuDao;
import com.cloudsky.material.dto.ItemBo;
import com.cloudsky.material.entity.Sku;
import com.cloudsky.material.utility.PageData;

@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private FormPlanDao formPlanDao;
	
	@Autowired
	private FormCheckDao formCheckDao;
	
	@Autowired
	private FormStockInDao formStockInDao;
	
	@Autowired
	private FormStockOutDao formStockOutDao;
	
	public PageData getPage(String categoryId, String keyword, int pageNo, int pageSize){
		return itemDao.getPage(categoryId, keyword, pageNo, pageSize);
	}
	
	
	public List<Map<String,Object>> query(String categoryId, String keyword){
		return itemDao.query(categoryId, keyword);
	}
	
	
	
    public ItemBo get(String id){
		
		
		
		Map<String,Object> item = itemDao.get(id);
		if(item!=null) {
			ItemBo itemBo = new ItemBo();
			
			itemBo.setId(id);
			itemBo.setCategoryId(item.get("category_id").toString());
			itemBo.setCode(item.get("code").toString());
			itemBo.setName(item.get("name").toString());
			itemBo.setUnit(item.get("unit").toString());
			
			List<Sku> skus = new ArrayList<Sku>();
			List<Map<String,Object>> skuList = skuDao.findListByItemId(id);
			for(int i=0; i<skuList.size(); i++) {
				Sku sku = new Sku();
				sku.setId(skuList.get(i).get("id").toString());
				sku.setSpec(skuList.get(i).get("spec").toString());
				skus.add(sku);
			}
			itemBo.setSkus(skus);
			
			return itemBo;
		}
		else {
			return null;
		}
		

	}
	
	
	public int add(ItemBo item){
		int result = 0;
		int counts = itemDao.getCountsByName(item.getName());
		if(counts>0) {
			return 2;
		}
		else {
			if(itemDao.add(item)) {
				result = 1;
			}
		}
		
		return result;
	}
	
	
	public int update(ItemBo item){
		int result = 0;
		int counts = itemDao.getCountsByNameAndExcludeId(item.getName(), item.getId());
		if(counts>0) {
			return 2;
		}
		else {
			if(itemDao.update(item)) {
				result = 1;
			}
		}
		
		return result;
	}
	
	
	public int delete(List<String> ids){
		int result = 0;
		for(String id : ids) {
			if(formPlanDao.getCountsByItemId(id)>0) {
				result = 2;
				break;
			}
			else if(formCheckDao.getCountsByItemId(id)>0) {
				result = 2;
				break;
			}
			else if(formStockInDao.getCountsByItemId(id)>0) {
				result = 2;
				break;
			}
			else if(formStockOutDao.getCountsByItemId(id)>0) {
				result = 2;
				break;
			}
		}
		
		if(result !=2 && itemDao.delete(ids)) {
			result = 1;
		}
		
		return result;
	}
	
	
	
}
