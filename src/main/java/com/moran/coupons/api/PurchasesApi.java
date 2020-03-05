package com.moran.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.detailsobjects.PurchaseDetailsObject;
import com.moran.coupons.entities.PurchaseEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.logic.PurchasesController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {

	@Autowired
	private PurchasesController purchasesController;

	public PurchasesApi() {
	}



	// ---------------------------
	// CRUD
	// -------------------------

	//  URL : http://localhost:8080/purchases
	@PostMapping
	public void createPurchase(@RequestBody PurchaseDetailsObject purchase, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		//only customer can purchase coupons
		if (!userType.equals(UserType.CUSTOMER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {			
			this.purchasesController.createPurchase(purchase, userData.getId());
		}
	}



	// http://localhost:8080/purchases/10
	@DeleteMapping("{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		//everyone can delete a purchase
		this.purchasesController.deletePurchase(purchaseId);
	}



	// http://localhost:8080/purchases/12
	@GetMapping("{purchaseId}")
	public PurchaseEntity getPurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		//everyone can get a purchase
		PurchaseEntity purchase = this.purchasesController.getPurchaseByPurchaseId(purchaseId);
		return purchase;
	}



	//  URL : http://localhost:8080/purchases
	@GetMapping
	public List<PurchaseEntity> getAllPurchases(HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		//only admin can get the full purchases list
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<PurchaseEntity> purchases = this.purchasesController.getAllPurchases();
			return purchases;
		}
	}



	// URL : http://localhost:8080/purchases/byCompanyId?companyId=1
	@GetMapping("/byCompanyId")
	public List<PurchaseEntity>getPurchasesByCompanyId (long companyId, HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		//only admin can get the company purchases list
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<PurchaseEntity> purchases = this.purchasesController.getPurchasesByCompanyId(companyId);
			return purchases;
		}
	}




	// URL : http://localhost:8080/purchases/byMyCompany?token=
	@GetMapping("/byMyCompany")
	public List<PurchaseEntity>getPurchasesByMyCompany (HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		//only seller can get his company purchases list
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			long companyId = userData.getCompanyId();
			List<PurchaseEntity> purchases = this.purchasesController.getPurchasesByCompanyId(companyId);
			return purchases;
		}
	}
	
	
	// URL : http://localhost:8080/purchases/byCustomerId?customerId=1
		@GetMapping("/byCustomerId")
		public List<PurchaseEntity>getCustomerPurchases (long customerId, HttpServletRequest request) throws ApplicationException{
			UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
			UserType userType = userData.getUserType();
			//only admin can get the customer purchases list
			if (!userType.equals(UserType.ADMIN)) {
				throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
			}
			else {
				List<PurchaseEntity> purchases = this.purchasesController.getCustomerPurchases(customerId);
				return purchases;
			}
		}
	
		
		
		// URL : http://localhost:8080/purchases/getMyPurchases?token=
		@GetMapping("/getMyPurchases")
		public List<PurchaseEntity>getMyPurchases (HttpServletRequest request) throws ApplicationException{
			UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
			UserType userType = userData.getUserType();
			//only customer can get his purchases list
			if (!userType.equals(UserType.CUSTOMER)) {
				throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
			}
			else {
				long customerId = userData.getId();
				List<PurchaseEntity> purchases = this.purchasesController.getCustomerPurchases(customerId);
				return purchases;
			}
		}
}