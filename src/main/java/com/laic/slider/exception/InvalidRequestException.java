package com.laic.slider.exception;


import com.laic.slider.api.enums.CodeEnum;

import java.util.Properties;

public class InvalidRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3813331196513990004L;

	private Properties errors;

	public InvalidRequestException() {
		this(CodeEnum.REQUEST_ERROR.name());
	}

	public InvalidRequestException(String message) {
		super(message);
	}

	public Properties getErrors() {
		return errors;
	}

	public void setErrors(Properties errors) {
		this.errors = errors;
	}
}
