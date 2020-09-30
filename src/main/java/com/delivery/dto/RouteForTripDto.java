package com.delivery.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteForTripDto {
    private long tripId;
    private List<String> route;
}
