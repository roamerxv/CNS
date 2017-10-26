package com.alcor.cns.repository;

import com.alcor.cns.entity.GatherInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.alcor.cns.repository.IGatherInfoRepository")
public interface IGatherInfoRepository extends JpaRepository<GatherInfoEntity, String>, PagingAndSortingRepository<GatherInfoEntity, String> {

    public List<GatherInfoEntity>  findAllByContractId(String contractId);

}
