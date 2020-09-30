package com.delivery.repository;

import com.delivery.entity.CityInTrip;
import com.delivery.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityInTripRepository extends JpaRepository<CityInTrip, Long> {
    void deleteAllByTrip(Trip trip);
}
