package com.ktcraft.validationproduct.service.exceptions;

import com.ktcraft.validationproduct.constants.ErrorConstants;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends GeneralException {
    public ServiceUnavailableException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, ErrorConstants.ServiceUnavailable.getMessage());
    }
}
