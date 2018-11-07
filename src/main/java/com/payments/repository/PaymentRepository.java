package com.payments.repository;

import com.payments.model.QuoteResponseJson;

import java.util.List;

/**
 * Payment Repository providing persistence.
 */
public interface PaymentRepository {

    /**
     * Saves the QuoteResponseJson object into the database.
     *
     * @param data the QuoteResponseJson object to save
     */
    void savePaymentItems(QuoteResponseJson data);

     /** Finds all payments in the database
      *
      * @return the List<QuoteResponseJson> objects
      */
    List<QuoteResponseJson> findPayments();
}