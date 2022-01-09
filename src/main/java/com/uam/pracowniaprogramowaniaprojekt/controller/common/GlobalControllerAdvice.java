package com.uam.pracowniaprogramowaniaprojekt.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error entityNotFoundExceptionHandler(final Exception ex) {
        LOG.error("Error occurred", ex);
        return new Error(ex.getMessage(), 404);
    }

}
