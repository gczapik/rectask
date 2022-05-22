package com.gczapik.rectask.control;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=FeatureAlreadyExists.MESSAGE)
public class FeatureAlreadyExists extends RuntimeException {
    final static String MESSAGE = "Feature with this name is already defined! Use PUT method to update it or pick a different name to create new one.";

    public FeatureAlreadyExists() {
        super(MESSAGE);
    }
}
