package com.moran.coupons.dao;

import org.springframework.data.repository.CrudRepository;

import com.moran.coupons.entities.CompanyEntity;

public interface ICompaniesDao extends CrudRepository<CompanyEntity, Long> {

	public boolean existsByCompanyName (String companyId);
}
