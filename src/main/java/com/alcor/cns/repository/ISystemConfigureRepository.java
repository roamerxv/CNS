package com.alcor.cns.repository;

import com.alcor.cns.entity.SystemConfigureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("com.alcor.cns.repository.ISystemConfigureRepository")
public interface ISystemConfigureRepository extends JpaRepository<SystemConfigureEntity, String>, PagingAndSortingRepository<SystemConfigureEntity, String> {
    public List<SystemConfigureEntity> findAll();
}
