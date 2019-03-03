package com.cloudsky.material.utility;

public class SqlUtil {

	public static String genCountSql(String sql) {
		StringBuilder countSql = new StringBuilder();
		countSql.append("select count(*) as cnts from (");
		countSql.append(sql);
		countSql.append(") a");
		return countSql.toString();
	}
	
	
	public static String DeInjection(String args) {  
		if(null!=args){
			args = args.replaceAll(".*([';]+|(--)+).*", ""); 
		}
		return args;
	}
	
	
	public static String ifNull(String value) {
		if(value==null) {
			return "NULL";
		}
		else {
			value = value.replaceAll(".*([';]+|(--)+).*", "");
			return "'" + value + "'";
		}
	}
	
	
}
