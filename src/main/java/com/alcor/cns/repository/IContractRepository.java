package com.alcor.cns.repository;

import com.alcor.cns.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.alcor.cns.repository.IContractRepository")
public interface IContractRepository extends JpaRepository<ContractEntity, String>, PagingAndSortingRepository<ContractEntity, String> {

    public List<ContractEntity>  findAllByCustomerId(String customerId);
}
