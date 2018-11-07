package com.payments.service;

import com.payments.model.QuoteResponseJson;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Service to process requests from the Payment REST Controller.
 */
public interface PaymentService {

    /**
     * Create a TransferWise Quote and store results in the database.
     *
     * @param userId the ID of the user
     * @param paymentAmount the payment amount
     * @return the Optional<QuoteResponseJson> object created in case of success.
     */
    Optional<QuoteResponseJson> createPayment(UUID userId, BigDecimal paymentAmount);

    /** Get all payments in the database
     *
     * @return the List<QuoteResponseJson> objects
     */
    List<QuoteResponseJson> getAllUsers();
}