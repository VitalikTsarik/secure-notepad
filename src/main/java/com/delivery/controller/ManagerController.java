package com.delivery.controller;

import com.delivery.dto.PackageDetailedDto;
import com.delivery.dto.TripDto;
import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.service.PackageService;
import com.delivery.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private PackageService packageService;
    @Autowired
    private TripService tripService;

    @GetMapping("/packages")
    public ResponseEntity<?> getAllPackages() {
        List<Package> packageList = packageService.findAll();

        return ResponseEntity.ok(packageList
                .stream()
                .map(PackageDetailedDto::build)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/trips")
    public ResponseEntity<?> getAllTrips() {
        List<Trip> tripList = tripService.findAll();

        return ResponseEntity.ok(tripList
                .stream()
                .map(TripDto::build)
                .collect(Collectors.toList())
        );
    }
}
