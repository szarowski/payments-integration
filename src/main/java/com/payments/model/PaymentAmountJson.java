package com.payments.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public final class PaymentAmountJson {

    @NotNull
    private final BigDecimal paymentAmount;

    @JsonCreator
    public PaymentAmountJson(final BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentAmountJson that = (PaymentAmountJson) o;
        return Objects.equals(paymentAmount, that.paymentAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentAmount);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("paymentAmount", paymentAmount)
                .toString();
    }
}