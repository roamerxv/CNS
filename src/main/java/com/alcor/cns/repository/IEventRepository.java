package com.alcor.cns.repository;

import com.alcor.cns.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("com.alcor.cns.repository.IEventRepository")
public interface IEventRepository extends JpaRepository<EventEntity, String>, PagingAndSortingRepository<EventEntity, String> {
}
