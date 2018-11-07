package com.payments.service;

import com.payments.client.TranswerWiseClient;
import com.payments.model.QuoteRequestJson;
import com.payments.model.QuoteResponseJson;
import com.payments.model.internal.UserData;
import com.payments.repository.PaymentRepository;
import com.payments.repository.UserRepository;
import com.payments.transform.PaymentTransformer;
import com.payments.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.payments.model.QuoteRequestJsonBuilder.quoteRequestJsonBuilder;
import static com.payments.model.QuoteResponseJsonBuilder.quoteResponseJsonBuilder;
import static com.payments.model.internal.UserDataBuilder.userDataBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    private static final String AUTHORIZATION_TOKEN = "Bearer dd8f7bf8-3ae7-4be6-8781-ee38b3408e77";

    @Mock
    private PaymentTransformer paymentTransformer;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private TranswerWiseClient transwerWiseClient;

    private PaymentServiceImpl service;

    @Before
    public void setUp() {
        service = new PaymentServiceImpl(paymentTransformer, userRepository, paymentRepository, transwerWiseClient);
    }

    @Test
    public void shouldCreatePaymentData() {
        BigDecimal paymentAmount = Random.amountVal();
        UserData user = userDataBuilder().build();
        QuoteRequestJson request = quoteRequestJsonBuilder().sourceAmount(paymentAmount).target(user.getPayoutCurrency())
                .build();
        QuoteResponseJson response = quoteResponseJsonBuilder().build();

        given(userRepository.findUserById(user.getId())).willReturn(Optional.of(user));
        doNothing().when(paymentRepository).savePaymentItems(response);
        given(paymentTransformer.toQuoteRequestJson(paymentAmount, user)).willReturn(request);
        given(transwerWiseClient.postQuotes(AUTHORIZATION_TOKEN, request)).willReturn(response);

        Optional<QuoteResponseJson> storedData = service.createPayment(user.getId(), paymentAmount);

        assertThat(storedData.isPresent()).isTrue();
        assertThat(storedData.get()).isEqualTo(response);
    }

    @Test
    public void shouldReturnNullIfNotFoundUserDataById() {
        UUID id = Random.uuid();

        given(userRepository.findUserById(id)).willReturn(Optional.empty());

        Optional<QuoteResponseJson> actualData = service.createPayment(id, Random.amountVal());

        assertThat(actualData.isPresent()).isFalse();
        assertThat(actualData).isEqualTo(Optional.empty());
    }
}
