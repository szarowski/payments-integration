package com.payments.service;

import com.payments.client.TranswerWiseClient;
import com.payments.model.QuoteResponseJson;
import com.payments.model.internal.UserData;
import com.payments.repository.PaymentRepository;
import com.payments.repository.UserRepository;
import com.payments.transform.PaymentTransformer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.payments.feign.UpstreamServiceHelper.withUpstreamErrorHandling;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * The Implementation of the Service to process requests from the Payment REST Controller.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOG = getLogger(PaymentServiceImpl.class);

    private static final String AUTHORIZATION_TOKEN = "Bearer dd8f7bf8-3ae7-4be6-8781-ee38b3408e77";

    private final PaymentTransformer paymentTransformer;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final TranswerWiseClient transwerWiseClient;

    @Autowired
    public PaymentServiceImpl(final PaymentTransformer paymentTransformer,
                              final UserRepository userRepository,
                              final PaymentRepository paymentRepository,
                              final TranswerWiseClient transwerWiseClient) {
        this.paymentTransformer = paymentTransformer;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.transwerWiseClient = transwerWiseClient;
    }

    @Override
    public Optional<QuoteResponseJson> createPayment(final UUID userId, BigDecimal paymentAmount) {
        QuoteResponseJson quoteResponse = null;
        final Optional<UserData> userData = userRepository.findUserById(userId);
        final boolean isUserData = userData.isPresent();
        if (userData.isPresent()) {
            LOG.info("User Data retrieved {}", userData);
            quoteResponse = withUpstreamErrorHandling(() -> transwerWiseClient.postQuotes(
                    AUTHORIZATION_TOKEN,
                    paymentTransformer.toQuoteRequestJson(paymentAmount, userData.get())));
            LOG.info("Payment data created for user ID {} and payment amount {}", userId, paymentAmount);
            paymentRepository.savePaymentItems(quoteResponse);
        }
        LOG.info("Created new payment quote {}", isUserData ? quoteResponse.toString() : "failed");
        return Optional.ofNullable(quoteResponse);
    }

    @Override
    public List<QuoteResponseJson> getAllUsers() {
        return paymentRepository.findPayments();
    }
}