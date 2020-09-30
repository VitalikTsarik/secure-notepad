package com.delivery.dto;

import com.delivery.entity.CityInTrip;
import com.delivery.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TripDto {
    private Long id;
    private Trip.State state;
    private List<String> routeList;
    private List<FreePackageDto> packageList;
    private String car;
    private long currentLocation;
    private UserDto transporter;

    public static TripDto build(Trip trip) {
        if (trip == null) {
            return null;
        }

        List<String> cityNames = trip.getRouteList().stream()
                .map(x -> x.getCity().getName()).collect(Collectors.toList());
        List<FreePackageDto> packages = trip.getPackageList().stream()
                .map(FreePackageDto::build).collect(Collectors.toList());

        CityInTrip currentLocation = trip.getCurrentLocation();
        long order = currentLocation == null ? -1 : currentLocation.getOrder();

        return new TripDto(
                trip.getId(),
                trip.getState(),
                cityNames,
                packages,
                trip.getCar(),
                order,
                UserDto.build(trip.getTransporter())
        );
    }
}
