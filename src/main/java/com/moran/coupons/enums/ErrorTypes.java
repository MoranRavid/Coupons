package com.moran.coupons.enums;

public enum ErrorTypes{

	GENERAL_ERROR(601, "General error", "GENERAL ERROR", true),
	FAILED_TO_GENERATE_ID(602, "Failed to generte id", "FAILED TO GENERATE ID", false),
	FAILED_TO_GET_OBJECT_REQUESTED(603, "Failed to get object requested", "FAILED TO GET OBJECT REQUESTED", false),
	ILLIGAL_USER_INPUT(604, "Illigal user input", "ILLIGAL USER INPUT", false),
	LOGIN_FAILED(605, "The Login has failed", "LOGIN FAILED", false),
	UNAUTHORIZED_USER(606, "You are not authorized", "UNAUTHORIZED USER", false),
	INVALID_LOGIN(607, "Invalid login", "INVALID LOGIN", false),
	DUPLICATE_COMPANY_NAME(608, "There is already a company by this name", "DUPLICATE COMPANY NAME", false),
	INVALID_COMPANY_NAME(609, "Company name is invalid", "INVALID COMPANY NAME", false),
	INVALID_ADDRESS(610, "Address is invalid", "INVALID ADDRESS", false),
	INVALID_PHONE_NUMBER(611, "Phone number is invalid", "INVALID PHONE NUMBER", false),
	INVALID_TYPE(612, "Type is invalid", "INVALID TYPE", false),
	UNKNOWN_COMPANY(613, "Unknown company", "UNKNOWN COMPANY", false),
	DUPLICATE_EMAIL(614, "The email is already exist", "DUPLICATE EMAIL", false),
	UNKNOWN_USER(615, "Unknown user", "UNKNOWN USER", false),
	MISSIMG_PASSWORD_NUMBER(616, "The password has to contain at least one number", "MISSIMG PASSWORD NUMBER", false),
	MISSIMG_PASSWORD_LOWER_CASE_LETTER(617, "The password has to contain at least one lower case letter", "MISSIMG PASSWORD LOWER CASE LETTER", false),
	MISSIMG_PASSWORD_CAPITAL_LETTER(618, "The password has to contain at least one capital letter", "MISSIMG PASSWORD CAPITAL LETTER", false),
	PASSWORD_TOO_SHORT(619, "The password is too short", "PASSWORD TOO SHORT", false),
	PASSWORD_TOO_LONG(620, "The password is too long", "PASSWORD TOO LONG", false),
	UNKNOWN_PURCHASE(621, "Unknown purchase", "UNKNOWN PURCHASE", false),
	UNKNOWN_COUPON(622, "Unknown coupon", "UNKNOWN COUPON", false),
	ILLIGAL_AMOUNT_OF_ITEMS(623, "You've tried to purchase illigal amount of items", "ILLIGAL AMOUNT OF ITEMS", false),
	COUPON_IS_NOT_AVAILABLE_YET(624, "The coupon is not available yet", "COUPON IS NOT AVAILABLE YET", false),
	EXPIRED_COUPON(625, "The coupon expiry date already passed", "EXPIRED_COUPON", false),
	ILLIGAL_AMOUNT_OF_KIDS(626, "Amount of kids is invalid", "ILLIGAL AMOUNT OF KIDS", false),
	UNKNOWN_CUSTOMER(627, "Unknown customer", "UNKNOWN CUSTOMER", false),
	DUPLICATE_COUPON_TITLE(628, "There is already a coupon by this name", "DUPLICATE COUPON NAME", false),
	INVALID_COUPON_TITLE(629, "coupon title is invalid", "INVALID COUPON TITLE", false),
	INVALID_COUPON_PRICE(629, "coupon price is invalid", "INVALID COUPON PRICE", false),
	ILLIGAL_UNITS_AMOUNT(630, "Units amount is invalid", "ILLIGAL UNITS AMOUNT", false),
	ILLIGAL_START_DATE(631, "Start date is invalid", "ILLIGAL START DATE", false),
	ILLIGAL_EXPIRY_DATE(632, "Expiry date is invalid", "ILLIGAL EXPIRY DATE", false),
	ILLIGAL_DATES(633, "Expiry date cannot be before start day", "ILLIGAL DATES", false);


	
		
	private int errorNumber;
	private String errorMessage;
	private String errorName;
	private boolean isShowStackTrace;
	
	private ErrorTypes() {
	}

	private ErrorTypes(int errorNumber, String errorMessage, String errorName, boolean isShowStackTrace) {
		this.errorNumber = errorNumber;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
		this.isShowStackTrace = isShowStackTrace;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorName() {
		return errorName;
	}

	public boolean isShowStackTrace() {
		return isShowStackTrace;
	}
}
