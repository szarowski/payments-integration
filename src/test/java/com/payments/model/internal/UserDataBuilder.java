package com.payments.model.internal;

import com.payments.util.Random;

import java.util.Currency;
import java.util.UUID;

public final class UserDataBuilder {
    private UUID id = Random.uuid();
    private String firstName = Random.alphaNumeric();
    private String lastName = Random.alphaNumeric();
    private Currency payoutCurrency = Random.currency();

    private UserDataBuilder() {
    }

    public static UserDataBuilder userDataBuilder() {
        return new UserDataBuilder();
    }

    public UserDataBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public UserDataBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDataBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDataBuilder payoutCurrency(Currency payoutCurrency) {
        this.payoutCurrency = payoutCurrency;
        return this;
    }

    public UserData build() {
        return new UserData(id, firstName, lastName, payoutCurrency);
    }
}
