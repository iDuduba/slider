package com.laic.slider.api.enums;

public enum CodeEnum {

	SUCCESS("0000", "成功"),	
    UPLOAD_EERROR("5000", "上传文件错误"),  
    UPLOAD_EXCEED("5001", "上传文件大小超过限制"),  
	FAIL("9999","系统内部异常,请稍候再试."),
	MD5_ERROR("9998","请求参数非法.");
	
	private String code;
	private String msg;
	
	CodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
