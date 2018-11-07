package com.payments.controller;

import com.payments.model.PaymentAmountJson;
import com.payments.model.QuoteResponseJson;
import com.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Payment Controller to handle the data from an external source.
 */
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuoteResponseJson> createPayment(@PathVariable("userId") final UUID userId,
                                                           @Valid @RequestBody final PaymentAmountJson paymentAmount) {
        final Optional<QuoteResponseJson> quoteResponse = paymentService.createPayment(userId, paymentAmount.getPaymentAmount());
        return quoteResponse.map(qr -> new ResponseEntity<>(qr, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<QuoteResponseJson> getPayments() {
        return paymentService.getAllUsers();
    }
}