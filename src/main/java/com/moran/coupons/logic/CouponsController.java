package com.moran.coupons.logic;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moran.coupons.dao.ICouponsDao;
import com.moran.coupons.detailsobjects.CouponDetailsObject;
import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.CouponEntity;
import com.moran.coupons.entities.UserEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.Type;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.utils.DateUtils;

@Controller
public class CouponsController {

	@Autowired
	private ICouponsDao couponsDao;

	@Autowired
	private UsersController usersController;


	public CouponsController() {
	}


	//----------------------create coupon----------------------

	public void createCoupon(CouponDetailsObject coupon) throws ApplicationException {
		//title validation
		if (this.existsByCouponTitle(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorTypes.DUPLICATE_COUPON_TITLE, "There is already a coupon by the name '" + coupon.getCouponTitle()+"'");	
		}

		//other validations
		validateCouponDetails(coupon);

		//convert coupon details object to coupon entity
		CouponEntity couponEntity = convertToCouponEntity(coupon);

		try {
			//sending the coupon to the dao
			couponsDao.save(couponEntity);			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not create a coupon");
		}
	}





	//---------------------update coupon------------------------


	public void updateCoupon(CouponDetailsObject coupon, long companyId) throws ApplicationException{
		CouponEntity dbCoupon = this.getCouponByCouponId(coupon.getCouponId());	

		//check if the company id is equals to the company of the user who send the update request
		companyValidation(companyId, dbCoupon);

		//specific to update
		titleUpdateValidation(dbCoupon, coupon);

		//other validations
		validateCouponDetails(coupon);

		//convert coupon details object to coupon entity
		CouponEntity couponEntity = convertToCouponEntity(coupon);

		try {
			this.couponsDao.save(couponEntity);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not update the coupon");
		}
	}




	//-----------------------remove coupon------------------------------------


	public void removeCoupon(long couponId, long companyId) throws ApplicationException{
		CouponEntity dbCoupon = this.getCouponByCouponId(couponId);

		//Check if the user is authorized
		companyValidation(companyId, dbCoupon);

		try {
			this.couponsDao.deleteById(couponId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove coupon no." + couponId);
		}
	}




		//-----------------------------remove expired coupons-----------
		
		public void removeExpiredCoupons() throws ApplicationException{
			Date today = new Date(Calendar.getInstance().getTime().getTime());
			try {
				couponsDao.deleteByExpiryDateLessThan(today);	
			} catch (Exception e) {
				throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove the expired coupons");
			}
		}
		



	//-----------------------------get coupon/s-----------------------

	public CouponEntity getCouponByCouponId(long couponId) throws ApplicationException {
		CouponEntity coupon = new CouponEntity();
		try {
			coupon = this.couponsDao.findById(couponId).get();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupon");
		}


		if (coupon == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_COUPON, "there is no coupon with the ID: "+ couponId);
		}

		return coupon;

	}



	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		try {
			List<CouponEntity> couponsList = (List<CouponEntity>) couponsDao.findAll();
			return couponsList;			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons list");
		}
	}



		public List<CouponEntity> getCompanyCouponsByCompanyId(long companyId) throws ApplicationException {
			try {
				CompanyEntity companyEntity = new CompanyEntity(companyId);
//				UserEntity userEntity = new UserEntity(companyEntity);
				List<CouponEntity> companyCoupons = couponsDao.companyCoupons(companyEntity);
				return companyCoupons;		
			} catch (Exception e) {
				throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons list");
			}
		}




	public List<CouponEntity> getCouponsByType(Type type) throws ApplicationException {
		try {
			List<CouponEntity> couponsList = couponsDao.findByType(type);
			return couponsList;		
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons list");
		}
	}




	//-----------------------------existence check------------------

	public boolean existsByCouponTitle(String couponTitle) throws ApplicationException {
		try {
			//title validation
			if (this.couponsDao.existsByCouponTitle(couponTitle)) {
				return true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "faild to check if coupon with the title " + couponTitle + " is exist");
		}
		return false;
	}




	//----------------------convert to coupon entity-------------------

	public CouponEntity convertToCouponEntity(CouponDetailsObject coupon) {
		
		CouponEntity couponEntity = new CouponEntity();
		couponEntity.setCouponPrice(coupon.getCouponPrice());
		couponEntity.setCouponTitle(coupon.getCouponTitle());
		couponEntity.setDescription(coupon.getDescription());
		couponEntity.setType(coupon.getType());
		couponEntity.setUnitsInStock(coupon.getUnitsInStock());
		if (coupon.getCouponId() != null) {
			couponEntity.setCouponId(coupon.getCouponId());
		}
		
		// convert String variable to sql Date variable
		Date sqlStartDate = Date.valueOf(coupon.getCouponStartDate());
		couponEntity.setCouponStartDate(sqlStartDate);
		
		// convert String variable to sql Date variable
		Date sqlExpiryDate = Date.valueOf(coupon.getExpiryDate());
		couponEntity.setExpiryDate(sqlExpiryDate);

		// create user entity only with user id to use less connections with the DB
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(coupon.getUserId());
		couponEntity.setUser(userEntity);


		return couponEntity;

	}




	//----------------------validate coupon details----------------------------

	private void validateCouponDetails(CouponDetailsObject coupon) throws ApplicationException {
		if (!isTitleValid(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON_TITLE, "coupon title is invalid");
		}
		if (!isPriceValid(coupon.getCouponPrice())) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON_PRICE, "the price is not valid");
		}
		if (!isTypeValid(coupon.getType())) {
			throw new ApplicationException(ErrorTypes.INVALID_TYPE, "type is invalid");
		}
		dateValidations(coupon.getCouponStartDate(), coupon.getExpiryDate());

		if (!isUnitsInStockValid(coupon.getUnitsInStock())) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_UNITS_AMOUNT, "Units amount is invalid");
		}
	}




	//--------------------------validations--------------------

	public void dateValidations(String couponStartDate, String expiryDate) throws ApplicationException {
		if (!isStartDateValid(couponStartDate)) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_START_DATE, "Start date is invalid");
		}
		if (!isExpiryDateValid(expiryDate)) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_EXPIRY_DATE, "Expiry date is invalid");
		}
		// check if the start date is after the expire date, if so throw exception
		if (LocalDate.parse(couponStartDate).isAfter(LocalDate.parse(expiryDate))) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_DATES, "Expiry date cannot be before start day");
		}
	}


	private void titleUpdateValidation(CouponEntity dbCoupon, CouponDetailsObject coupon) throws ApplicationException {
		// if the title is equal to the origin title everything OK, i can continue to the next validations
		if (!dbCoupon.getCouponTitle().equals (coupon.getCouponTitle())) {
			// if not, i want to check that he didn't used title that already exist
			if (this.existsByCouponTitle(coupon.getCouponTitle())) {
				throw new ApplicationException(ErrorTypes.DUPLICATE_COUPON_TITLE, "There is already a coupon by the name '" + coupon.getCouponTitle()+"'");	
			}
		}
	}


	private void companyValidation(long companyId, CouponEntity dbCoupon) throws ApplicationException {
		UserEntity dbUser = this.usersController.getUserByUserId(dbCoupon.getUser().getUserId());
		if (dbUser.getCompany().getCompanyId()!=companyId) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "you're not authorized");
		}
	}


	private boolean isTitleValid(String title) {
		if (title == null) {
			return false;
		}

		if (title == "") {
			return false;
		}

		if (title.length()<2) {
			return false;
		}
		return true;
	}


	private boolean isPriceValid(float price) {
		if (price<=0) {
			return false;
		}
		return true;
	}


	private boolean isTypeValid(Type type) {
		// the user has to choose one of the types I suggested him
		if (type == null) {
			return false;
		}
		return true;
	}


	private boolean isExpiryDateValid(String strExpiryDate) throws ApplicationException{
		try {
			if (strExpiryDate == null) {
				return false;
			}
			// convert from string to localDate
			LocalDate localDateExp = LocalDate.parse(strExpiryDate);
			// validation that the date has not passed
			if (DateUtils.isDatePassed(localDateExp)) {
				return false;
			}
			return true;
		}catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.ILLIGAL_EXPIRY_DATE, "The expiry date is invalid");
		}
	}




	private boolean isStartDateValid(String stringStartDate) throws ApplicationException {
		try {
			if (stringStartDate == null) {
				return false;
			}
			// convert from string to localDate
			// If the date is not in the format yyyy-MM-dd it will throw exception
			LocalDate.parse(stringStartDate);
			return true;
		}catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.ILLIGAL_START_DATE, "The start date is invalid");
		}

	}





	private boolean isUnitsInStockValid(Integer unitsInStock) {
		if (unitsInStock == null) {
			return false;
		}
		if (unitsInStock <= 0) {
			return false;
		}
		return true;
	}



}
