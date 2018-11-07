package com.payments.feign;

import com.payments.util.Random;
import feign.FeignException;
import feign.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.payments.util.MapBuilder.mapWith;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponseForwardingErrorDecoderTest {

    private ResponseForwardingErrorDecoder decoder = new ResponseForwardingErrorDecoder();

    @Test
    public void shouldReturnUpstreamServiceExceptionFor4xxError() {
        String responseBody = Random.string();
        String headerName = CONTENT_TYPE;
        List<String> headerValue = singletonList(MediaType.TEXT_PLAIN_VALUE);
        Response response = Response.builder()
                .status(422)
                .body(responseBody.getBytes())
                .headers(mapWith(headerName, headerValue)).build();

        Exception exception = decoder.decode(Random.string(), response);

        assertThat(exception).isExactlyInstanceOf(UpstreamServiceException.class);
        assertThat(((UpstreamServiceException) exception).getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(((UpstreamServiceException) exception).getResponseBody()).isEqualTo(responseBody);
        assertThat(((UpstreamServiceException) exception).getHeaders()).containsEntry(headerName.toLowerCase(), headerValue);
    }

    @Test
    public void shouldReturnUpstreamServiceExceptionFor5xxError() {
        String responseBody = Random.string();
        String headerName = CONTENT_TYPE;
        List<String> headerValue = singletonList(MediaType.TEXT_PLAIN_VALUE);
        Response response = Response.builder()
                .status(502)
                .body(responseBody.getBytes())
                .headers(mapWith(headerName, headerValue)).build();

        Exception exception = decoder.decode(Random.string(), response);

        assertThat(exception).isExactlyInstanceOf(UpstreamServiceException.class);
        assertThat(((UpstreamServiceException) exception).getStatus()).isEqualTo(HttpStatus.BAD_GATEWAY);
        assertThat(((UpstreamServiceException) exception).getResponseBody()).isEqualTo(responseBody);
        assertThat(((UpstreamServiceException) exception).getHeaders()).containsEntry(headerName.toLowerCase(), headerValue);
    }

    @Test
    public void shouldUseDefaultErrorDecoderForAnyOtherStatusCode() {
        String methodName = Random.string();
        String responseBody = Random.string();
        Response response = Response.builder()
                .status(303)
                .body(responseBody.getBytes())
                .headers(emptyMap()).build();

        Exception exception = decoder.decode(methodName, response);

        assertThat(exception)
                .isExactlyInstanceOf(FeignException.class)
                .hasMessage("status 303 reading " + methodName + "; content:\n" + responseBody);
    }
}