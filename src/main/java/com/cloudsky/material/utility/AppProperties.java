package com.cloudsky.material.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${app.admin.username}")
	private String appAdminUserName;

	@Value("${app.admin.password}")
	private String appAdminPassword;
	
	@Value("${oss.url}")
	private String fileUrlPrefix;
	
	public String getFileUrlPrefix() {
		return fileUrlPrefix;
	}

	public String getAppAdminPassword() {
		return appAdminPassword;
	}

	public String getAppAdminUserName() {
		return appAdminUserName;
	}


	
	
}
