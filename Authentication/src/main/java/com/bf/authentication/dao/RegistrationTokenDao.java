package com.bf.authentication.dao;

import com.bf.authentication.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationTokenDao extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findByToken(String token);
}
