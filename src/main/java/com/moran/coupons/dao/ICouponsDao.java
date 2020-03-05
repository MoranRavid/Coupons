package com.moran.coupons.dao;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.CouponEntity;
import com.moran.coupons.entities.UserEntity;
import com.moran.coupons.enums.Type;

public interface ICouponsDao extends CrudRepository<CouponEntity, Long> {

	public boolean existsByCouponTitle (String couponTitle);

	public List<CouponEntity> findByType (Type type);

	public List<CouponEntity> findByUser(UserEntity userEntity);
	
	@Transactional
	public void deleteByExpiryDateLessThan(Date today);
	
	@Query ("SELECT CE FROM CouponEntity CE WHERE CE.user IN (SELECT UE FROM UserEntity UE WHERE UE.company =:companyId)")
    List<CouponEntity> companyCoupons(@Param("companyId") CompanyEntity companyEntity);

}
