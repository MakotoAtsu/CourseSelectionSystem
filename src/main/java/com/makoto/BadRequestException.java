package com.makoto;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {

    private final int statusCode;

    public BadRequestException(int statusCode, String reason) {
        super(reason);
        this.statusCode = statusCode;
    }
}
