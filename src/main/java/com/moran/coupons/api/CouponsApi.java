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

import com.moran.coupons.data.UserAfterLoginData;
import com.moran.coupons.detailsobjects.CouponDetailsObject;
import com.moran.coupons.entities.CouponEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.Type;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.logic.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;

	public CouponsApi() {
	}

	//  URL : http://localhost:8080/coupons
	@PostMapping
	public void createCoupon(@RequestBody CouponDetailsObject coupon, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			coupon.setUserId(userData.getId());
			this.couponsController.createCoupon(coupon);
		}
	}



	//  URL : http://localhost:8080/coupons
	@PutMapping
	public void updateCoupon(@RequestBody CouponDetailsObject coupon, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			coupon.setUserId(userData.getId());
			long companyId = userData.getCompanyId();
			this.couponsController.updateCoupon(coupon, companyId);
		}
	}



	// http://localhost:8080/coupons/10
	@DeleteMapping("{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		long companyId = userData.getCompanyId();
		this.couponsController.removeCoupon(couponId, companyId);
	}

	
	
	

	// http://localhost:8080/coupons
	@GetMapping("{couponId}")
	public CouponEntity getCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		CouponEntity coupon = new CouponEntity();
		coupon = couponsController.getCouponByCouponId(couponId);
		return coupon;
	}

	
	
	
	
	
	


	//  URL : http://localhost:8080/coupons
	@GetMapping
	public List<CouponEntity> getAllCoupons() throws ApplicationException{
		List<CouponEntity> coupons = couponsController.getAllCoupons();
		return coupons;
	}


	
	
	
	
	
	// URL : http://localhost:8080/coupons/byCompanyId?companyId=1
	@GetMapping("/byCompanyId")
	public List<CouponEntity> getCompanyCouponsByCompanyId(@RequestParam("companyId") long companyId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		List<CouponEntity>companyCoupons = couponsController.getCompanyCouponsByCompanyId(companyId);
		return companyCoupons;
	}




	// URL : http://localhost:8080/coupons/myCompanyCoupons
	@GetMapping("/myCompanyCoupons")
	public List<CouponEntity> getMyCompanyCoupons(HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.SELLER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		long companyId = userData.getCompanyId();
		List<CouponEntity>companyCoupons = couponsController.getCompanyCouponsByCompanyId(companyId);
		return companyCoupons;
	}
	
	
	

	// URL : http://localhost:8080/coupons/byType?type=SPA
	@GetMapping("/byType")
	public List<CouponEntity> getCouponsbyType(@RequestParam("type") Type type, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)&&!userType.equals(UserType.CUSTOMER)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		List<CouponEntity>couponsOfType = couponsController.getCouponsByType(type);
		return couponsOfType;
	}	

}
