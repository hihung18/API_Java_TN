package com.example.api_java.repository;


import com.example.api_java.model.entity.BusinessTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IBusinessTripRepository extends JpaRepository<BusinessTrip, Long> {
    @Override
    Optional<BusinessTrip> findById(Long aLong);
    @Query(value = "SELECT * from businesstrip bt where  bt.id_manager in ?1", nativeQuery = true)
    List<BusinessTrip> findAllByUser_ManagerId(Long userId);

    @Query(value = "SELECT bt.* from businesstrip bt INNER JOIN task t ON bt.id = t.id_businessTrip WHERE t.userId in ?1" , nativeQuery = true)
    List<BusinessTrip> findAllBusinessTripsByUserId(Long userId);

    List<BusinessTrip> findAllByPartner_PartnerId(Long partnerId);
}
