package com.moran.coupons.data;

public class ErrorBean {
	private int errorNumber;
	private String errorMessage;
	private String errorName;


	public ErrorBean(int errorNumber, String errorMessage, String errorName) {
		this.errorNumber = errorNumber;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
	}


	public ErrorBean() {
	}


	public int getErrorNumber() {
		return errorNumber;
	}


	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getErrorName() {
		return errorName;
	}


	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
}
