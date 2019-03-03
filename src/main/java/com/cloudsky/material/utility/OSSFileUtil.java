package com.cloudsky.material.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class OSSFileUtil {

	private static String ACCESS_ID;
	private static String ACCESS_KEY;
	private static String OSS_ENDPOINT;	
	private static String url;
	private static String bucketName = null;

	private static OSSClient client = null;


	public static String getUrl() {
		return url;
	}

	

	static {
		Resource resource = new ClassPathResource("/application.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			ACCESS_ID = props.getProperty("oss.accessid");
			ACCESS_KEY = props.getProperty("oss.accesskey");
			OSS_ENDPOINT = props.getProperty("oss.endpoint");
			url = props.getProperty("oss.url");
			bucketName = props.getProperty("oss.bucketName");


		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientConfiguration conf = new ClientConfiguration();
		conf.setMaxConnections(10);
		conf.setConnectionTimeout(5000);
		conf.setMaxErrorRetry(5);
		conf.setSocketTimeout(5000);
		client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY, conf);

	}

	public static void uploadFile(String fileName, String filePath) throws Exception {
		File file = new File(filePath);
		String key = fileName;
		InputStream content = new FileInputStream(file);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(file.length());
		@SuppressWarnings("unused")
		PutObjectResult result = OSSFileUtil.client.putObject(OSSFileUtil.bucketName, key, content, meta);
	}



	public static void uploadFile(byte[] bytes, String fileName) throws Exception {
		String key = fileName;
		OSSFileUtil.client.putObject(OSSFileUtil.bucketName, key, new ByteArrayInputStream(bytes));
	}



	public static void deleteFile(String key) {
		try {
			OSSFileUtil.client.deleteObject(bucketName, key);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	 public static String genImgFileName(){
			StringBuffer sb = new StringBuffer(10);
			Random random = new Random();
			SimpleDateFormat format = new SimpleDateFormat("HHmmss");
			sb.append(format.format(new Date()));
			for (int i = 0; i < 10; i++) {
				sb.append(random.nextInt(9));
			}
			
			return sb.toString() + ".jpg";
	}
		 
}
