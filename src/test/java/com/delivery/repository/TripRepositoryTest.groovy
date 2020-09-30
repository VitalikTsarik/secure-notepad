package com.delivery.repository

import com.delivery.entity.Package
import com.delivery.entity.Role
import com.delivery.entity.Trip
import com.delivery.entity.User
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@DataJpaTest
class TripRepositoryTest {
    @Autowired
    private TripRepository tripRepository
    @Autowired
    private UserRepository userRepository
    @Autowired
    private PackageRepository packageRepository

    @Test
    void testFindTripsForCargoOwner() {
        User cargoOwner = new User()
        cargoOwner.role = Role.CARGO_OWNER
        userRepository.save(cargoOwner)
        User transporter = new User()
        transporter.role = Role.TRANSPORTER
        userRepository.save(transporter)
        List<Package> savedPackages = []
        3.times {
            Package aPackage = new Package()
            aPackage.owner = cargoOwner
            packageRepository.save(aPackage)
            savedPackages.add(aPackage)
        }
        Trip trip = new Trip()
        trip.transporter = transporter
        trip.packageList = savedPackages.subList(0, 2)
        trip.packageList.forEach({x -> x.currentTrip = trip})
        tripRepository.save(trip)

        def foundTrips = tripRepository.findTripsForCargoOwner(cargoOwner)

        Assert.assertTrue(foundTrips.size() == 1)
        Assert.assertTrue(foundTrips[0] == trip)
        Assert.assertTrue(foundTrips[0].getPackageList() == trip.getPackageList())
    }
}
