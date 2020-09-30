package com.delivery.exception;

import com.delivery.entity.Trip;

public class TripInvalidStateException extends Exception {
    public TripInvalidStateException(Trip.State currentState) {
        super("Trip is in invalid state: " + currentState);
    }
}
