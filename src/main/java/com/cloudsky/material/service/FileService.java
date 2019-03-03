package com.cloudsky.material.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudsky.material.dao.UploadFileDao;

@Service
public class FileService {

	@Autowired
	private UploadFileDao uploadFileDao;
	
	public int getNextFileId() {
		return uploadFileDao.getMaxId() + 1;
	}
	
	public int add(String fileName) {
		return uploadFileDao.add(fileName);
	}
	
	public boolean delete(String fileName) {
		return uploadFileDao.delete(fileName);
	}
	
	
}
