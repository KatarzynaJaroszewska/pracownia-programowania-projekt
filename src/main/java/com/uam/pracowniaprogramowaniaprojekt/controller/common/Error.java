package com.uam.pracowniaprogramowaniaprojekt.controller.common;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Error {

    private Instant date;
    private int httpStatusNumber;
    private String errorMessage;

    public Error(final String errorMessage, int httpStatusNumber) {
        this.date = Instant.now();
        this.errorMessage = errorMessage;
        this.httpStatusNumber = httpStatusNumber;
    }

}
