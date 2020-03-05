package com.moran.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moran.coupons.enums.Type;

@Entity
@Table(name= "COMPANIES")
public class CompanyEntity {

	@Id
	@GeneratedValue
	private long CompanyId;

	@Column(name="companyName", unique = true, nullable = false)
	private String companyName;

	@Column(name="address", nullable = false)
	private String address;

	@Column(name="phone", nullable = false)
	private String phone;

	@Column(name="type", nullable = false)
	private Type type;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="company")
	List<UserEntity> users;




	//--------------------constructors-------------------------------------


	public CompanyEntity() {
	}



	public CompanyEntity(String companyName, String address, String phone, Type type) {
		this.companyName = companyName;
		this.address = address;
		this.phone = phone;
		this.type = type;
	}


	public CompanyEntity(long companyId, String companyName, String address, String phone, Type type) {
		this(companyName, address, phone, type);
		this.CompanyId = companyId;

	}


	
	public CompanyEntity(long companyId) {
		CompanyId = companyId;
	}



	//-------------------getters and setters-------------------------------


	public long getCompanyId() {
		return CompanyId;
	}


	public void setCompanyId(long id) {
		this.CompanyId = id;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String name) {
		this.companyName = name;
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


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public List<UserEntity> getUsers() {
		return users;
	}



	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}



	@Override
	public String toString() {
		return "Company [CompanyId=" + CompanyId + ", name=" + companyName + ", address=" + address + ", phone=" + phone
				+ ", type=" + type + "]";
	}

}
