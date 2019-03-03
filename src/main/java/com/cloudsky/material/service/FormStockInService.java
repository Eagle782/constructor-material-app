package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.FormStockInDao;
import com.cloudsky.material.dto.FormStockInBo;
import com.cloudsky.material.dto.FormStockInFilter;
import com.cloudsky.material.entity.FormStockInItem;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.StringUtil;

@Service
public class FormStockInService {

	@Autowired
	private FormStockInDao formStockInDao;
	
	public PageData getPage(String projectId, FormStockInFilter filter, int pageNo, int pageSize){
		return formStockInDao.getPage(projectId, filter, pageNo, pageSize);
	}
	
	
	public FormStockInBo get(String id){

		Map<String,Object> form = formStockInDao.get(id);
		
		if(form!=null) {
			
			FormStockInBo formBo = new FormStockInBo();
			formBo.setId(id);
			formBo.setProjectId(form.get("project_id").toString());
			formBo.setFormCheckId(StringUtil.ifNull(form.get("form_check_id")));
			formBo.setCategoryId(form.get("category_id").toString());
			formBo.setSupplierId(form.get("supplier_id").toString());
			formBo.setType(form.get("type").toString());
			formBo.setApplyDate(form.get("apply_date").toString());
			formBo.setCreator(form.get("creator").toString());
			formBo.setCategoryName(form.get("categoryName").toString());
			formBo.setSupplierName(form.get("supplierName").toString());
			formBo.setTypeName(form.get("typeName").toString());
			

			List<FormStockInItem> items = new ArrayList<FormStockInItem>();
			
			List<Map<String,Object>> stockInItems = formStockInDao.getItemList(id);
			for(int i=0; i<stockInItems.size(); i++) {
				
				FormStockInItem item = new FormStockInItem();
				item.setFormId(id);
				item.setItemId(stockInItems.get(i).get("item_id").toString());
				item.setName(stockInItems.get(i).get("name").toString());
				item.setSpec(stockInItems.get(i).get("spec").toString());
				item.setUnit(stockInItems.get(i).get("unit").toString());
				item.setQty(stockInItems.get(i).get("qty").toString());
				item.setPrice(stockInItems.get(i).get("price").toString());
				item.setAmount(stockInItems.get(i).get("amount").toString());
				item.setTaxPrice(stockInItems.get(i).get("tax_price").toString());
				item.setTaxAmount(stockInItems.get(i).get("tax_amount").toString());
				item.setRemark(StringUtil.ifNull(stockInItems.get(i).get("remark")));
				items.add(item);
			}
			
			formBo.setItems(items);
			
			return formBo;
		}
		else {
			return null;
		}
		
		
	}
	
	public boolean add(FormStockInBo formBo){
		return formStockInDao.add(formBo);
	}
	
	
	public boolean update(FormStockInBo formBo){
		return formStockInDao.update(formBo);
	}
	
	
	public boolean delete(List<String> ids){
		return formStockInDao.delete(ids);
	}
	
	
}
