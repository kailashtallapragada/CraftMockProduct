package com.ktcraft.validationproduct.service.businessprofile.rest;

import com.ktcraft.validationproduct.http.util.HttpUtil;
import com.ktcraft.validationproduct.service.businessprofile.dao.ServerResponse;
import com.ktcraft.validationproduct.service.businessprofile.service.BusinessProfileService;
import com.ktcraft.validationproduct.service.security.AccountAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/business_profile")
@Slf4j
public class BusinessProfileResource {

    @Autowired
    BusinessProfileService businessProfileService;

    @Autowired
    HttpUtil httpUtil;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getProfile(
            @CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        final ServerResponse businessProfile = businessProfileService.getBusinessProfile(authHeader);
        return httpUtil.formResponse(businessProfile);
    }

    @PutMapping(value = "/updaterequest", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getProfileUpdateRequest(
            @CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody String body) {
        return httpUtil.formResponse(businessProfileService.updateBusinessProfile(authHeader, body));
    }

    @PatchMapping("/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> verifyProfile(@CurrentSecurityContext(expression = "authentication") AccountAuthentication auth,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                @RequestBody String body) {
        return httpUtil.formResponse(businessProfileService.validateBusinessProfile(authHeader, body));
    }
}
