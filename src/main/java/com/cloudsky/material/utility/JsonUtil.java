package com.cloudsky.material.utility;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class JsonUtil {

	public static List<String> toList(JSONArray jsonArray){
		
		List<String> list = new ArrayList<String>();
		for(int i=0; i<jsonArray.size(); i++) {
			list.add(jsonArray.get(i).toString());
		}
		return list;
	}
	
	
}
