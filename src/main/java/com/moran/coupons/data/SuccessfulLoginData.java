package com.moran.coupons.data;

import com.moran.coupons.enums.UserType;

public class SuccessfulLoginData {

	//not very secure, it better to use string
	private int token;
	private UserType userType;


	//---------------------constructors-------------------------
	public SuccessfulLoginData() {
	}


	public SuccessfulLoginData(int token, UserType userType) {
		this.token = token;
		this.userType = userType;
	}

	//----------------------getters and setters----------------

	public int getToken() {
		return token;
	}


	public void setToken(int token) {
		this.token = token;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}




	//-------------------------to string-----------------

	@Override
	public String toString() {
		return "SuccessfulLoginDetails [token=" + token + ", userType=" + userType + "]";
	}




}
