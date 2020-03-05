package com.moran.coupons.detailsobjects;

import com.moran.coupons.enums.UserType;

public class UserDetailsObject {
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private UserType userType;
	private Long companyId;


	//---------------------------constructors--------------------

	public UserDetailsObject() {
	}


	public UserDetailsObject(String userName, String password, String email, UserType userType, Long companyId, Long userId) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userType = userType;
		this.companyId = companyId;
		this.userId = userId;
	}


	//----------------------------getters and setters-----------------

	

	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public UserType getUserType() {
		return userType;
	}



	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public Long getCompanyId() {
		return companyId;
	}



	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public String getEmail() {
		return email;
	}
	
	
	
	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}


	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email
				+ ", userType=" + userType + ", companyId=" + companyId + "]";
	}

}
