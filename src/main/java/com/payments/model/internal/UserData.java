package com.payments.model.internal;

import com.google.common.base.MoreObjects;

import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

public final class UserData {

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final Currency payoutCurrency;

    public UserData(final UUID id, final String firstName, final String lastName, final Currency payoutCurrency) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.payoutCurrency = payoutCurrency;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Currency getPayoutCurrency() {
        return payoutCurrency;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserData userData = (UserData) o;
        return Objects.equals(id, userData.id) &&
                Objects.equals(firstName, userData.firstName) &&
                Objects.equals(lastName, userData.lastName) &&
                Objects.equals(payoutCurrency, userData.payoutCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, payoutCurrency);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("payoutCurrency", payoutCurrency)
                .toString();
    }
}