package com.laic.slider.exception;


import com.laic.slider.api.enums.CodeEnum;

public class InvalidRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3813331196513990004L;

	public InvalidRequestException() {
		this(CodeEnum.REQUEST_ERROR.name());
	}

	public InvalidRequestException(String message) {
		super(message);
	}

}
