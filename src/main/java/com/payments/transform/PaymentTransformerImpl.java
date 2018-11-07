package com.payments.transform;

import com.payments.model.QuoteRequestJson;
import com.payments.model.PaymentType;
import com.payments.model.internal.UserData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * The implementation of the PaymentData transformer.
 */
@Component
public class PaymentTransformerImpl implements PaymentTransformer {

    @Override
    public QuoteRequestJson toQuoteRequestJson(final BigDecimal paymentAmount, final UserData userData) {
        return new QuoteRequestJson("252",
                Currency.getInstance("GBP"),
                userData.getPayoutCurrency(),
                "FIXED",
                null,
                paymentAmount,
                PaymentType.REGULAR
        );
    }
}
