package com.moran.coupons.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moran.coupons.enums.Type;

@Entity
@Table(name= "COUPONS")
public class CouponEntity {

	@Id
	@GeneratedValue
	private Long couponId;

	@Column(name="couponTitle", unique = true, nullable = false)
	private String couponTitle;

	@Column(name="description")
	private String description;

	@Column(name="couponPrice", nullable = false)
	private float couponPrice;

	@Column(name="type", nullable = false)
	private Type type;

	@Column(name="couponStartDate", nullable = false)
	private Date couponStartDate;

	@Column(name="expiryDate", nullable = false)
	private Date expiryDate;

	@Column(name="unitsInStock", nullable = false)
	private int unitsInStock;

	@JsonIgnore
	@ManyToOne
	private UserEntity user;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="coupon")
	private List<PurchaseEntity> purchases;


	//------------------------constructors------------------------------

	public CouponEntity() {
	}


	public CouponEntity(String couponTitle, String description, UserEntity user, float couponPrice, Type type, Date expiryDate, Date couponStartDate, int unitsInStock) {
		this.couponTitle = couponTitle;
		this.description = description;
		this.user = user;
		this.couponPrice = couponPrice;
		this.type = type;
		this.expiryDate = expiryDate;
		this.couponStartDate = couponStartDate;
		this.unitsInStock = unitsInStock;
	}

	public CouponEntity(Long couponId, String couponTitle, String description, UserEntity user, float couponPrice, Type type, Date expiryDate, Date couponStartDate, int unitsInStock) {
		this(couponTitle, description, user, couponPrice, type, expiryDate, couponStartDate, unitsInStock);
		this.couponId = couponId;
	}



	public CouponEntity(UserEntity user) {
		this.user = user;
	}

	//-----------------------getters and setters-------------------------

	public Long getCouponId() {
		return couponId;
	}


	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}


	public String getCouponTitle() {
		return couponTitle;
	}


	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}


	public float getCouponPrice() {
		return couponPrice;
	}


	public void setCouponPrice(float couponPrice) {
		this.couponPrice = couponPrice;
	}



	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCouponStartDate() {
		return couponStartDate;
	}


	public void setCouponStartDate(Date couponStartDate) {
		this.couponStartDate = couponStartDate;
	}


	public int getUnitsInStock() {
		return unitsInStock;
	}


	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}



	public List<PurchaseEntity> getPurchases() {
		return purchases;
	}


	public void setPurchases(List<PurchaseEntity> purchases) {
		this.purchases = purchases;
	}


	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	//-----------------------to string------------------------

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponTitle=" + couponTitle + ", description=" + description
				+ ", user=" + user + ", couponPrice=" + couponPrice + ", type=" + type + ", couponStartDate="
				+ couponStartDate + ", expiryDate=" + expiryDate + ", unitsInStock=" + unitsInStock + "]";
	}
}
