package com.bf.authentication.service;

//import com.bf.authentication.config.SecurityConfig;
import com.bf.authentication.dao.RegistrationTokenDao;
import com.bf.authentication.dao.RoleDao;
import com.bf.authentication.dao.UserDao;
import com.bf.authentication.domain.request.UserRequest;
import com.bf.authentication.entity.RegistrationToken;
import com.bf.authentication.entity.Role;
import com.bf.authentication.entity.User;
import com.bf.authentication.entity.UserRole;
import com.bf.authentication.exception.NoAuthorizationException;
import com.bf.authentication.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import io.jsonwebtoken.Jwts;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
//@EnableAutoConfiguration(exclude = SecurityConfig.class)
public class RegistrationServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private RegistrationTokenDao registrationTokenDao;
    @InjectMocks
    RegistrationService service;
    private Long tokenDuration = 3 * 60 * 60 * 1000L;
    String realtoken = "bearer: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJociIsInBlcm1pc3Npb25zIjpbeyJhdXRob3JpdHkiOiJIUiJ9XX0.cOCTF9zVVr6BgvZqhFkCFBk1ly246YYYHrMqNYi0ywM";

    @Test
    void test_generateToken() throws NoAuthorizationException {
        String username = "x";
        UserRole ur = new UserRole();
        Role role = new Role();
        role.setRoleName("HR");
        ur.setRole(role);
        ur.setActiveFlag(true);
        HashSet s = new HashSet<>();
        s.add(ur);
        User user = User.builder().id(1L).activeFlag(true).roles(s).build();
        Mockito.when(userDao.findUserByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));
        Mockito.when(registrationTokenDao.save(any(RegistrationToken.class))).thenReturn(null);
        assertNotEquals(service.generateToken("12345678", realtoken), realtoken);
    }
}
