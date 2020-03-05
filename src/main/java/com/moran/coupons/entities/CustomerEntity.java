package com.moran.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "CUSTOMERS")
public class CustomerEntity {

	@Id
	private long customerId;

	@Column(name="amountOfKids", nullable = true)
	private int amountOfKids;

	@Column(name="isMarried", nullable = false)
	private boolean isMarried;

	@Column(name="address", nullable = false)
	private String address;

	@Column(name="phone", nullable = false)
	private String phone;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapsId
	private UserEntity user;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="customer")
	private List<PurchaseEntity> purchases;



	//-----------------------constructors----------------------------------------------------

	public CustomerEntity() {
	}



	public CustomerEntity(long customerId) {
		this.customerId = customerId;
	}

	//-----------------------getters and setters-------------------------------------------------


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}


	public int getAmountOfKids() {
		return amountOfKids;
	}


	public void setAmountOfKids(int amountOfKids) {
		this.amountOfKids = amountOfKids;
	}


	public boolean getIsMarried() {
		return isMarried;
	}


	public void setIsMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}



	public long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}



	public List<PurchaseEntity> getPurchases() {
		return purchases;
	}



	public void setPurchases(List<PurchaseEntity> purchases) {
		this.purchases = purchases;
	}
	
	
}
