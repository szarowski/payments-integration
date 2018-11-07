package com.payments.transform;

import com.payments.model.QuoteRequestJson;
import com.payments.model.internal.UserData;

import java.math.BigDecimal;

/**
 * PaymentData transformer.
 */
public interface PaymentTransformer {

    /**
     * Transforms a PaymentAmount and UserData objects to a new QuoteRequestJson element.
     *
     * @param paymentAmount the payment amount
     * @param userData the user data
     * @return the QuoteRequestJson object
     */
    QuoteRequestJson toQuoteRequestJson(BigDecimal paymentAmount, UserData userData);
}