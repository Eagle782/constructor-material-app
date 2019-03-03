package com.cloudsky.material.dto;

public class Response {

	public final static int SUCCESS = 1;
	public final static int FAIL = 0;
	public final static int NO_PERMISSION = -1;	
	public final static String SUCCESS_MSG = "操作成功！";
	public final static String FAIL_MSG = "操作失败！";
	public final static String NO_PERMISSION_MSG = "您没有操作权限！";
	
    private int status;
    private String msg;    
    private Object data;

	
    
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
