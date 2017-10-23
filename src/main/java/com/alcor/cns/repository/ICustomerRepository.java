package com.alcor.cns.repository;

import com.alcor.cns.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("com.alcor.cns.repository.ICustomerRepository")
public interface ICustomerRepository extends JpaRepository<CustomerEntity, String>, PagingAndSortingRepository<CustomerEntity, String> {

}
