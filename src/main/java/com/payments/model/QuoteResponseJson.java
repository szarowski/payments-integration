package com.payments.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.Objects;

public final class QuoteResponseJson {

    private final Integer id;

    private final String source;

    private final String target;

    private final BigDecimal targetAmount;

    private final BigDecimal sourceAmount;

    private final BigDecimal rate;

    private final BigDecimal fee;

    @JsonCreator
    public QuoteResponseJson(final Integer id, final String source, final String target, final BigDecimal targetAmount,
                             final BigDecimal sourceAmount, final BigDecimal rate, final BigDecimal fee) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.targetAmount = targetAmount;
        this.sourceAmount = sourceAmount;
        this.rate = rate;
        this.fee = fee;
    }

    public Integer getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QuoteResponseJson that = (QuoteResponseJson) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(targetAmount, that.targetAmount) &&
                Objects.equals(sourceAmount, that.sourceAmount) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(fee, that.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, target, targetAmount, sourceAmount, rate, fee);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("source", source)
                .add("target", target)
                .add("targetAmount", targetAmount)
                .add("sourceAmount", sourceAmount)
                .add("rate", rate)
                .add("fee", fee)
                .toString();
    }
}
