package com.moran.coupons.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.CustomerEntity;
import com.moran.coupons.entities.PurchaseEntity;

public interface IPurchasesDao extends CrudRepository<PurchaseEntity, Long> {

	public List<PurchaseEntity> findByCustomer(CustomerEntity customerEntity);

	@Query ("SELECT PE FROM PurchaseEntity PE WHERE PE.coupon IN(SELECT CE FROM CouponEntity CE WHERE CE.user IN (SELECT UE FROM UserEntity UE WHERE UE.company =:companyId))")
    List<PurchaseEntity> companyPurchases(@Param("companyId") CompanyEntity companyEntity);
}
