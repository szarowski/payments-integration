package com.payments.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.payments.config.TestJacksonConfig;
import com.payments.config.TestRestConfig;
import com.payments.error.model.Errors;
import com.payments.error.model.RequestError;
import com.payments.model.PaymentAmountJson;
import com.payments.model.PaymentType;
import com.payments.model.QuoteRequestJson;
import com.payments.model.QuoteResponseJson;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.payments.feign.JsonUtils.asJson;
import static com.payments.model.PaymentAmountJsonBuilder.paymentAmountJsonBuilder;
import static com.payments.model.QuoteRequestJsonBuilder.quoteRequestJsonBuilder;
import static com.payments.model.QuoteResponseJsonBuilder.quoteResponseJsonBuilder;
import static com.payments.util.IntegrationTestHelper.apiUrl;
import static com.payments.wiremock.WireMockRegistry.wireMockClassRule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.RequestEntity.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestJacksonConfig.class, TestRestConfig.class})
public class PaymentControllerITest {

    private static final String AUTHORIZATION_TOKEN = "Bearer dd8f7bf8-3ae7-4be6-8781-ee38b3408e77";

    @LocalServerPort
    private int port;

    @Rule
    public final WireMockClassRule TRANSWER_WISE = wireMockClassRule(9445);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @After
    public void wipeDb() {
        jdbcTemplate.update("DELETE FROM payments");
    }

    @Test
    public void shouldCreateAndGetPayment() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");

        PaymentAmountJson paymentAmount = paymentAmountJsonBuilder().build();
        QuoteRequestJson quoteRequest = quoteRequestJsonBuilder().source(Currency.getInstance("GBP"))
                .target(Currency.getInstance((String) users.get(0).get("PAYOUT_CURRENCY")))
                .profile("252").sourceAmount(paymentAmount.getPaymentAmount())
                .rateType("FIXED")
                .type(PaymentType.REGULAR).build();
        QuoteResponseJson quoteResponse = quoteResponseJsonBuilder().source(Currency.getInstance("GBP").toString())
                .target(users.get(0).get("PAYOUT_CURRENCY").toString())
                .build();

        givenTranswerWiseReturnsQuote(quoteRequest, quoteResponse, AUTHORIZATION_TOKEN);

        ResponseEntity<QuoteResponseJson> response = restTemplate.exchange(
                post(apiUrl("/v1/payments/" + users.get(0).get("ID"), port)).header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .body(paymentAmount), QuoteResponseJson.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody()).isEqualTo(quoteResponse);

        ResponseEntity<List<QuoteResponseJson>> response2 = restTemplate.exchange(
                RequestEntity.get(apiUrl("/v1/payments", port)).header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE).build(),
                new ParameterizedTypeReference<List<QuoteResponseJson>>() {});

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().size()).isEqualTo(1);
        assertThat(response2.getBody().get(0)).isEqualTo(quoteResponse);

        int dataCount  = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM payments WHERE id = ?", int.class, response.getBody().getId());
        assertThat(dataCount).isEqualTo(1);
    }

    @Test
    public void shouldReturn400ForNullPaymentAmount() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");

        QuoteRequestJson quoteRequest = quoteRequestJsonBuilder().source(Currency.getInstance("GBP"))
                .target(Currency.getInstance((String) users.get(0).get("PAYOUT_CURRENCY")))
                .profile("252").sourceAmount(null)
                .rateType("FIXED")
                .type(PaymentType.REGULAR).build();
        QuoteResponseJson quoteResponse = quoteResponseJsonBuilder().source(Currency.getInstance("GBP").toString())
                .target(users.get(0).get("PAYOUT_CURRENCY").toString())
                .build();

        givenTranswerWiseReturnsQuote(quoteRequest, quoteResponse, AUTHORIZATION_TOKEN);

        ResponseEntity<Errors> response = restTemplate.exchange(
                post(apiUrl("/v1/payments/" + users.get(0).get("ID"), port)).header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .body(null), Errors.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();

        int dataCount  = jdbcTemplate.queryForObject("SELECT count(*) FROM payments", int.class);
        assertThat(dataCount).isEqualTo(0);
    }

    @Test
    public void shouldReturn422ForNullResponse() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");

        PaymentAmountJson paymentAmount = paymentAmountJsonBuilder().paymentAmount(null).build();
        QuoteRequestJson quoteRequest = quoteRequestJsonBuilder().source(Currency.getInstance("GBP"))
                .target(Currency.getInstance((String) users.get(0).get("PAYOUT_CURRENCY")))
                .profile("252").sourceAmount(paymentAmount.getPaymentAmount())
                .rateType("FIXED")
                .type(PaymentType.REGULAR).build();
        QuoteResponseJson quoteResponse = quoteResponseJsonBuilder().source(Currency.getInstance("GBP").toString())
                .target(users.get(0).get("PAYOUT_CURRENCY").toString())
                .build();

        givenTranswerWiseReturnsQuote(quoteRequest, quoteResponse, AUTHORIZATION_TOKEN);

        ResponseEntity<Errors> response = restTemplate.exchange(
                post(apiUrl("/v1/payments/" + users.get(0).get("ID"), port)).header(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .body(paymentAmount), Errors.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody().getErrors())
                .hasSize(1)
                .containsOnly(new RequestError("NotNull", "payment_amount must not be null", "payment_amount", null));

        int dataCount  = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM payments", int.class);
        assertThat(dataCount).isEqualTo(0);
    }

    private void givenTranswerWiseReturnsQuote(QuoteRequestJson quoteRequest, QuoteResponseJson quoteResponse, String tokenHeader) {
        TRANSWER_WISE.stubFor(
                WireMock.post(urlPathEqualTo("/v1/quotes"))
                        .withHeader(AUTHORIZATION, equalTo(tokenHeader))
                        .withHeader(CONTENT_TYPE, equalTo(APPLICATION_JSON_UTF8_VALUE))
                        .withRequestBody(equalTo(asJson(quoteRequest)))
                        .willReturn(aResponse()
                                .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                                .withStatus(CREATED.value())
                                .withBody(asJson(quoteResponse)))
        );
    }
}