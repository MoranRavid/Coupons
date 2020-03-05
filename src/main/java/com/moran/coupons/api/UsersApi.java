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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moran.coupons.data.SuccessfulLoginData;
import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.detailsobjects.UserDetailsObject;
import com.moran.coupons.detailsobjects.UserLoginDetails;
import com.moran.coupons.entities.UserEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.logic.UsersController;


@RestController
@RequestMapping("/users")
public class UsersApi {

	@Autowired
	private UsersController usersController;

	public UsersApi() {
	}



	//Only for ADMIN and SELLER users.
	//  URL : http://localhost:8080/users/login
	@PostMapping("/login")
	public SuccessfulLoginData login(@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		return usersController.login(userLoginDetails);
	}

	// ---------------------------
	// CRUD
	// -------------------------

	//  URL : http://localhost:8080/users?token=
	//only the admin could create user
	@PostMapping
	public void createUser(@RequestBody UserDetailsObject user, HttpServletRequest request) throws ApplicationException {
		//check if the request indeed came from the admin
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.usersController.createUser(user);			
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}



	//the admin updating seller\admin
	//  URL : http://localhost:8080/users
	@PutMapping
	public void updateUser(@RequestBody UserDetailsObject user, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.usersController.updateUser(user);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}




	//  URL : http://localhost:8080/users/byMyDetails
	@PutMapping("{byMyDetails}")
	public void updateMyUserDetails(@RequestBody UserDetailsObject user, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.SELLER)||userType.equals(UserType.ADMIN)) {
			user.setUserId(userData.getId());
			user.setCompanyId(userData.getCompanyId());
			this.usersController.updateUser(user);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}

	
	
	// http://localhost:8080/users/10
	//only the admin can remove user
	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable("userId") long userId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.usersController.removeUser(userId);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}



	// http://localhost:8080/users/12
	@GetMapping("{userId}")
	public UserEntity getUser(@PathVariable("userId") long userId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		UserEntity user = new UserEntity();
		if (userType.equals(UserType.ADMIN)) {
			user = this.usersController.getUserByUserId(userId);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		return user;
	}


	// http://localhost:8080/users/getMyDetails
	@GetMapping("/getMyDetails")
	public UserEntity getMyUserDetails(HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		UserEntity user = new UserEntity();
		if (!userType.equals(UserType.CUSTOMER)) {
			long userId = userData.getId();
			user = this.usersController.getUserByUserId(userId);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		return user;
	}


	//  URL : http://localhost:8080/users
	//only the admin could get the all users list
	@GetMapping
	public List<UserEntity> getAllUsers(HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<UserEntity> users = this.usersController.getAllUsers();
			return users;
		}
	}


	// URL : http://localhost:8080/users/byCompanyId?companyId=1
	@GetMapping("/byCompanyId")
	public List<UserEntity> getUsersByCompanyId(@RequestParam("companyId") long companyId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<UserEntity>companyUsers = this.usersController.getUsersByCompanyId(companyId);
			return companyUsers;
		}
	}



	// URL : http://localhost:8080/users/myCompany
	@GetMapping("/myCompany")
	public List<UserEntity> getMyCompanyUsers(HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {				
			long companyId = userData.getCompanyId();
			List<UserEntity>companyUsers = this.usersController.getUsersByCompanyId(companyId);
			return companyUsers;
		}
	}
}
