package com.payments.repository.mapper;

import com.payments.model.QuoteResponseJson;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Row mapper to retrieve mapped rows from the payment table
 */
public class PaymentRowMapper implements RowMapper<QuoteResponseJson> {

    @Override
    public QuoteResponseJson mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new QuoteResponseJson(
                rs.getInt("id"),
                rs.getString("source"),
                rs.getString("target"),
                rs.getBigDecimal("target_amount"),
                rs.getBigDecimal("source_amount"),
                rs.getBigDecimal("rate"),
                rs.getBigDecimal("fee"));
    }
}