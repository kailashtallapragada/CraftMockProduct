package com.ktcraft.validationproduct.service.businessprofile.service;


import com.ktcraft.validationproduct.service.businessprofile.dao.ServerResponse;

public interface BusinessProfileService {

    ServerResponse getBusinessProfile(String authHeader);

    ServerResponse updateBusinessProfile(String authHeader, String body);

    ServerResponse validateBusinessProfile(String authHeader, String body);
}
