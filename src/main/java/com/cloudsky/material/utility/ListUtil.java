package com.cloudsky.material.utility;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static String toString(List<String> rows) {
		String result = "";
		if(rows!=null && rows.size()>0) {
        	for(String row : rows) {
        		result += row + ",";
        	}
        	result = result.substring(0, result.length()-1);
        }
		return result;
	}
	
	
	
	public static List<String> phrase(String value, String delimiter){
		String[] strs = value.split(delimiter);
		
		List<String> rows = new ArrayList<String>();
		for(String str : strs) {
			if(!str.isEmpty()) {
				rows.add(str);
			}
			
		}
		
		return rows;
	}
	
}
