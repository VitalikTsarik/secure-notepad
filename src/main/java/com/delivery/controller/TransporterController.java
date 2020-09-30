package com.delivery.controller;

import com.delivery.dto.FreePackageDto;
import com.delivery.dto.TripDto;
import com.delivery.dto.TripToCreateDto;
import com.delivery.dto.RouteForTripDto;
import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.entity.User;
import com.delivery.exception.InvalidCityOrderException;
import com.delivery.exception.RouteException;
import com.delivery.exception.TripInvalidStateException;
import com.delivery.exception.TripNotFoundException;
import com.delivery.service.PackageService;
import com.delivery.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAuthority('TRANSPORTER')")
@RequestMapping("/api/transporter")
public class TransporterController {

    @Autowired
    private PackageService packageService;
    @Autowired
    private TripService tripService;

    @GetMapping("/packages/free")
    public ResponseEntity<?> getFreePackages() {
        List<Package> freePackages = packageService.findFreePackages();

        return ResponseEntity.ok(
                freePackages
                        .stream()
                        .map(FreePackageDto::build)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/trip/{id}")
    public ResponseEntity<?> getTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @PathVariable long id
    ) throws TripNotFoundException {
        Trip trip = tripService.findTripByIdAndTransporter(id, transporter);
        if (trip == null) {
            throw new TripNotFoundException();
        }

        return ResponseEntity.ok(TripDto.build(trip));
    }

    @GetMapping("/trips")
    public ResponseEntity<?> getTrips(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam(required = false) Trip.State state
    ) {
        List<Trip> trips;
        if (state == null) {
            trips = tripService.findTripsTransporter(transporter);
        } else {
            trips = tripService.findTripsByStateAndTransporter(state, transporter);
        }

        return ResponseEntity.ok(
                trips
                        .stream()
                        .map(TripDto::build)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/trip")
    public ResponseEntity<?> createTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestBody TripToCreateDto tripDto
    ) {
        Trip newTrip = tripService.createTrip(transporter, tripDto.getCar());

        return ResponseEntity.ok(TripDto.build(newTrip));
    }

    @PostMapping("/trip/started")
    public ResponseEntity<?> startTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam long tripId
    ) throws TripInvalidStateException, TripNotFoundException, RouteException {
        Trip trip = tripService.startTrip(tripId, transporter);

        return ResponseEntity.ok(TripDto.build(trip));
    }

    @PostMapping("/trip/finished")
    public ResponseEntity<?> finishTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam long tripId
    ) throws TripInvalidStateException, TripNotFoundException {
        Trip trip = tripService.finishTrip(tripId, transporter);

        return ResponseEntity.ok(TripDto.build(trip));
    }

    @PutMapping("/trip/package")
    public ResponseEntity<?> addPackageToTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam long packageId,
            @RequestParam long tripId
    ) throws TripNotFoundException, TripInvalidStateException {
        Trip updatedTrip = tripService.addPackageToTrip(packageId, tripId, transporter);

        return ResponseEntity.ok(TripDto.build(updatedTrip));
    }

    @PutMapping("/trip/route")
    public ResponseEntity<?> assignRouteForTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestBody RouteForTripDto routeForTripDto
    ) throws TripInvalidStateException, TripNotFoundException {
        Trip updatedTrip = tripService.assignRouteForTrip(
                routeForTripDto.getTripId(),
                routeForTripDto.getRoute(),
                transporter
        );

        return ResponseEntity.ok(TripDto.build(updatedTrip));
    }

    @PutMapping("/trip/current-city")
    public ResponseEntity<?> updateCityInTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam int order,
            @RequestParam long tripId
    ) throws TripNotFoundException, InvalidCityOrderException {
        Trip trip = tripService.updateCurrentCityInTrip(order, tripId, transporter);

        return ResponseEntity.ok(TripDto.build(trip));
    }

    @DeleteMapping("/trip/package")
    public ResponseEntity<?> removePackageFromTrip(
            @AuthenticationPrincipal @ApiIgnore User transporter,
            @RequestParam long packageId,
            @RequestParam long tripId
    ) throws TripNotFoundException, TripInvalidStateException {
        tripService.removePackageFromTrip(packageId, tripId, transporter);

        return ResponseEntity.ok().build();
    }
}
