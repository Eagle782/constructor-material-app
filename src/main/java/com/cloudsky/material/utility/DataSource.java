package com.cloudsky.material.utility;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import com.mysql.cj.jdbc.Driver;


public class DataSource {

	private static DataSource instance = null;
	private static SimpleDriverDataSource ds = null;

	
	public static SimpleDriverDataSource getDs() {
		return ds;
	}
	
	

	private DataSource() {
		Resource resource = new ClassPathResource("/application.properties");
		Properties props=null;
		try {
			props= PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ds = new SimpleDriverDataSource();
		ds.setDriverClass(Driver.class);
		ds.setUsername((String) props.get("spring.datasource.username"));
		ds.setUrl((String) props.get("spring.datasource.url"));
		ds.setPassword((String) props.get("spring.datasource.password"));
		
	}
	
	public static DataSource getInstance() {  
	    if (instance == null) {  
	    	instance = new DataSource();  
	    }  
		return instance;  
	}
}
