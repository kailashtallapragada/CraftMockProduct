package com.ktcraft.validationproduct.http.util;

import com.ktcraft.validationproduct.service.businessprofile.dao.ServerResponse;
import com.ktcraft.validationproduct.service.exceptions.InternalServerException;
import com.ktcraft.validationproduct.service.exceptions.ServiceUnavailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HttpUtil {

    public HttpHeaders getHeaders(final java.net.http.HttpHeaders headers) {
        if (headers == null) {
            return null;
        }
        final HttpHeaders result = new HttpHeaders();
        for (Map.Entry<String, List<String>> header: headers.map().entrySet()) {
            if (header.getValue() != null && header.getValue().size() > 0) {
                result.add(header.getKey(), header.getValue().get(0));
            }
        }
        return result;
    }

    public ResponseEntity<String> formResponse(ServerResponse response) {
        if (response == null) {
            throw new InternalServerException();
        }
        if (response.getHttpStatus().is5xxServerError()) {
            throw new ServiceUnavailableException();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        if (response.getHeaders().get(HttpHeaders.CONTENT_TYPE) != null
                && response.getHeaders().get(HttpHeaders.CONTENT_TYPE).size() > 0) {
            httpHeaders.put(HttpHeaders.CONTENT_TYPE, response.getHeaders().get(HttpHeaders.CONTENT_TYPE));
        }
        final ResponseEntity<String> result = new ResponseEntity<>(
                response.getBody(), httpHeaders, response.getHttpStatus());
        return result;
    }
}
