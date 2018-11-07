package com.payments.feign;

import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.lang.String.format;

public class UpstreamServiceException extends RuntimeException {
    private final HttpStatus status;
    private final String responseBody;
    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    public UpstreamServiceException(final String methodKey, final int status, final String responseBody, final Map<String, Collection<String>> headers) {
        super(format("Upstream service call %s returned status %d", methodKey, status));
        this.status = HttpStatus.valueOf(status);
        this.responseBody = responseBody;
        headers.forEach((header, values) -> this.headers.put(header, new ArrayList<>(values)));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }
}
