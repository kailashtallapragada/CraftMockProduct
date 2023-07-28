package com.ktcraft.validationproduct.service.exceptions;

import com.ktcraft.validationproduct.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class InternalServerException extends GeneralException {

    public InternalServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.InternalServerError.getMessage());
    }
}
