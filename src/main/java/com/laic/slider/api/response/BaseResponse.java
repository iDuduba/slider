package com.laic.slider.api.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laic.slider.api.enums.CodeEnum;
import com.laic.slider.api.enums.LangEnum;
import lombok.Data;

@Data
public abstract class BaseResponse {
	protected String respCode;
	protected String respMsg;

	@JsonIgnore
	protected LangEnum language = LangEnum.CN;

	public void setResponse(CodeEnum resp) {
		respCode = resp.getCode();
		respMsg = resp.getMsg();
	}
	
}
