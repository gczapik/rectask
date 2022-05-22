package com.gczapik.rectask.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=FeatureNotFound.MESSAGE)
public class FeatureNotFound extends RuntimeException {
    final static String MESSAGE = "Feature with this name does not exist! Use POST method to create it first.";

    public FeatureNotFound() {
        super(MESSAGE);
    }
}
