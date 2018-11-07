package com.payments.repository.mapper;

import com.payments.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserDataRowMapperTest {

    private static final String COL_ID = "id";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_PAYOUT_CURRENCY = "payout_currency";

    private ResultSet rs;
    private static final int ROW_NUM = 88;

    @Before
    public void setupMocks() {
        rs = mock(ResultSet.class);
    }

    @Test
    public void testInteractions() throws SQLException {
        when(rs.getBytes(anyString())).thenReturn(Random.uuid().toString().getBytes());
        when(rs.getString(COL_FIRST_NAME)).thenReturn(Random.string());
        when(rs.getString(COL_PAYOUT_CURRENCY)).thenReturn(Random.currency().getCurrencyCode());

        final UserDataRowMapper rowMapper = new UserDataRowMapper();
        rowMapper.mapRow(rs, ROW_NUM);
        verify(rs, Mockito.times(1)).getObject(COL_ID, UUID.class);
        verify(rs, Mockito.times(1)).getString(COL_FIRST_NAME);
        verify(rs, Mockito.times(1)).getString(COL_LAST_NAME);
        verify(rs, Mockito.times(1)).getString(COL_PAYOUT_CURRENCY);

        verifyNoMoreInteractions(rs);
    }
}