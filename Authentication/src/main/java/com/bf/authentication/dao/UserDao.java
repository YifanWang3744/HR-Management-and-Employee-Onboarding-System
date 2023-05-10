package com.bf.authentication.dao;

import com.bf.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
