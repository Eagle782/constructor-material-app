package com.cloudsky.material.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsky.material.dao.FormPlanDao;
import com.cloudsky.material.dto.FormPlanBo;
import com.cloudsky.material.dto.FormPlanFilter;
import com.cloudsky.material.entity.FormPlanItem;
import com.cloudsky.material.utility.ListUtil;
import com.cloudsky.material.utility.PageData;
import com.cloudsky.material.utility.StringUtil;

@Service
public class FormPlanService {

	@Autowired
	private FormPlanDao formPlanDao;
	
	public PageData getPage(String projectId, FormPlanFilter filter, int pageNo, int pageSize){
		return formPlanDao.getPage(projectId, filter, pageNo, pageSize);
	}
	
	
	public FormPlanBo get(String id){

		Map<String,Object> formPlan = formPlanDao.get(id);
		
		if(formPlan!=null) {
			
			FormPlanBo formPlanBo = new FormPlanBo();
			formPlanBo.setId(id);
			formPlanBo.setProjectId(formPlan.get("project_id").toString());
			formPlanBo.setCategoryId(formPlan.get("category_id").toString());
			formPlanBo.setCategoryName(formPlan.get("categoryName").toString());
			formPlanBo.setPlanDate(formPlan.get("plan_date").toString());
			formPlanBo.setSerialNo(StringUtil.ifNull(formPlan.get("serial_no")));
			formPlanBo.setCreator(formPlan.get("creator").toString());
			
			List<String> attachFiles = new ArrayList<String>();
			if(formPlan.get("attach_files")!=null && !formPlan.get("attach_files").toString().isEmpty()) {
				attachFiles = ListUtil.phrase(formPlan.get("attach_files").toString(), ",");
			}
			formPlanBo.setAttachFiles(attachFiles);
			

			List<FormPlanItem> items = new ArrayList<FormPlanItem>();
			
			List<Map<String,Object>> planItems = formPlanDao.getItemList(id);
			for(int i=0; i<planItems.size(); i++) {
				FormPlanItem item = new FormPlanItem();
				item.setFormId(id);
				item.setItemId(planItems.get(i).get("item_id").toString());
				item.setName(planItems.get(i).get("name").toString());
				item.setSpec(planItems.get(i).get("spec").toString());
				item.setPlanArrivalDate(planItems.get(i).get("plan_arrival_date").toString());
				item.setQty(planItems.get(i).get("qty").toString());
				item.setUnit(planItems.get(i).get("unit").toString());
				item.setUsage(StringUtil.ifNull(planItems.get(i).get("usage")));
				item.setRemark(StringUtil.ifNull(planItems.get(i).get("remark")));
				items.add(item);
			}
			
			formPlanBo.setItems(items);
			
			return formPlanBo;
		}
		else {
			return null;
		}
		
		
	}
	
	public boolean add(FormPlanBo formPlan){
		return formPlanDao.add(formPlan);
	}
	
	
	public boolean update(FormPlanBo formPlan){
		return formPlanDao.update(formPlan);
	}
	
	
	public boolean delete(List<String> ids){
		return formPlanDao.delete(ids);
	}
	
	
}
