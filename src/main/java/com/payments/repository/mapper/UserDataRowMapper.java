package com.payments.repository.mapper;

import com.payments.model.internal.UserData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.UUID;

/**
 * Row mapper to retrieve mapped rows from the user table
 */
public class UserDataRowMapper implements RowMapper<UserData> {

    @Override
    public UserData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new UserData(
                rs.getObject("id", UUID.class),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Currency.getInstance(rs.getString("payout_currency")));
    }
}