package com.delivery.repository;

import com.delivery.entity.Trip;
import com.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findFirstByIdAndTransporter(long id, User transporter);

    List<Trip> findAllByStateAndTransporter(Trip.State state, User transporter);

    List<Trip> findAllByTransporter(User transporter);

    @Query("select trip" +
            " from Trip trip" +
            " where trip.id in (" +
            "   select p.currentTrip.id" +
            "     from Package p " +
            "     where p.owner=:cargoOwner" +
            " )" +
            " order by trip.id")
    List<Trip> findTripsForCargoOwner(@Param("cargoOwner") User cargoOwner);
}
