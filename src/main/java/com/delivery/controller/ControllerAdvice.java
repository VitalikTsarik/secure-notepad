package com.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;

@RestControllerAdvice(basePackages = "com.delivery.controller")
public class ControllerAdvice {

    @Autowired
    private Clock clock;

//    @ExceptionHandler
//    public ResponseEntity<?> handleException(TripNotFoundException exc) {
//        ExceptionDto response = new ExceptionDto(
//                exc.getMessage(),
//                clock.millis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleException(TripInvalidStateException exc) {
//        ExceptionDto response = new ExceptionDto(
//                exc.getMessage(),
//                clock.millis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleException(InvalidCityOrderException exc) {
//        ExceptionDto response = new ExceptionDto(
//                exc.getMessage(),
//                clock.millis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleException(RouteException exc) {
//        ExceptionDto response = new ExceptionDto(
//                exc.getMessage(),
//                clock.millis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}
