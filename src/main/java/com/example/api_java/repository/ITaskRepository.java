package com.example.api_java.repository;

import com.example.api_java.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    @Override
    Optional<Task> findById(Long aLong);

//    List<Task> findAllByUserDetail_UserId(Long userId);
//    List<Task> findAllByBusinessTrip_BusinessTripId(Long businesstripId);


}
