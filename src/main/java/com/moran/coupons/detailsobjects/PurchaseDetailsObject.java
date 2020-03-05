package com.moran.coupons.detailsobjects;

public class PurchaseDetailsObject {
	private long couponId;
	private int amountOfItems;


	//-----------------------------constructors---------------------------
	public PurchaseDetailsObject() {
	}


	public PurchaseDetailsObject(long couponId, int amountOfItems) {
		this.couponId = couponId;
		this.amountOfItems = amountOfItems;
	}



	//----------------------------getters and setters--------------------------



	public long getCouponId() {
		return couponId;
	}



	public int getAmountOfItems() {
		return amountOfItems;
	}



	@Override
	public String toString() {
		return "Purchase [couponId=" + couponId + ", amountOfItems=" + amountOfItems + "]";
	}	

}
