package com.delivery.service;

import com.delivery.entity.City;
import com.delivery.entity.CityInTrip;
import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.entity.User;
import com.delivery.exception.InvalidCityOrderException;
import com.delivery.exception.RouteException;
import com.delivery.exception.TripInvalidStateException;
import com.delivery.exception.TripNotFoundException;
import com.delivery.repository.CityInTripRepository;
import com.delivery.repository.PackageRepository;
import com.delivery.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private CityInTripRepository cityInTripRepository;
    @Autowired
    private CityService cityService;

    public Trip createTrip(
            User transporter,
            String car
    ) {
        Trip trip = new Trip();
        trip.setTransporter(transporter);
        trip.setCar(car);
        trip.setState(Trip.State.CREATING);

        tripRepository.save(trip);

        return trip;
    }

    public Trip startTrip(
            long tripId,
            User transporter
    ) throws TripNotFoundException, TripInvalidStateException, RouteException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("User haven't this trip");
        }
        if (trip.getState() != Trip.State.CREATING) {
            throw new TripInvalidStateException(trip.getState());
        }

        List<CityInTrip> route = trip.getRouteList();
        if (route.size() <= 1) {
            throw new RouteException("Wrong routeList size: " + route.size());
        }

        route.sort(CityInTrip.comparator);
        trip.setState(Trip.State.STARTED);
        trip.setCurrentLocation(route.get(0));
        tripRepository.save(trip);

        return trip;
    }

    public Trip finishTrip(long tripId, User transporter) throws TripNotFoundException, TripInvalidStateException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("User haven't this trip");
        }
        if (trip.getState() != Trip.State.STARTED) {
            throw new TripInvalidStateException(trip.getState());
        }

        trip.setState(Trip.State.FINISHED);
        tripRepository.save(trip);

        return trip;
    }

    @Transactional
    public Trip addPackageToTrip(
            final long packageId,
            final long tripId,
            final User transporter
    ) throws TripNotFoundException, TripInvalidStateException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("Trip not found");
        }
        if (trip.getState() != Trip.State.CREATING) {
            throw new TripInvalidStateException(trip.getState());
        }

        boolean added = packageRepository.bookPackageForTrip(packageId, tripId) == 1;
        if (added) {
            trip = tripRepository
                    .findById(tripId)
                    .orElseThrow(() ->
                            new TripNotFoundException("Trip with id " + tripId + " not found"));
        }

        return trip;
    }

    @Transactional
    public boolean removePackageFromTrip(
            long packageId,
            long tripId,
            User transporter
    ) throws TripNotFoundException, TripInvalidStateException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("Trip not found");
        }
        if (trip.getState() != Trip.State.CREATING) {
            throw new TripInvalidStateException(trip.getState());
        }

        Package packageFromTrip = packageRepository.findById(packageId).orElse(null);

        if (packageFromTrip != null
                && packageFromTrip.getCurrentTrip() != null
                && packageFromTrip.getCurrentTrip().getId().equals(trip.getId())) {
            packageFromTrip.setCurrentTrip(null);
            packageRepository.save(packageFromTrip);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Trip assignRouteForTrip(
            long tripId,
            List<String> route,
            User transporter
    ) throws TripNotFoundException, TripInvalidStateException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("User haven't this trip");
        }
        if (trip.getState() != Trip.State.CREATING) {
            throw new TripInvalidStateException(trip.getState());
        }

        cityInTripRepository.deleteAllByTrip(trip);
        for (int i = 0; i < route.size(); i++) {
            City city = cityService.getOrAdd(route.get(i));
            CityInTrip cityInTrip = new CityInTrip(city, trip, i);
            cityInTripRepository.save(cityInTrip);
        }
        tripRepository.save(trip);

        return trip;
    }

    public Trip updateCurrentCityInTrip(
            int order,
            long tripId,
            User transporter
    ) throws TripNotFoundException, InvalidCityOrderException {
        Trip trip = findTripByIdAndTransporter(tripId, transporter);
        if (trip == null) {
            throw new TripNotFoundException("User haven't this trip");
        }

        int citiesAll = trip.getRouteList().size();
        if (order >= citiesAll || order < 0) {
            throw new InvalidCityOrderException("Invalid order: " + order
                    + ". There is only " + citiesAll + " cities in this trip"
            );
        }

        trip.setCurrentLocation(trip.getRouteList().get(order));
        tripRepository.save(trip);

        return trip;
    }

    @Nullable
    public Trip findTripByIdAndTransporter(long id, User transporter) {
        return tripRepository.findFirstByIdAndTransporter(id, transporter);
    }

    public List<Trip> findTripsByStateAndTransporter(Trip.State state, User transporter) {
        return tripRepository.findAllByStateAndTransporter(state, transporter);
    }

    public List<Trip> findTripsTransporter(User transporter) {
        return tripRepository.findAllByTransporter(transporter);
    }

    public List<Trip> findTripsForCargoOwner(User cargoOwner) {
        return tripRepository.findTripsForCargoOwner(cargoOwner);
    }

    @Transactional
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }
}
