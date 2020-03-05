package com.moran.coupons.detailsobjects;

public class CustomerDetailsObject {
	private UserDetailsObject user;
	private int amountOfKids;
	private boolean isMarried;
	private String address;
	private String phone;



	//-----------------------constructors----------------------------------------------------

	public CustomerDetailsObject() {
	}


	public CustomerDetailsObject(int amountOfKids, boolean isMarried, String address, String phone, UserDetailsObject user) {
		this.amountOfKids = amountOfKids;
		this.isMarried = isMarried;
		this.address = address;
		this.phone = phone;
		this.user = user;
	}



	//-----------------------getters and setters-------------------------------------------------

	public UserDetailsObject getUser() {
		return user;
	}


	public int getAmountOfKids() {
		return amountOfKids;
	}



	public boolean getIsMarried() {
		return isMarried;
	}



	public String getAddress() {
		return address;
	}



	public String getPhone() {
		return phone;
	}


	//-----------------------to string---------------------

	@Override
	public String toString() {
		return "Customer [user=" + user + ", amountOfKids=" + amountOfKids + ", isMarried=" + isMarried + ", address="
				+ address + ", phone=" + phone + "]";
	}


}
