package com.ktcraft.validationproduct.service.businessprofile.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerResponse {

    private HttpStatus httpStatus;

    private HttpHeaders headers;

    private String body;
}
