package com.moran.coupons.logic;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moran.coupons.dao.ICouponsDao;
import com.moran.coupons.dao.IPurchasesDao;
import com.moran.coupons.detailsobjects.PurchaseDetailsObject;
import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.CouponEntity;
import com.moran.coupons.entities.CustomerEntity;
import com.moran.coupons.entities.PurchaseEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.utils.DateUtils;

@Controller
public class PurchasesController {

	@Autowired
	private IPurchasesDao purchasesDao;

	@Autowired
	private CouponsController couponsController;

	@Autowired
	private ICouponsDao couponsDao;

	public PurchasesController() {
	}



	//-----------------------------create purchase---------------------------------------

	public void createPurchase(PurchaseDetailsObject purchase, long userId) throws ApplicationException {
		CouponEntity coupon = this.couponsController.getCouponByCouponId(purchase.getCouponId());

		//validation
		validatePurchaseDetails(purchase, coupon);

		//convert purchase details object to purchase entity and add some details
		PurchaseEntity purchaseEntity = convertToPurchaseEntity(purchase, coupon, userId);

		//send to transactional function that save the purchase and update the coupon amount in stock
		savePurchaseAndUpdateCouponAmount(purchaseEntity, coupon);
	}


	@Transactional
	//A transactional part of create purchase
	private void savePurchaseAndUpdateCouponAmount(PurchaseEntity purchaseEntity, CouponEntity coupon) {
		try {
			this.purchasesDao.save(purchaseEntity);

			//update new amount of units in the stock to the coupon
			int unitsInStock = coupon.getUnitsInStock() - purchaseEntity.getAmountOfItems();
			coupon.setUnitsInStock(unitsInStock);
			this.couponsDao.save(coupon);

		} catch (Exception e) {

		}
	}



	//-----------------------------remove purchase---------------------------------------

	public void deletePurchase(long purchaseId) throws ApplicationException {
		try {
			this.purchasesDao.deleteById(purchaseId);			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove purchase no." + purchaseId);
		}
	}




	//-----------------------------get purchase/s---------------------------------------

	public PurchaseEntity getPurchaseByPurchaseId(long purchaseId) throws ApplicationException {
		PurchaseEntity purchase = new PurchaseEntity();
		try {
			purchase = this.purchasesDao.findById(purchaseId).get();	
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the purchase");
		}

		if (purchase == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_PURCHASE, "there is no purchase with the id: "+ purchaseId);
		}

		return purchase;
	}



	public List<PurchaseEntity> getCustomerPurchases(long customerId) throws ApplicationException {
		CustomerEntity customerEntity = new CustomerEntity(customerId);
		try {
			List<PurchaseEntity> purchasesList = this.purchasesDao.findByCustomer(customerEntity);
			return purchasesList;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the purchases list");
		}
	}



	public List<PurchaseEntity> getAllPurchases() throws ApplicationException {
		try {
			List<PurchaseEntity> purchasesList = (List<PurchaseEntity>) this.purchasesDao.findAll();
			return purchasesList;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the purchases list");
		}

	}



	public List<PurchaseEntity>getPurchasesByCompanyId (long companyId) throws ApplicationException{
		CompanyEntity companyEntity = new CompanyEntity(companyId);
		try {
			List<PurchaseEntity> companyPurchasesList = this.purchasesDao.companyPurchases(companyEntity);
			return companyPurchasesList;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the purchases list");
		}

	}


	//----------------------validate purchase details----------------------------

	private void validatePurchaseDetails(PurchaseDetailsObject purchase, CouponEntity coupon) throws ApplicationException {
		// Existence check 
		if (coupon == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_COUPON, "the coupon you've tried to purchase deos not exist");
		}

		// date validations
		this.couponsController.dateValidations(coupon.getCouponStartDate().toString(), coupon.getExpiryDate().toString());
		dateValidations(coupon);

		//Stock validation
		if (!isAmountOfItemsValid(coupon, purchase)) {
			throw new ApplicationException(ErrorTypes.ILLIGAL_AMOUNT_OF_ITEMS, "You've tried to purchase more items than exists");
		}
	}






	//--------------------------validations--------------------

	private void dateValidations(CouponEntity coupon) throws ApplicationException {
		// check if the coupon is already available to buy
		if (!isCouponStartDateValid(coupon)) {
			throw new ApplicationException(ErrorTypes.COUPON_IS_NOT_AVAILABLE_YET, "The coupon is not yet available");
		}
		//check the expire date has not passed
		if (!isCouponExpiryDateValid(coupon)) {
			throw new ApplicationException(ErrorTypes.EXPIRED_COUPON, "The coupon date is expired");
		}	
	}


	private float calculateTotalPrice(CouponEntity coupon, PurchaseDetailsObject purchase) {
		float couponPrice = coupon.getCouponPrice();
		float totalPrice = purchase.getAmountOfItems() * couponPrice;
		return totalPrice;
	}


	private boolean isCouponStartDateValid(CouponEntity coupon) {
		// convert from string to localDate
		LocalDate localDateStart = LocalDate.parse(coupon.getCouponStartDate().toString());
		if (DateUtils.isTodayBeforeDate(localDateStart)) {
			return false;
		}
		return true;
	}



	private boolean isCouponExpiryDateValid(CouponEntity coupon) {
		// convert from string to localDate
		LocalDate localDateExp = LocalDate.parse(coupon.getExpiryDate().toString());
		if (DateUtils.isDatePassed(localDateExp)) {
			return false;
		}
		return true;
	}




	private boolean isAmountOfItemsValid(CouponEntity coupon, PurchaseDetailsObject purchase) {
		if (purchase.getAmountOfItems()<=0) {
			return false;
		}
		if ((coupon.getUnitsInStock() - purchase.getAmountOfItems()) < 0) {
			return false;
		}
		return true;
	}





	//-----------convert purchase details object to purchase entity----------------
	private PurchaseEntity convertToPurchaseEntity(PurchaseDetailsObject purchase, CouponEntity coupon, long userId) {
		PurchaseEntity purchaseEntity = new PurchaseEntity();

		//Calculate total price
		float totalPrice = calculateTotalPrice(coupon, purchase);
		purchaseEntity.setTotalPrice(totalPrice);

		//create timeStamp and convert it to string
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();  
		purchaseEntity.setTimeStamp(formatter.format(date));

		// create customer entity only with customer id to use less connections with the DB
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerId(userId);
		purchaseEntity.setCustomer(customerEntity);
		purchaseEntity.setAmountOfItems(purchase.getAmountOfItems());


		// create coupon entity only with coupon id to use less connections with the DB
		CouponEntity couponEntity = new CouponEntity();
		couponEntity.setCouponId(purchase.getCouponId());
		purchaseEntity.setCoupon(couponEntity);

		return purchaseEntity;
	}
}

