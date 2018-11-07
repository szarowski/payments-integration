package com.payments.model;

import com.payments.util.Random;

import java.math.BigDecimal;

public final class QuoteResponseJsonBuilder {
    private Integer id = Random.intVal();
    private String source = Random.currency().toString();
    private String target = Random.currency().toString();
    private BigDecimal targetAmount = Random.amountVal();
    private BigDecimal sourceAmount = Random.amountVal();
    private BigDecimal rate = Random.amountVal();
    private BigDecimal fee = Random.amountVal();

    private QuoteResponseJsonBuilder() {
    }

    public static QuoteResponseJsonBuilder quoteResponseJsonBuilder() {
        return new QuoteResponseJsonBuilder();
    }

    public QuoteResponseJsonBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public QuoteResponseJsonBuilder source(String source) {
        this.source = source;
        return this;
    }

    public QuoteResponseJsonBuilder target(String target) {
        this.target = target;
        return this;
    }

    public QuoteResponseJsonBuilder targetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
        return this;
    }

    public QuoteResponseJsonBuilder sourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
        return this;
    }

    public QuoteResponseJsonBuilder rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public QuoteResponseJsonBuilder fee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public QuoteResponseJson build() {
        return new QuoteResponseJson(id, source, target, targetAmount, sourceAmount, rate, fee);
    }
}
