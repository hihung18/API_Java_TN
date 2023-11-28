package com.example.api_java.repository;

import com.example.api_java.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPartnerRepository extends JpaRepository<Partner, Long> {
    @Override
    Optional<Partner> findById(Long aLong);
}
