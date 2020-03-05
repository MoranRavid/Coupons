package com.moran.coupons.detailsobjects;

import com.moran.coupons.enums.Type;

public class CouponDetailsObject {
	private Long couponId;
	private String couponTitle;
	private String description;
	private float couponPrice;
	private Type type;
	private String couponStartDate;
	private String expiryDate;
	private int unitsInStock;
	private long userId;


	//------------------------constructors------------------------------

	public CouponDetailsObject() {
	}


	public CouponDetailsObject(String couponTitle, String description, float couponPrice, Type type, String expiryDate, String couponStartDate, int unitsInStock, long userId, Long couponId) {
		this.couponId = couponId;
		this.couponTitle = couponTitle;
		this.description = description;
		this.couponPrice = couponPrice;
		this.type = type;
		this.expiryDate = expiryDate;
		this.couponStartDate = couponStartDate;
		this.unitsInStock = unitsInStock;
		this.userId = userId;
	}



	//-----------------------getters and setters-------------------------



	public String getCouponTitle() {
		return couponTitle;
	}


	public float getCouponPrice() {
		return couponPrice;
	}



	public Type getType() {
		return type;
	}


	public String getExpiryDate() {
		return expiryDate;
	}


	public String getDescription() {
		return description;
	}



	public String getCouponStartDate() {
		return couponStartDate;
	}



	public int getUnitsInStock() {
		return unitsInStock;
	}



	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	
	public Long getCouponId() {
		return couponId;
	}



	//-----------------------to string------------------------

	@Override
	public String toString() {
		return "Coupon [couponTitle=" + couponTitle + ", description=" + description
				+ ", couponPrice=" + couponPrice + ", type=" + type + ", couponStartDate="
				+ couponStartDate + ", expiryDate=" + expiryDate + ", unitsInStock=" + unitsInStock + "]";
	}	


}
