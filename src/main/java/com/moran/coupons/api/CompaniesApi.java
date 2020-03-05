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
import com.moran.coupons.detailsobjects.CompanyDetailsObject;
import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.UserType;
import com.moran.coupons.exceptions.ApplicationException;
import com.moran.coupons.logic.CompaniesController;

@RestController
@RequestMapping("/companies")
public class CompaniesApi {

	@Autowired
	private CompaniesController companiesController;

	public CompaniesApi() {
	}

	//  URL : http://localhost:8080/companies?token=
	//only the admin could create company
	@PostMapping
	public void createCompany(@RequestBody CompanyDetailsObject company, HttpServletRequest request) throws ApplicationException {
		//check if the request indeed came from the admin
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.companiesController.createCompany(company);			
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}



	//Only the admin can update company
	//  URL : http://localhost:8080/comoanies
	@PutMapping
	public void updateCompany(@RequestBody CompanyDetailsObject company, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.companiesController.updateCompany(company);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}




	// http://localhost:8080/comoanies/10
	//only the admin can remove company
	@DeleteMapping("{companyId}")
	public void deleteCompany(@PathVariable("companyId") long companyId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (userType.equals(UserType.ADMIN)) {
			this.companiesController.removeCompany(companyId);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}



	// http://localhost:8080/comoanies/12
	@GetMapping("{companyId}")
	public CompanyEntity getCompany(@PathVariable("companyId") long companyId, HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		CompanyEntity company = new CompanyEntity();
		if (userType.equals(UserType.ADMIN)) {
			company = this.companiesController.getCompanyByCompanyId(companyId);
			return company;
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
	}


	// http://localhost:8080/companies/getMyCompanyDetails
	@GetMapping("/getMyCompanyDetails")
	public CompanyEntity getMyCompanyDetails(HttpServletRequest request) throws ApplicationException {
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		CompanyEntity company = new CompanyEntity();
		if (userType.equals(UserType.SELLER)) {
			long companyId = userData.getCompanyId();
			company = companiesController.getCompanyByCompanyId(companyId);
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		return company;
	}


	//  URL : http://localhost:8080/companies
	//only the admin could get the all companies list
	@GetMapping
	public List<CompanyEntity> getAllCompanies(HttpServletRequest request) throws ApplicationException{
		UserAfterLoginData userData = (UserAfterLoginData) request.getAttribute("userLoginData");
		UserType userType = userData.getUserType();
		if (!userType.equals(UserType.ADMIN)) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "You're not authorized");
		}
		else {
			List<CompanyEntity> allCompanies = companiesController.getAllCompanies();
			return allCompanies;
		}
	}
}
