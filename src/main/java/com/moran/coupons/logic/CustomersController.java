package com.moran.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moran.coupons.dao.ICustomersDao;
import com.moran.coupons.detailsobjects.CustomerDetailsObject;
import com.moran.coupons.entities.CustomerEntity;
import com.moran.coupons.entities.UserEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;

@Controller
public class CustomersController {

	@Autowired
	private ICustomersDao customersDao;

	@Autowired
	private UsersController usersController;

	public CustomersController() {
	}


	//----------------------create customer----------------------

	public void createCustomer(CustomerDetailsObject customer) throws ApplicationException {

		//Customer validations
		validateCustomerDetails(customer);

		//set user details
		customer = setUserDetails(customer);
		this.usersController.validateUserDetails(customer.getUser());

		//convert customer details object to customer entity
		CustomerEntity customerEntity = convertToCustomerEntity(customer);

		//Then create the customer
		try {
			this.customersDao.save(customerEntity);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not create a customer");
		}
	}



	//---------------------update customer-----------------------



	public void updateCustomer(CustomerDetailsObject customer) throws ApplicationException {
		//validations
		validateCustomerDetails(customer);

		//First send the user details to update user
		customer = setUserDetails(customer);
		this.usersController.updateUser(customer.getUser());

		//convert customer details object to customer entity
		CustomerEntity customerEntity = convertToCustomerEntity(customer);
		customerEntity.setCustomerId(customer.getUser().getUserId());

		// sending the customer to the dao
		this.customersDao.save(customerEntity);
	}



	//---------------------remove customer-----------------------

	public void removeCustomer(long userId) throws ApplicationException {
		try {
			this.customersDao.deleteById(userId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove customer no." + userId);
		}
	}



	//------------------------get customer/s---------------------------

	public CustomerEntity getCustomerByCustomerId(long customerId) throws ApplicationException {
		CustomerEntity customer = new CustomerEntity();
		try {
			customer = this.customersDao.findById(customerId).get();			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the customer");
		}

		if (customer == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_CUSTOMER, "there is no customer with the id: "+ customerId);
		}

		return customer;
	}




	public List<CustomerEntity> getAllCustomers() throws ApplicationException {
		try {
			List<CustomerEntity>customersList = (List<CustomerEntity>) customersDao.findAll();
			return customersList;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the customers list");
		}
	}




	//------------------validate customer details---------------
	private void validateCustomerDetails(CustomerDetailsObject customer) throws ApplicationException {
		if (!isAmountOfKidsValid(customer.getAmountOfKids())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_AMOUNT_OF_KIDS, "Amount of kids is invalid");
		}		
		if (!isAddressValid(customer.getAddress())) {
			throw new ApplicationException(ErrorTypes.INVALID_ADDRESS, "Address is invalid");
		}
		if (!isPhoneValid(customer.getPhone())) {
			throw new ApplicationException(ErrorTypes.INVALID_PHONE_NUMBER, "Phone number is invalid");
		}
	}



	//-----------------------------validations-----------------------
	private boolean isPhoneValid(String phone) {
		if (phone.charAt(0)!= '0') {
			return false;
		}
		if (phone.length()<9) {
			return false;
		}
		if (phone.length()>10) {
			return false;
		}
		if (!phone.matches("[0-9]+")) {
			return false;
		}
		return true;
	}


	private boolean isAddressValid(String address) {
		if (address == null) {
			return false;
		}
		if (address == "") {
			return false;
		}
		if (address.length() < 2) {
			return false;
		}
		return true;
	}


	private boolean isAmountOfKidsValid(int amountOfKids) {
		if (amountOfKids<0) {
			return false;
		}
		return true;
	}





	//----------------------convert to customer entity-------------------

	private CustomerEntity convertToCustomerEntity(CustomerDetailsObject customer) {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setAddress(customer.getAddress());
		customerEntity.setAmountOfKids(customer.getAmountOfKids());
		customerEntity.setIsMarried(customer.getIsMarried());
		customerEntity.setPhone(customer.getPhone());


		UserEntity userEntity = this.usersController.convertToUserEntity(customer.getUser());
		customerEntity.setUser(userEntity);

		return customerEntity;
	}


	
	//------------------------set customer type function--------------
	
	private CustomerDetailsObject setUserDetails(CustomerDetailsObject customer) {
		customer.getUser().setUserType(UserType.CUSTOMER);
		customer.getUser().setCompanyId(null);
		return customer;
	}
}
