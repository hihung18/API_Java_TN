package com.example.api_java.repository;

import com.example.api_java.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReportRepository  extends JpaRepository<Report, Long> {
    @Override
    Optional<Report> findById(Long aLong);
//    List<Report> findAllByBusinessTrip_BusinessTripId(Long businessTripId);
}
