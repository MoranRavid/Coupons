package com.moran.coupons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PURCHASES")
public class PurchaseEntity {

	@Id
	@GeneratedValue
	private long purchaseId;
	
	@JsonIgnore
	@ManyToOne
	private CouponEntity coupon;
	
	@JsonIgnore
	@ManyToOne
	private CustomerEntity customer;
	
	@Column(name= "amountOfItems", nullable = false)
	private int amountOfItems;
	
	@Column(name= "timeStamp", nullable = false)
	private String timeStamp;
	
	@Column(name= "totalPrice", nullable = false)
	private float totalPrice;

	
	
	//-----------------------constructor------------------
	
	
	public PurchaseEntity() {
	}

	
	
	//--------------------getters and setters--------------

	public long getPurchaseId() {
		return purchaseId;
	}


	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}


	public CouponEntity getCoupon() {
		return coupon;
	}


	public void setCoupon(CouponEntity coupon) {
		this.coupon = coupon;
	}


	public CustomerEntity getCustomer() {
		return customer;
	}


	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}


	public int getAmountOfItems() {
		return amountOfItems;
	}


	public void setAmountOfItems(int amountOfItems) {
		this.amountOfItems = amountOfItems;
	}


	public String getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}


