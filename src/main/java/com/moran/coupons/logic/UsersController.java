package com.moran.coupons.logic;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moran.coupons.dao.ICompaniesDao;
import com.moran.coupons.dao.IUsersDao;
import com.moran.coupons.data.SuccessfulLoginData;
import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.detailsobjects.UserDetailsObject;
import com.moran.coupons.detailsobjects.UserLoginDetails;
import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.UserEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;

@Controller
public class UsersController {

	private final static String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private static final String ENCRYPTION_TOKEN_SALT = "AASDFHSJFHJHKAAAAA-3423@#$@#$";

	@Autowired
	private IUsersDao usersDao;

	@Autowired
	private ICompaniesDao companiesDao;

	@Autowired
	private CacheController cacheController;


	public UsersController() {
	}



	//----------------------create user----------------------

	public void createUser(UserDetailsObject user) throws ApplicationException {
		// validations
		if (this.isExistByEmail(user.getEmail())) {
			throw new ApplicationException(ErrorTypes.DUPLICATE_EMAIL, "The email "+user.getEmail()+ " is already exist");
		}

		
		validateUserDetails(user);

		//convert from user details object to user entity
		UserEntity userEntity = convertToUserEntity(user);

		try {
			this.usersDao.save(userEntity);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not create a user");
		}
	}




	//---------------------update user-----------------------

	public void updateUser(UserDetailsObject user) throws ApplicationException {
		//email validations - relevant only for update
		emailValidations(user);

		validateUserDetails(user);

		//convert from user details object to user entity
		UserEntity userEntity = convertToUserEntity(user);

		try {
			this.usersDao.save(userEntity);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not update the user");
		}
	}







	//---------------------remove user-----------------------

	public void removeUser(long userId) throws ApplicationException {
		try {
			this.usersDao.deleteById(userId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove user no." + userId);
		}
	}



	//------------------------get user/s---------------------------

	public UserEntity getUserByUserId (long userId) throws ApplicationException{
		UserEntity user = new UserEntity();
		try {			
			user = this.usersDao.findById(userId).get();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the user");
		}


		if (user == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_USER, "there is no user with the id: "+ userId);
		}

		return user;
	}




	public List<UserEntity>getAllUsers()throws ApplicationException{
		try {
			List<UserEntity> usersList = (List<UserEntity>) usersDao.findAll();
			return usersList;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the users list");
		}
	}



		public List<UserEntity>getUsersByCompanyId(long companyId)throws ApplicationException{
			CompanyEntity companyEntity = new CompanyEntity(companyId);
			try {
				List<UserEntity>companyUsersList = this.usersDao.findByCompany(companyEntity);
				return companyUsersList;		
			} catch (Exception e) {
				throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the users list");
			}
		}




	//--------------------------login-------------------------------


	public SuccessfulLoginData login(UserLoginDetails userLoginDetails) throws ApplicationException {
		UserEntity userEntity = new UserEntity();
		try {
			userEntity = this.usersDao.fetchUsers(userLoginDetails.getEmail(), String.valueOf(userLoginDetails.getPassword().hashCode()));			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Your email or password is incorrect");
		}
		UserAfterLoginData userLoginData = new UserAfterLoginData();
		if (!userEntity.getUserType().equals(UserType.SELLER)) {
			userLoginData.setCompanyId(null);
		}
		else {			
			userLoginData.setCompanyId(userEntity.getCompany().getCompanyId());
		}
		userLoginData.setId(userEntity.getUserId());
		userLoginData.setUserType(userEntity.getUserType());
		int token = generateToken(userLoginDetails);

		// The following 2 lines are equivalent, just 2 techniques on how to convert an int --> String
		//cacheController.put(token+"", userLoginData);
		this.cacheController.put(String.valueOf(token), userLoginData);

		return new SuccessfulLoginData(token, userLoginData.getUserType());
	}



	private int generateToken(UserLoginDetails userLoginDetails) {
		String text = userLoginDetails.getEmail() + Calendar.getInstance().getTime().toString() + ENCRYPTION_TOKEN_SALT;
		return text.hashCode();
	}





	//----------------------convert to user entity-------------------

	public UserEntity convertToUserEntity(UserDetailsObject user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(String.valueOf(user.getPassword().hashCode()));
		userEntity.setUserName(user.getUserName());
		userEntity.setUserType(user.getUserType());

		if (user.getUserId() != null) {
			userEntity.setUserId(user.getUserId());
		}

		if (user.getCompanyId() == null) {
			userEntity.setCompany(null);
		}
		else {	
			// create company entity only with company id to use less connections with the DB
			CompanyEntity companyEntity = new CompanyEntity();
			companyEntity.setCompanyId(user.getCompanyId());
			userEntity.setCompany(companyEntity);
		}

		return userEntity;
	}



	//-----------------------------validate user details---------------

	public void validateUserDetails(UserDetailsObject user) throws ApplicationException {

		if (!isUsreNameValid(user.getUserName())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_USER_INPUT, "The name is invalid");
		}

		if (!isPasswordValid(user.getPassword())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_USER_INPUT, "The password is invalid");
		}

		if (!isEmailValid(user.getEmail())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_USER_INPUT, "The email is invalid");
		}
		if (!isUserTypeValid(user.getUserType())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_USER_INPUT, "The user type is invalid");
		}
		if (!isCompanyIdValid(user.getCompanyId())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_USER_INPUT, "The company id does not exist");
		}
	}



	//relevant only for update
	private void emailValidations(UserDetailsObject user) throws ApplicationException {
		// check if he changed to email that already exist
		UserEntity dbUser = new UserEntity();
		dbUser = this.getUserByUserId(user.getUserId());

		// if the email is equal to the origin email everything OK, i can continue to the next validations
		if (!dbUser.getEmail().equals (user.getEmail())) {
			// if not, i want to check that he didn't used email that already exist
			if (this.isExistByEmail(user.getEmail())) {
				throw new ApplicationException(ErrorTypes.DUPLICATE_EMAIL, "You cannot change the email to one that already exist");	
			}
		}		
	}




	//----------------------------validations------------------------

	private boolean isCompanyIdValid(Long companyId) throws ApplicationException{

		// relevant for admin and customer users
		if (companyId==null) {
			return true;
		}

		// relevant for company user
		CompanyEntity company = this.companiesDao.findById(companyId).get();
		if (company == null) {
			return false;
		}
		return true;
	}




	private boolean isUserTypeValid(UserType userType) {
		// the user has to choose one of the types i suggested him
		if (userType == null) {
			return false;	
		}
		return true;
	}




	private boolean isEmailValid(String email) {
		if (email == null) {
			return false;
		}
		if (email == "") {
			return false;
		}

		// This line converts the regex code into the desired pattern
		Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

		//This line checks if the inserted email follows the previously created pattern
		Matcher matcher = emailPattern.matcher(email);

		//It will return true if the the email is valid, false if it invalid
		return matcher.find();
	}



	private boolean isPasswordValid(String password) throws ApplicationException {
		// password has to contain between 6 to 12 chars,
		//at least one capital letter
		//at least one lower letter
		//at least one number
		lengthValidation(password);
		hasCapitalLetterValidation(password);
		hasLowerCaseLetterValidation(password);
		hasNumberValidation(password);
		return true;
	}



	private void hasNumberValidation(String password) throws ApplicationException {
		boolean isHasNumber = false;
		int index = 0;
		while(index < password.length() && !isHasNumber) {
			if (password.charAt(index)>='0' && password.charAt(index)<='9') {
				isHasNumber = true;
			}
			index++;
		}
		if (!isHasNumber) {
			throw new ApplicationException(ErrorTypes.MISSIMG_PASSWORD_NUMBER, "The password does not contain a number");
		}
	}



	private void hasLowerCaseLetterValidation(String password) throws ApplicationException {
		boolean isHasLowerCaseLetter = false;
		int index = 0;
		while(index < password.length() && !isHasLowerCaseLetter) {
			if (password.charAt(index)>='a' && password.charAt(index)<='z') {
				isHasLowerCaseLetter = true;
			}
			index++;
		}

		if (!isHasLowerCaseLetter) {
			throw new ApplicationException(ErrorTypes.MISSIMG_PASSWORD_LOWER_CASE_LETTER, "The password does not contain a lower case letter");
		}

	}



	private void hasCapitalLetterValidation(String password) throws ApplicationException {
		boolean isHasCapitalLetter = false;
		int index = 0;
		while(index < password.length() && !isHasCapitalLetter) {
			if (password.charAt(index)>='A' && password.charAt(index)<='Z') {
				isHasCapitalLetter = true;
			}
			index++;
		}
		if (!isHasCapitalLetter) {
			throw new ApplicationException(ErrorTypes.MISSIMG_PASSWORD_CAPITAL_LETTER, "The password does not contain a capital letter");
		}
	}



	private void lengthValidation(String password) throws ApplicationException {
		if (password.length()<6) {
			throw new ApplicationException(ErrorTypes.PASSWORD_TOO_SHORT, "Password is too short");
		}

		if (password.length()>12) {
			throw new ApplicationException(ErrorTypes.PASSWORD_TOO_LONG, "Password is too long");
		}

	}



	private boolean isUsreNameValid(String userName) {
		if (userName == null) {
			return false;
		}
		if (userName == "") {
			return false;
		}
		if (userName.length() < 2) {
			return false;
		}
		return true;
	}




	//---------------------existence check-----------------------------

	private boolean isExistByEmail(String email) throws ApplicationException {
		try {
			if (this.usersDao.existsByEmail(email)) {
				return true;
			}
			return false;	
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "faild to check if the email- " + email + " is exist");
		}
	}

}

