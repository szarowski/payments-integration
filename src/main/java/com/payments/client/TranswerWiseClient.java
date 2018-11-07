package com.payments.client;

import com.payments.model.QuoteRequestJson;
import com.payments.model.QuoteResponseJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Feign client for fetching external data
 */
@FeignClient("transwerwise")
public interface TranswerWiseClient {

    @PostMapping(value = "/v1/quotes", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    QuoteResponseJson postQuotes(@RequestHeader(AUTHORIZATION) String authorization,
                                 @Valid @RequestBody QuoteRequestJson quoteRequest);
}