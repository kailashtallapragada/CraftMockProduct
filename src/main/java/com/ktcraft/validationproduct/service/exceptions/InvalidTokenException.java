package com.ktcraft.validationproduct.service.exceptions;

import com.ktcraft.validationproduct.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends GeneralException {

    public InvalidTokenException() {
        super(HttpStatus.FORBIDDEN, ErrorConstants.InvalidAuthToken.getMessage());
    }
}
