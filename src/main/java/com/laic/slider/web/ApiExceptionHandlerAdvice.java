package com.laic.slider.web;

import com.alibaba.fastjson.JSONException;
import com.laic.slider.api.enums.CodeEnum;
import com.laic.slider.api.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerAdvice {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public CommonResponse exception(Exception ex, WebRequest request) {
	    log.error(Throwables.getRootCause(ex).getMessage());

		CommonResponse error = new CommonResponse(CodeEnum.FAIL);
		return error;
	}

	@ExceptionHandler(value = JSONException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CommonResponse onJSONException(Exception ex, WebRequest request) {
		log.error(Throwables.getRootCause(ex).getMessage());

		CommonResponse error = new CommonResponse(CodeEnum.MD5_ERROR);
		return error;
	}

}
