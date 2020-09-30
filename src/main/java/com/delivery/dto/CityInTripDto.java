package com.delivery.dto;

import com.delivery.entity.City;
import com.delivery.entity.CityInTrip;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityInTripDto {
    private City city;
    private long order;

    public static CityInTripDto build(CityInTrip cityInTrip) {
        if (cityInTrip == null) {
            return null;
        }

        return new CityInTripDto(
                cityInTrip.getCity(),
                cityInTrip.getOrder()
        );
    }
}
