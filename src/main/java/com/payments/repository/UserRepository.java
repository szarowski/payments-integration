package com.payments.repository;

import com.payments.model.internal.UserData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    /**
     * Finds a UserData in the database.
     *
     * @param id the ID to find as UUID
     * @return the Optional<UserData> object
     */
    Optional<UserData> findUserById(UUID id);

    /**
     * Finds all UserData in the database.
     *
     * @return the List<UserData> objects
     */
    List<UserData> findUsers();
}
