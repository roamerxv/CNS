package com.alcor.cns.repository;

import com.alcor.cns.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("com.alcor.cns.repository.IUserRepository")
public interface IUserRepository extends JpaRepository<UserEntity, String>, PagingAndSortingRepository<UserEntity, String> {
}
