package com.payments.repository;

import com.payments.model.QuoteResponseJson;
import com.payments.repository.mapper.PaymentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.payments.util.MapBuilder.mapWith;

/**
 * Implementation of the Payment Repository providing persistence services for PaymentController.
 */
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public PaymentRepositoryImpl(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void savePaymentItems(final QuoteResponseJson data) {
        jdbc.update("INSERT INTO payments(id, source, target, source_amount, target_amount, rate, fee) " +
                "VALUES (:id, :source, :target, :source_amount, :target_amount, :rate, :fee)",
                mapWith("id", (Object) data.getId())
                        .and("source", data.getSource())
                        .and("target", data.getTarget())
                        .and("source_amount", data.getSourceAmount())
                        .and("target_amount", data.getTargetAmount())
                        .and("rate", data.getRate())
                        .and("fee", data.getFee()));
    }

    @Override
    public List<QuoteResponseJson> findPayments() {
        return new ArrayList<>(jdbc.query("SELECT * FROM payments", new PaymentRowMapper()));
    }
}
