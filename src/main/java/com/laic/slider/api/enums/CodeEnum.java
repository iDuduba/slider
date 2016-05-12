package com.laic.slider.api.enums;

public enum CodeEnum {

	SUCCESS("0000", "成功"),
	NO_CONTENT("0204", "没有匹配的数据"),

	REQUEST_ERROR("0400", "无效的请求"),
	UNAUTHORIZED("0401", "未授权"),
	FORBIDDEN("0403", "禁止访问"),

	SERVER_ERROR("0500", "系统内部异常"),
	SERVICE_UNAVAILABLE("0503", "服务不可用，稍后再试"),



	USERNAME_ERROR("1000", "无效的用户名"),
	PASSWORD_ERROR("1001", "口令错误"),

	UPLOAD_EERROR("5000","上传文件错误"),
	UPLOAD_EXCEED("5001", "上传文件大小超过限制"),

	SMS_EERROR("5100","发送短信失败"),

	NO_DATA_PARA("9997", "无效的DATA参数"),
	MD5_ERROR("9998", "无效的请求签名."),
	FAIL("9999", "系统内部异常,请稍候再试.");

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
