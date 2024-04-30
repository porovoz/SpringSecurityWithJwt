package com.bestapp.SpringSecurityWithJWT.repository;

import com.bestapp.SpringSecurityWithJWT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * An interface containing methods for working with a database of objects of the UserEntity class
 * @see UserEntity
 * @see com.bestapp.SpringSecurityWithJWT.service.UserService
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Getting user by email
     * @param email user email in database
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks by email if user exists in database
     * @param email user email in database
     * @return <B>true</B> if the user exists in database, otherwise <B>false</B> .
     */
    boolean existsByEmail(String email);
}
