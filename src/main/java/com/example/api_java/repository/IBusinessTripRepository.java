package com.example.api_java.repository;


import com.example.api_java.model.entity.BusinessTrip;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IBusinessTripRepository extends JpaRepository<BusinessTrip, Long> {
    @Override
    Optional<BusinessTrip> findById(Long aLong);
//    @Query(value = "SELECT * from businesstrip bt where  bt.id_manager in ?1", nativeQuery = true)
    List<BusinessTrip> findAllByUserDetail_UserId(Long managerId);

    @Query(value = "SELECT * FROM businesstrip bt INNER JOIN task t ON bt.id = t.id_business_trip WHERE t.id_employee = ?1", nativeQuery = true)
    List<BusinessTrip> findBusinessTripsByUserDetailUserId(Long userId);


//    @EntityGraph(attributePaths = {"tasks"})
//    @Query("SELECT b FROM BusinessTrip b JOIN b.tasks t WHERE t.userDetail.userId = :userId")
//    List<BusinessTrip> findBusinessTripsByUserDetailUserId(@Param("userId") Long userId);

    List<BusinessTrip> findAllByPartner_PartnerId(Long partnerId);
}
