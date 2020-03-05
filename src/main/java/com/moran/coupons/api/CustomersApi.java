package com.moran.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.detailsobjects.CustomerDetailsObject;
import com.moran.coupons.entities.CustomerEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.logic.CustomersController;

@RestController
@RequestMapping("/customers")

public class CustomersApi {

	@Autowired
	private CustomersController customersController;

	public CustomersApi() {
	}


	// ---------------------------
	// CRUD
	// -------------------------

	//  URL : http://localhost:8080/customers
	@PostMapping
	public void createCustomer(@RequestBody CustomerDetailsObject customer) throws ApplicationException {
		this.customersController.createCustomer(customer);
	}



	//  URL : http://localhost:8080/customers
	@PutMapping
	public void updateCustomer(@RequestBody CustomerDetailsObject customer, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			this.customersController.updateCustomer(customer);
		}
	}



	//  URL : http://localhost:8080/customers/myDetails
	@PutMapping("/myDetails")
	public void updateMyCustomerDetails(@RequestBody CustomerDetailsObject customer, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.CUSTOMER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {	
			customer.getUser().setUserId(userData.getId());
			customer.getUser().setUserType(userData.getUserType());
			this.customersController.updateCustomer(customer);
		}
	}



	//----Only admin could delete a customer----
	// http://localhost:8080/customers/10
	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {	
			this.customersController.removeCustomer(customerId);
		}
	}


	// http://localhost:8080/customers/10
	@GetMapping("{customerId}")
	public CustomerEntity getCustomer(@PathVariable("customerId") long customerId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			CustomerEntity customer = this.customersController.getCustomerByCustomerId(customerId);
			return customer;
		}
	}



	// http://localhost:8080/customers/myDetails
	@GetMapping("/myDetails")
	public CustomerEntity getMyDetails(HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.CUSTOMER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			long customerId = userData.getId();
			CustomerEntity customer = this.customersController.getCustomerByCustomerId(customerId);
			return customer;
		}
	}


	//  URL : http://localhost:8080/customers
	@GetMapping
	public List<CustomerEntity> getAllCustomers(HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<CustomerEntity> customers = this.customersController.getAllCustomers();
			return customers;
		}
	}
}
