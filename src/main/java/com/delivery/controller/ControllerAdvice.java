package com.delivery.controller;

import com.delivery.dto.ExceptionDto;
import com.delivery.exception.InvalidCityOrderException;
import com.delivery.exception.RouteException;
import com.delivery.exception.TripInvalidStateException;
import com.delivery.exception.TripNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;

@RestControllerAdvice(basePackages = "com.delivery.controller")
public class ControllerAdvice {

    @Autowired
    private Clock clock;

    @ExceptionHandler
    public ResponseEntity<?> handleException(TripNotFoundException exc) {
        ExceptionDto response = new ExceptionDto(
                exc.getMessage(),
                clock.millis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(TripInvalidStateException exc) {
        ExceptionDto response = new ExceptionDto(
                exc.getMessage(),
                clock.millis()
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(InvalidCityOrderException exc) {
        ExceptionDto response = new ExceptionDto(
                exc.getMessage(),
                clock.millis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(RouteException exc) {
        ExceptionDto response = new ExceptionDto(
                exc.getMessage(),
                clock.millis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
