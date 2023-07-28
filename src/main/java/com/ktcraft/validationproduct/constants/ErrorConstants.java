package com.ktcraft.validationproduct.constants;

public enum ErrorConstants {

    InvalidInput("The input provided is not valid"),
    InternalServerError("An error has occured on the server"),
    ServiceUnavailable("The requested service is currently unavailable"),
    InvalidAuthToken("Invalid authentication token.");

    private String message;

    ErrorConstants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
