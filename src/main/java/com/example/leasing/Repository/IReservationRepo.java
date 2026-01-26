package com.example.leasing.Repository;

import com.example.leasing.Entity.ReservationEntity;
import com.example.leasing.Entity.ReservationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepo extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByCarId(Long carId);

    List<ReservationEntity> findByClientId(Long clientId);

    List<ReservationEntity> findByStatus(ReservationStatusEnum status);
}