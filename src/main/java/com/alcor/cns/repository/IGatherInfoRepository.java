package com.alcor.cns.repository;

import com.alcor.cns.entity.GatherInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("com.alcor.cns.repository.IGatherInfoRepository")
public interface IGatherInfoRepository extends JpaRepository<GatherInfoEntity, String>, PagingAndSortingRepository<GatherInfoEntity, String> {

    public List<GatherInfoEntity> findAll();

    public List<GatherInfoEntity> findAllByContractId(String contractId);

    public List<GatherInfoEntity> findAllByNoticeDateAndGatheredAndNotice(Date noticeDate, boolean gathered, boolean notice);

    public List<GatherInfoEntity> findAllByGatherDateBetween(Date beginDate, Date endDate);

    public int countByGatherDateBetween(Date beginDate, Date endDate);

    @Query("select sum(u.amount) from GatherInfoEntity u WHERE u.gatherDate BETWEEN ?1 AND ?2")
    public float sumAmountByGatherDateBetween(Date beginDate, Date endDate);

}
