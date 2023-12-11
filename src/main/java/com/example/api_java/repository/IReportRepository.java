package com.example.api_java.repository;

import com.example.api_java.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReportRepository  extends JpaRepository<Report, Long> {
    @Override
    Optional<Report> findById(Long aLong);
    @Query(value = "SELECT * from report r where  r.id_task = ?1 order by time_cre ASC", nativeQuery = true)
    List<Report> findAllByTask_TaskId(Long taskID);
    @Query(value = "SELECT distinct r.* FROM businesstrip bt JOIN task t ON bt.id = t.id_business_trip\n" +
            "JOIN report  r ON t.id = r.id_task WHERE bt.id = ?1 order by time_cre ASC", nativeQuery = true)
    List<Report> findAllByBusinessId(Long businessID);
}
