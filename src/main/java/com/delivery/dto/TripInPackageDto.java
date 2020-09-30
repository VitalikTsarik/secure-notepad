package com.delivery.dto;

import com.delivery.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TripInPackageDto {
    private Long id;
    private Trip.State state;
    private List<CityInTripDto> routeList;
    private String car;
    private CityInTripDto currentLocation;

    public static TripInPackageDto build(Trip trip) {
        if (trip == null) {
            return null;
        }

        return new TripInPackageDto(
                trip.getId(),
                trip.getState(),
                trip.getRouteList()
                        .stream()
                        .map(CityInTripDto::build)
                        .collect(Collectors.toList()),
                trip.getCar(),
                CityInTripDto.build(trip.getCurrentLocation())
        );
    }
}
