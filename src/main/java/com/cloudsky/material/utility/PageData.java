package com.cloudsky.material.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageData {
	
	private int pageNo;
	private int totalPages;
	private int pageSize;
	private int totalRecords;
	private int nextPageNo;
	private int PrePageNo;
	private List<Map<String, Object>> rows;
	private List<Integer> pageTags;
	
	
	public int getNextPageNo() {
		return nextPageNo;
	}
	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}
	public int getPrePageNo() {
		return PrePageNo;
	}
	public void setPrePageNo(int prePageNo) {
		PrePageNo = prePageNo;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}
	public List<Integer> getPageTags() {
		return pageTags;
	}
	public void setPageTags(List<Integer> pageTags) {
		this.pageTags = pageTags;
	}
	

	
	public void makeTages() {
		

		
		if(totalRecords % pageSize >0) {
			totalPages = (int)Math.floor(totalRecords / pageSize)+1;
		}
		else {
			totalPages = (int)Math.floor(totalRecords / pageSize);
		}
		
		
		int startTag = 1;
		int endTag = totalPages;
		
		if(totalPages>10){
			if(pageNo<=6){
				startTag = 1;
				endTag = 10;
			}
			else{
				endTag = pageNo + 4;
				if(endTag>totalPages){
					endTag = totalPages;
				}
				startTag = endTag-9;
			}
			
		}
		
		
		if(pageTags==null){
			pageTags = new ArrayList<Integer>();
		}
	
		pageTags.clear();
		for(int i=startTag;i<=endTag;i++){
			pageTags.add(i);
		}
		
		
		
		//-----------
		if(pageNo>1){
			PrePageNo = pageNo-1;
		}
		else{
			PrePageNo = 1;
		}
		
		
		if(pageNo<totalPages){
			nextPageNo = pageNo+1;
		}
		else{
			nextPageNo = totalPages;
		}
		
		
	}

}
