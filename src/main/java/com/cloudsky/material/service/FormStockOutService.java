package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.FormStockOutDao;
import com.cloudsky.material.dto.FormStockOutBo;
import com.cloudsky.material.dto.FormStockOutFilter;
import com.cloudsky.material.entity.FormStockOutItem;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.StringUtil;

@Service
public class FormStockOutService {

	@Autowired
	private FormStockOutDao formStockOutDao;
 
	public PageData getPage(String projectId, FormStockOutFilter filter, int pageNo, int pageSize){
		return formStockOutDao.getPage(projectId, filter, pageNo, pageSize);
	}
	
	
	public FormStockOutBo get(String id){

		Map<String,Object> form = formStockOutDao.get(id);
		
		if(form!=null) {
			
			FormStockOutBo formBo = new FormStockOutBo();
			formBo.setId(id);
			formBo.setProjectId(form.get("project_id").toString());
			formBo.setCategoryId(form.get("category_id").toString());
			formBo.setConsumerId(form.get("consumer_id").toString());
			formBo.setType(form.get("type").toString());
			formBo.setApplyDate(form.get("apply_date").toString());
			formBo.setRemark(form.get("remark").toString());
			formBo.setCreator(form.get("creator").toString());
			formBo.setCategoryName(form.get("categoryName").toString());
			formBo.setConsumerName(form.get("consumerName").toString());
			formBo.setTypeName(form.get("typeName").toString());
			

			List<FormStockOutItem> items = new ArrayList<FormStockOutItem>();
			
			List<Map<String,Object>> stockOutItems = formStockOutDao.getItemList(id);
			for(int i=0; i<stockOutItems.size(); i++) {
				
				FormStockOutItem item = new FormStockOutItem();
				item.setFormId(id);
				item.setItemId(stockOutItems.get(i).get("item_id").toString());
				item.setName(stockOutItems.get(i).get("name").toString());
				item.setSpec(stockOutItems.get(i).get("spec").toString());
				item.setUnit(stockOutItems.get(i).get("unit").toString());
				item.setQty(stockOutItems.get(i).get("qty").toString());
				item.setPrice(stockOutItems.get(i).get("price").toString());
				item.setAmount(stockOutItems.get(i).get("amount").toString());
				item.setTaxPrice(stockOutItems.get(i).get("tax_price").toString());
				item.setTaxAmount(stockOutItems.get(i).get("tax_amount").toString());
				item.setRemark(StringUtil.ifNull(stockOutItems.get(i).get("remark")));
				items.add(item);
			}
			
			formBo.setItems(items);
			
			return formBo;
		}
		else {
			return null;
		}
		
		
	}
	
	public boolean add(FormStockOutBo formBo){
		return formStockOutDao.add(formBo);
	}
	
	
	public boolean update(FormStockOutBo formBo){
		return formStockOutDao.update(formBo);
	}
	
	
	public boolean delete(List<String> ids){
		return formStockOutDao.delete(ids);
	}
	
	
}
