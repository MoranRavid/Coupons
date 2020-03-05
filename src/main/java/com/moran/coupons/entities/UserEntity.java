package com.moran.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moran.coupons.enums.UserType;

@Entity
@Table(name= "USERS")
public class UserEntity {

	@Id
	@GeneratedValue
	private long userId;

	@Column(name= "userName", nullable = false, unique = true)
	private String userName;

	@Column(name= "password", nullable = false)
	private String password;

	@Column(name= "email", nullable = false, unique = true)
	private String email;

	@Column(name= "userType", nullable = false)
	private UserType userType;

	@JsonIgnore
	@ManyToOne
	private CompanyEntity company;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="user")
	private List<CouponEntity>coupons;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CustomerEntity customer;



	public UserEntity() {
	}



	public UserEntity(CompanyEntity company) {
		this.company = company;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public UserType getUserType() {
		return userType;
	}



	public void setUserType(UserType userType) {
		this.userType = userType;
	}



	public CompanyEntity getCompany() {
		return company;
	}



	public void setCompany(CompanyEntity company) {
		this.company = company;
	}



	public List<CouponEntity> getUserCoupons() {
		return coupons;
	}



	public void setUserCoupons(List<CouponEntity> userCoupons) {
		this.coupons = userCoupons;
	}



	public CustomerEntity getCustomer() {
		return customer;
	}



	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}



//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + password;
//		return result;
//	}
//
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserEntity other = (UserEntity) obj;
//		if (password != other.password)
//			return false;
//		return true;
//	}
//	
	
	
}
