package com.ktcraft.validationproduct.service.businessprofile.service;

import com.ktcraft.validationproduct.http.util.HttpUtil;
import com.ktcraft.validationproduct.service.businessprofile.dao.ServerResponse;
import com.ktcraft.validationproduct.service.exceptions.InternalServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BusinessProfileServiceImpl implements BusinessProfileService {

    @Value("${businessprofileservice.baseUrl}")
    private String baseUrl;

    @Autowired
    HttpClient httpClient;

    @Autowired
    HttpUtil httpUtil;

    private static final String PROFILE_PATH = "/profile";
    private static final String VERIFY_PATH = PROFILE_PATH + "/verify";

    @Override
    public ServerResponse getBusinessProfile(String authHeader) {
        try {
            final HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(baseUrl + PROFILE_PATH))
                    .headers(HttpHeaders.AUTHORIZATION, authHeader)
                    .GET().build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ServerResponse(HttpStatus.resolve(
                    response.statusCode()), httpUtil.getHeaders(response.headers()), response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InternalServerException();
        }
    }

    @Override
    public ServerResponse updateBusinessProfile(String authHeader, String body) {
        try {
            final HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(baseUrl + PROFILE_PATH))
                    .headers(HttpHeaders.AUTHORIZATION, authHeader)
                    .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .PUT(HttpRequest.BodyPublishers.ofString(body)).build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ServerResponse(HttpStatus.resolve(
                    response.statusCode()), httpUtil.getHeaders(response.headers()), response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InternalServerException();
        }
    }

    @Override
    public ServerResponse validateBusinessProfile(String authHeader, String body) {
        try {
            final HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(baseUrl + VERIFY_PATH))
                    .headers(HttpHeaders.AUTHORIZATION, authHeader)
                    .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                    .build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ServerResponse(HttpStatus.resolve(
                    response.statusCode()), httpUtil.getHeaders(response.headers()), response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InternalServerException();
        }
    }
}
