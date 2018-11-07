package com.payments.model;

import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class QuoteRequestJson {

    @NotNull
    private final String profile;

    @NotNull
    private final Currency source;

    @NotNull
    private final Currency target;

    @NotNull
    private final String rateType;

    private final BigDecimal targetAmount;

    private final BigDecimal sourceAmount;

    @NotNull
    private final PaymentType type;

    public QuoteRequestJson(final String profile, final Currency source, final Currency target, final String rateType,
                            final BigDecimal targetAmount, final BigDecimal sourceAmount, final PaymentType type) {
        this.profile = profile;
        this.source = source;
        this.target = target;
        this.rateType = rateType;
        this.targetAmount = targetAmount;
        this.sourceAmount = sourceAmount;
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public Currency getSource() {
        return source;
    }

    public Currency getTarget() {
        return target;
    }

    public String getRateType() {
        return rateType;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public PaymentType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteRequestJson that = (QuoteRequestJson) o;
        return Objects.equals(profile, that.profile) &&
                Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(rateType, that.rateType) &&
                Objects.equals(targetAmount, that.targetAmount) &&
                Objects.equals(sourceAmount, that.sourceAmount) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, source, target, rateType, targetAmount, sourceAmount, type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("profile", profile)
                .add("source", source)
                .add("target", target)
                .add("rateType", rateType)
                .add("targetAmount", targetAmount)
                .add("sourceAmount", sourceAmount)
                .add("type", type)
                .toString();
    }
}