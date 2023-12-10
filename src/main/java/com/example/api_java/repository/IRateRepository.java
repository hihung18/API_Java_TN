package com.example.api_java.repository;

import com.example.api_java.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Long> {
    @Override
    Optional<Rate> findById(Long aLong);
    List<Rate> findAllByUserDetail_UserId(Long userId);

    @Query(value = "SELECT * from rate r where  r.id_business_trip = ?1 order by time_cre ASC", nativeQuery = true)
    List<Rate> findAllByBusinessTrip_BusinessTripId (Long businessTripId);
}
