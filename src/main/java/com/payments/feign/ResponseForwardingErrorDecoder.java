package com.payments.feign;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseForwardingErrorDecoder implements ErrorDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseForwardingErrorDecoder.class);

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(final String methodKey, final Response response) {
        if (response.status() >= 400) {
            try {
                final String body = response.body() == null ? null : Util.toString(response.body().asReader());
                final Exception exception = new UpstreamServiceException(methodKey, response.status(), body, response.headers());
                LOG.info("{}, response body: {}", exception.getMessage(), body);
                return exception;
            } catch (IOException ioe) {
                LOG.warn("Error constructing UpstreamServiceException", ioe);
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
