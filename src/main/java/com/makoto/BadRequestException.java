package com.makoto;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {

    private final int statusCode;
    private final String reason;

    public BadRequestException(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }
}
