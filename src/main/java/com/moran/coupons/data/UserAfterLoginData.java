package com.moran.coupons.data;

import com.moran.coupons.enums.UserType;

public class UserAfterLoginData {
	private long id;
	private UserType userType;
	private Long companyId;



	public UserAfterLoginData(long id, UserType userType, Long companyId) {
		this.id = id;
		this.userType = userType;
		this.companyId = companyId;
	}


	public UserAfterLoginData() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
}
