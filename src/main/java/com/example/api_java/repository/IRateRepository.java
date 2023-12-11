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
    @Query(value = "SELECT distinct r.* FROM rate r\n" +
            "JOIN task t ON r.id_task = t.id\n" +
            "JOIN businesstrip bt ON t.id_business_trip = bt.id\n" +
            "WHERE t.id = ?1 and (t.id_employee = r.id_employee or r.id_employee = bt.id_manager )\n" +
            "order by r.time_cre ASC", nativeQuery = true)
    List<Rate> findAllByTask_TaskId (Long taskID);

    @Query(value = "SELECT distinct r.* FROM businesstrip bt JOIN task t ON bt.id = t.id_business_trip\n" +
            "JOIN rate  r ON t.id = r.id_task WHERE bt.id = ?1 order by r.time_cre ASC", nativeQuery = true)
    List<Rate> findAllByBusinessID (Long businessID);
}
