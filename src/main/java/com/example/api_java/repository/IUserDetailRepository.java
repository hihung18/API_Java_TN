package com.example.api_java.repository;


import com.example.api_java.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUsername(String username);
    Optional<UserDetail> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
