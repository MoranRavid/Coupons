package com.moran.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moran.coupons.dao.ICompaniesDao;
import com.moran.coupons.detailsobjects.CompanyDetailsObject;
import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.enums.ErrorTypes;
import com.moran.coupons.enums.Type;
import com.moran.coupons.exceptions.ApplicationException;

@Controller
public class CompaniesController {

	@Autowired
	private ICompaniesDao companiesDao;


	public CompaniesController() {
	}


	//----------------------create company----------------------

	public void createCompany(CompanyDetailsObject company) throws ApplicationException {
		//name validation
		if (this.isExistByCompanyName(company.getCompanyName())) {
			throw new ApplicationException(ErrorTypes.DUPLICATE_COMPANY_NAME, "There is already a company by the name " + company.getCompanyName());
		}

		//other validations
		validateCompanyDetails(company);

		//convert company details object to company entity
		CompanyEntity companyEntity = convertToCompanyEntity(company);


		try {
			//sending the company to the dao
			this.companiesDao.save(companyEntity);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not create a company");
		}
	}





	//---------------------update company------------------------


	public void updateCompany(CompanyDetailsObject company) throws ApplicationException {

		//Specific validation for the update function
		nameValidations(company);

		//other validations
		validateCompanyDetails(company);

		//convert company details object to company entity
		CompanyEntity companyEntity = convertToCompanyEntity(company);

		try {
			this.companiesDao.save(companyEntity);			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not create a company");
		}
	}




	//-----------------------remove company------------------------------------


	public void removeCompany (long companyId) throws ApplicationException{
		try {
			this.companiesDao.deleteById(companyId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not remove company no." + companyId);
		}
	}


	


	//-----------------------------get company/ies-----------------------

	public CompanyEntity getCompanyByCompanyId(long companyId) throws ApplicationException {
		CompanyEntity company = new CompanyEntity();
		try {
			company = companiesDao.findById(companyId).get();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the company");
		}


		if (company == null) {
			throw new ApplicationException(ErrorTypes.UNKNOWN_COMPANY, "there is no company with the id: "+ companyId);
		}

		return company;
	}

	
	

	public List<CompanyEntity> getAllCompanies() throws ApplicationException {
		try {
			List<CompanyEntity> companiesList = (List<CompanyEntity>) this.companiesDao.findAll();			
			return companiesList;		
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the companies list");
		}
	}



	//-----------------------------existence check------------------
	
	public boolean isExistByCompanyName(String companyName) throws ApplicationException {
		try {
			//title validation
			if (this.companiesDao.existsByCompanyName(companyName)) {
				return true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "faild to check if company with the name " + companyName + " is exist");
		}
		return false;
	}



	//----------------------convert to company entity-------------------

	private CompanyEntity convertToCompanyEntity(CompanyDetailsObject company) {
		CompanyEntity companyEntity = new CompanyEntity();
		companyEntity.setAddress(company.getAddress());
		companyEntity.setCompanyName(company.getCompanyName());
		companyEntity.setPhone(company.getPhone());
		companyEntity.setType(company.getType());
		if (company.getCompanyId() != null) {
			companyEntity.setCompanyId(company.getCompanyId());
		}

		return companyEntity;
	}
	
	
	//----------------------validate company details----------------------------
	
	private void nameValidations(CompanyDetailsObject company) throws ApplicationException {
		//the validations is only for update function
		
		// check if the company name changed to one that already exist
		CompanyEntity dbCompany = new CompanyEntity();
		try {
			dbCompany = this.companiesDao.findById(company.getCompanyId()).get();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the company");
		}
		
		// if the name is equal to the origin name everything OK, i can continue to the next validations
		if (!dbCompany.getCompanyName().equals (company.getCompanyName())) {
			// if not, i want to check that he didn't used name that already exist
			if (this.isExistByCompanyName(company.getCompanyName())) {
				throw new ApplicationException(ErrorTypes.DUPLICATE_COMPANY_NAME, "There is already a company by this name");	
			}
		}
	}
	
	
	
	
	private void validateCompanyDetails(CompanyDetailsObject company) throws ApplicationException {
		if (!isNameValid(company.getCompanyName())) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY_NAME, "Company name is invalid");
		}
		if (!isAddressValid(company.getAddress())) {
			throw new ApplicationException(ErrorTypes.INVALID_ADDRESS, "Address is invalid");
		}
		if (!isPhoneValid(company.getPhone())) {
			throw new ApplicationException(ErrorTypes.INVALID_PHONE_NUMBER, "Phone is invalid");
		}
		if (!isTypeValid(company.getType())) {
			throw new ApplicationException(ErrorTypes.INVALID_TYPE, "Type is invalid");
		}
	}
	
	
	
	
	
	//--------------------------validations--------------------
	
	private boolean isNameValid(String companyName) {
		if (companyName == null) {
			return false;
		}
		if (companyName == "") {
			return false;
		}
		if (companyName.length() < 2) {
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
	
	private boolean isTypeValid(Type type) {
		// the user has to choose one of the types i suggested him
		if (type == null) {
			return false;
		}
		return true;			
	}
}