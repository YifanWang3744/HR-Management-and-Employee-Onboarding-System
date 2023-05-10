package com.bf.authentication.service;

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

@ExtendWith({MockitoExtension.class})
class UserServiceTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService service;

    @Test
    void loadUserByUsername() {
        String identifier = "1";
        Role role = Role.builder().roleName("HR").build();
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setActiveFlag(true);
        HashSet s = new HashSet<>();
        s.add(userRole);
        User user = User.builder().id(1L).email("x").username("x").roles(s).password("1").build();
        Mockito.when(userDao.findUserByUsername(identifier)).thenReturn(Optional.ofNullable(user));
        AuthUserDetail expected = AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password("1")
                .authorities(new ArrayList<>())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        assertEquals(service.loadUserByUsername(identifier).getUsername(), expected.getUsername());
    }

    @Test
    void test_test() {
        Long userId = 1L;
        User user = User.builder().id(1L).username("x").build();
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.ofNullable(user));
        User expected = User.builder().id(1L).username("x").build();
        assertEquals(expected.getUsername(), service.test(userId).getUsername());
    }
}