package com.delivery.exception;

public class TripNotFoundException extends Exception {
    public TripNotFoundException() {
        super("Trip not found");
    }

    public TripNotFoundException(String message) {
        super(message);
    }
}
