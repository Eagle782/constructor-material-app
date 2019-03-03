package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.FormCheckDao;
import com.cloudsky.material.dto.FormCheckBo;
import com.cloudsky.material.dto.FormCheckFilter;
import com.cloudsky.material.entity.FormCheckItem;
import com.cloudsky.material.utility.ListUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.StringUtil;

@Service
public class FormCheckService {

	@Autowired
	private FormCheckDao formCheckDao;
	
	public PageData getPage(String projectId, FormCheckFilter filter, int pageNo, int pageSize){
		return formCheckDao.getPage(projectId, filter, pageNo, pageSize);
	}
	
	
	public FormCheckBo get(String id){

		Map<String,Object> formCheck = formCheckDao.get(id);
		
		if(formCheck!=null) {
			
			FormCheckBo formCheckBo = new FormCheckBo();
			formCheckBo.setId(id);
			formCheckBo.setProjectId(formCheck.get("project_id").toString());
			formCheckBo.setCategoryId(formCheck.get("category_id").toString());
			formCheckBo.setSupplierId(formCheck.get("supplier_id").toString());
			formCheckBo.setCheckDate(formCheck.get("check_date").toString());
			formCheckBo.setShippingNo(StringUtil.ifNull(formCheck.get("shipping_no")));
			formCheckBo.setRemark(formCheck.get("remark").toString());
			formCheckBo.setCreator(formCheck.get("creator").toString());
			formCheckBo.setCategoryName(formCheck.get("categoryName").toString());
			formCheckBo.setSupplierName(formCheck.get("supplierName").toString());
			
			List<String> attachFiles = new ArrayList<String>();
			if(formCheck.get("attach_files")!=null && !formCheck.get("attach_files").toString().isEmpty()) {
				attachFiles = ListUtil.phrase(formCheck.get("attach_files").toString(), ",");
			}
			formCheckBo.setAttachFiles(attachFiles);
			

			List<FormCheckItem> items = new ArrayList<FormCheckItem>();
			
			List<Map<String,Object>> checkItems = formCheckDao.getItemList(id);
			for(int i=0; i<checkItems.size(); i++) {
				FormCheckItem item = new FormCheckItem();
				item.setFormId(id);
				item.setItemId(checkItems.get(i).get("item_id").toString());
				item.setName(checkItems.get(i).get("name").toString());
				item.setSpec(checkItems.get(i).get("spec").toString());
				item.setUnit(checkItems.get(i).get("unit").toString());
				item.setPlanQty(StringUtil.ifNull(checkItems.get(i).get("plan_qty")));
				item.setQty(checkItems.get(i).get("qty").toString());
				item.setPrice(StringUtil.ifNull(checkItems.get(i).get("price").toString()));
				item.setAmount(StringUtil.ifNull(checkItems.get(i).get("amount").toString()));
				item.setRemark(StringUtil.ifNull(checkItems.get(i).get("remark")));
				item.setExtend1(StringUtil.ifNull(checkItems.get(i).get("extend_1")));
				item.setExtend2(StringUtil.ifNull(checkItems.get(i).get("extend_2")));
				item.setExtend3(StringUtil.ifNull(checkItems.get(i).get("extend_3")));
				item.setExtend4(StringUtil.ifNull(checkItems.get(i).get("extend_4")));
				item.setExtend5(StringUtil.ifNull(checkItems.get(i).get("extend_5")));
				item.setExtend6(StringUtil.ifNull(checkItems.get(i).get("extend_6")));
				item.setExtend7(StringUtil.ifNull(checkItems.get(i).get("extend_7")));
				item.setExtend8(StringUtil.ifNull(checkItems.get(i).get("extend_8")));
				item.setExtend9(StringUtil.ifNull(checkItems.get(i).get("extend_9")));
				item.setExtend10(StringUtil.ifNull(checkItems.get(i).get("extend_10")));
				item.setExtend11(StringUtil.ifNull(checkItems.get(i).get("extend_11")));
				item.setExtend12(StringUtil.ifNull(checkItems.get(i).get("extend_12")));
				item.setExtend13(StringUtil.ifNull(checkItems.get(i).get("extend_13")));
				item.setExtend14(StringUtil.ifNull(checkItems.get(i).get("extend_14")));
				item.setExtend15(StringUtil.ifNull(checkItems.get(i).get("extend_15")));
				item.setExtend16(StringUtil.ifNull(checkItems.get(i).get("extend_16")));
				item.setExtend17(StringUtil.ifNull(checkItems.get(i).get("extend_17")));
				item.setExtend18(StringUtil.ifNull(checkItems.get(i).get("extend_18")));
				item.setExtend19(StringUtil.ifNull(checkItems.get(i).get("extend_19")));
				item.setExtend20(StringUtil.ifNull(checkItems.get(i).get("extend_20")));
				
				items.add(item);
			}
			
			formCheckBo.setItems(items);
			
			return formCheckBo;
		}
		else {
			return null;
		}
		
		
	}
	
	public boolean add(FormCheckBo formCheck){
		return formCheckDao.add(formCheck);
	}
	
	
	public boolean update(FormCheckBo formCheck){
		return formCheckDao.update(formCheck);
	}
	
	
	public boolean delete(List<String> ids){
		return formCheckDao.delete(ids);
	}
	
	
	
}
