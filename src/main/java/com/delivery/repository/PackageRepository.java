package com.delivery.repository;

import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query("select p" +
            " from Package p" +
            " where p.owner=:owner" +
            " order by p.id")
    List<Package> findAllPackages(@Param("owner") User owner);

    @Query("select p" +
            " from Package p" +
            " where p.currentTrip is null" +
            " order by p.id")
    List<Package> findFreePackages();

    @Modifying
    @Query("update Package p" +
            " set p.currentTrip.id=:tripId" +
            " where p.currentTrip is null and p.id=:packageId")
    int bookPackageForTrip(@Param("packageId") Long packageId, @Param("tripId") long tripId);

    List<Package> findAllByCurrentTrip(Trip currentTrip);

    Package findByIdAndOwner(long id, User owner);
}
