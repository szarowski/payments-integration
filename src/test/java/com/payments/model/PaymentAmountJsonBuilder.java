package com.payments.model;

import com.payments.util.Random;

import java.math.BigDecimal;

public final class PaymentAmountJsonBuilder {
    private BigDecimal paymentAmount = Random.amountVal();

    private PaymentAmountJsonBuilder() {
    }

    public static PaymentAmountJsonBuilder paymentAmountJsonBuilder() {
        return new PaymentAmountJsonBuilder();
    }

    public PaymentAmountJsonBuilder paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public PaymentAmountJson build() {
        return new PaymentAmountJson(paymentAmount);
    }
}
