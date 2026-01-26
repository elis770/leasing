package com.example.leasing.Repository;

import com.example.leasing.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepo extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByCarId(Long carId);

    List<ReviewEntity> findByClientId(Long clientId);
}