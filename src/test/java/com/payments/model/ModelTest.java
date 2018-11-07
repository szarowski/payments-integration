package com.payments.model;

import be.joengenduvel.java.verifiers.ToStringVerifier;
import com.payments.error.model.Errors;
import com.payments.error.model.RequestError;
import com.payments.model.internal.UserData;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.Currency;

import static com.payments.error.model.ErrorsBuilder.errorsBuilder;
import static com.payments.error.model.RequestErrorBuilder.requestErrorBuilder;
import static com.payments.model.PaymentAmountJsonBuilder.paymentAmountJsonBuilder;
import static com.payments.model.QuoteRequestJsonBuilder.quoteRequestJsonBuilder;
import static com.payments.model.QuoteResponseJsonBuilder.quoteResponseJsonBuilder;
import static com.payments.model.internal.UserDataBuilder.userDataBuilder;

public class ModelTest {

    @Test
    public void shouldTestEqualsAndHashCode() {
        EqualsVerifier.forClass(UserData.class)
                .withPrefabValues(Currency.class, Currency.getInstance("EUR"), Currency.getInstance("GBP"))
                .verify();
        EqualsVerifier.forClass(QuoteRequestJson.class)
                .withPrefabValues(Currency.class, Currency.getInstance("EUR"), Currency.getInstance("GBP"))
                .verify();
        EqualsVerifier.forClass(QuoteResponseJson.class)
                .withPrefabValues(Currency.class, Currency.getInstance("EUR"), Currency.getInstance("GBP"))
                .verify();
        EqualsVerifier.forClass(PaymentAmountJson.class).verify();
        EqualsVerifier.forClass(Errors.class).verify();
        EqualsVerifier.forClass(RequestError.class).verify();
    }

    @Test
    public void shouldTestToString() {
        ToStringVerifier.forClass(UserData.class).containsAllPrivateFields(userDataBuilder().build());
        ToStringVerifier.forClass(QuoteRequestJson.class).containsAllPrivateFields(quoteRequestJsonBuilder().build());
        ToStringVerifier.forClass(QuoteResponseJson.class).containsAllPrivateFields(quoteResponseJsonBuilder().build());
        ToStringVerifier.forClass(PaymentAmountJson.class).containsAllPrivateFields(paymentAmountJsonBuilder().build());
        ToStringVerifier.forClass(Errors.class).containsAllPrivateFields(errorsBuilder().build());
        ToStringVerifier.forClass(RequestError.class).containsAllPrivateFields(requestErrorBuilder().build());
    }
}