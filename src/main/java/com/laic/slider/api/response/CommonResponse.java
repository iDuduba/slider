package com.laic.slider.api.response;


import com.laic.slider.api.enums.CodeEnum;
import lombok.Data;

@Data
public class CommonResponse extends BaseResponse {

	public CommonResponse() {
		this(CodeEnum.SUCCESS);
	}
	
	public CommonResponse(CodeEnum resp) {
		respCode = resp.getCode();
		respMsg = resp.getMsg();		
	}
	
}
