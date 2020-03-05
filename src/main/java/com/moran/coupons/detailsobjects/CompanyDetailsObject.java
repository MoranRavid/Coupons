package com.moran.coupons.detailsobjects;

import com.moran.coupons.enums.Type;

public class CompanyDetailsObject {
	private Long companyId;
	private String companyName;
	private String address;
	private String phone;
	private Type type;
	
	
	
	
	//--------------------constructors-------------------------------------
	
	
	public CompanyDetailsObject() {
	}
	
	
	
	public CompanyDetailsObject(String companyName, String address, String phone, Type type, Long companyId) {
		this.companyName = companyName;
		this.address = address;
		this.phone = phone;
		this.type = type;
		this.companyId = companyId;
	}

	
	
	//-------------------getters and setters-------------------------------
	
	

	public String getCompanyName() {
		return companyName;
	}


	
	public String getAddress() {
		return address;
	}



	public String getPhone() {
		return phone;
	}


	
	public Type getType() {
		return type;
	}


	
	public Long getCompanyId() {
		return companyId;
	}



	@Override
	public String toString() {
		return "Company [name=" + companyName + ", address=" + address + ", phone=" + phone
				+ ", type=" + type + "]";
	}
	
	
}
