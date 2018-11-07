package com.payments.repository;

import com.payments.model.internal.UserData;
import com.payments.repository.mapper.UserDataRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.payments.util.MapBuilder.mapWith;

/**
 * Implementation of the User Repository providing persistence services.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public UserRepositoryImpl(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<UserData> findUserById(final UUID id) {
        final Map<String, ?> params = mapWith("id", id);
        return jdbc.query("SELECT * FROM users WHERE id = :id", params, new UserDataRowMapper())
                .stream().findFirst();
    }

    @Override
    public List<UserData> findUsers() {
        return new ArrayList<>(jdbc.query("SELECT * FROM users", new UserDataRowMapper()));
    }
}