package com.moran.coupons.dao;

import org.springframework.data.repository.CrudRepository;

import com.moran.coupons.entities.CustomerEntity;

public interface ICustomersDao extends CrudRepository<CustomerEntity, Long> {

}
