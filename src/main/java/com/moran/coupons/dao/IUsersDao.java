package com.moran.coupons.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.moran.coupons.entities.CompanyEntity;
import com.moran.coupons.entities.UserEntity;

public interface IUsersDao extends CrudRepository<UserEntity, Long>{

	public boolean existsByEmail (String email);

//	public UserEntity findByNameAndPassword(UserLoginDetails userLoginDetails);
	
	public List<UserEntity> findByCompany(CompanyEntity companyEntity);

	
	@Query("SELECT u FROM UserEntity u WHERE u.email=:email and u.password=:password")
    UserEntity fetchUsers(@Param("email") String email, @Param("password") String password);
}
