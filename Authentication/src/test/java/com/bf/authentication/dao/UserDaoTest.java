package com.bf.authentication.dao;

import com.bf.authentication.dao.UserDao;
import com.bf.authentication.entity.Role;
import com.bf.authentication.entity.User;
import com.bf.authentication.entity.UserRole;
import com.bf.authentication.security.AuthUserDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import com.bf.authentication.controller.RegistrationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(value = "test")
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    @Transactional
    void test_findUserByUsername() {
        assertNotEquals(userDao.findUserByUsername("a").isPresent(), true);
    }

    @Test
    @Transactional
    void test_findUserByEmail() {
        assertNotEquals(userDao.findUserByEmail("a").isPresent(), true);
    }
}
