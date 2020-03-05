package com.moran.coupons.exceptions;

import com.moran.coupons.enums.ErrorTypes;

public class ApplicationException extends RuntimeException {
	
	private ErrorTypes errorTypes;

	public ApplicationException(ErrorTypes errorTypes, String message) {
		super(message);
		this.errorTypes = errorTypes;
	}
	
	public ApplicationException(Exception e, ErrorTypes errorTypes, String message) {
		super(message, e);
		this.errorTypes = errorTypes;
	}

	public ErrorTypes getErrorTypes() {
		return errorTypes;
	}

}
