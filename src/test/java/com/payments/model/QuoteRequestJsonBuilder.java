package com.payments.model;

import com.payments.util.Random;

import java.math.BigDecimal;
import java.util.Currency;

public final class QuoteRequestJsonBuilder {
    private String profile = Random.string();
    private Currency source = Random.currency();
    private Currency target = Random.currency();
    private String rateType = "FIXED";
    private BigDecimal targetAmount;
    private BigDecimal sourceAmount = Random.amountVal();
    private PaymentType type = Random.value(PaymentType.BALANCE_CONVERSION, PaymentType.BALANCE_PAYOUT, PaymentType.REGULAR);

    private QuoteRequestJsonBuilder() {
    }

    public static QuoteRequestJsonBuilder quoteRequestJsonBuilder() {
        return new QuoteRequestJsonBuilder();
    }

    public QuoteRequestJsonBuilder profile(String profile) {
        this.profile = profile;
        return this;
    }

    public QuoteRequestJsonBuilder source(Currency source) {
        this.source = source;
        return this;
    }

    public QuoteRequestJsonBuilder target(Currency target) {
        this.target = target;
        return this;
    }

    public QuoteRequestJsonBuilder rateType(String rateType) {
        this.rateType = rateType;
        return this;
    }

    public QuoteRequestJsonBuilder targetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
        return this;
    }

    public QuoteRequestJsonBuilder sourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
        return this;
    }

    public QuoteRequestJsonBuilder type(PaymentType type) {
        this.type = type;
        return this;
    }

    public QuoteRequestJson build() {
        return new QuoteRequestJson(profile, source, target, rateType, targetAmount, sourceAmount, type);
    }
}
