package com.example.api_java.repository;


import com.example.api_java.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    @Override
    Optional<Image> findById(Long s);
    List<Image> findAllByReport_ReportId(Long reportId);
}
