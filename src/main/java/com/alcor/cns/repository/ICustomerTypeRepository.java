package com.alcor.cns.repository;

import com.alcor.cns.entity.CustomerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("com.alcor.cns.repository.ICustomerTypeRepository")
public interface ICustomerTypeRepository extends JpaRepository<CustomerTypeEntity, String>, PagingAndSortingRepository<CustomerTypeEntity, String> {

}
