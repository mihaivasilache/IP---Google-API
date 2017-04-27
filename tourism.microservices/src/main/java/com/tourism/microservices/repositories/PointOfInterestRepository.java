package com.tourism.microservices.repositories;

import com.tourism.microservices.models.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by apiriu on 4/28/2017.
 */
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
}
