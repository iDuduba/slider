package com.laic.slider.api.response;


import com.laic.slider.api.enums.CodeEnum;
import lombok.Data;

@Data
public abstract class BaseResponse {
	protected String respCode;
	protected String respMsg;
	
	public void setResponse(CodeEnum resp) {
		respCode = resp.getCode();
		respMsg = resp.getMsg();
	}
	
}
