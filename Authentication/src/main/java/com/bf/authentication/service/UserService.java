package com.bf.authentication.service;

import com.bf.authentication.dao.UserDao;
import com.bf.authentication.entity.User;
import com.bf.authentication.entity.UserRole;
import com.bf.authentication.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;
    private RemoteEmployeeService employeeService;

    @Value("${security.jwt.JWT_TOKEN}")
    private String JWT_TOKEN;


    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setEmployeeService(RemoteEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.findUserByUsername(identifier);

        if (!userOptional.isPresent()){
            userOptional = userDao.findUserByEmail(identifier);
            if(!userOptional.isPresent()) {
                throw new UsernameNotFoundException("Username or email does not exist");
            }
        }

        User user = userOptional.get(); // database user
        user.getRoles().isEmpty();
        System.out.println(user);
        String employeeId = employeeService.getEmployeeIdByUserId(user.getId(), JWT_TOKEN);

        return AuthUserDetail.builder() // spring security's userDetail
                .employeeId(employeeId)
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        for (UserRole role:  user.getRoles()){
            if(!role.getActiveFlag()) continue;
            userAuthorities.add(new SimpleGrantedAuthority(role.getRole().getRoleName()));
        }

        return userAuthorities;
    }

    @Transactional
    public User test(Long userId){
        Optional<User> user = userDao.findById(userId);
        return user.get();
    }
}
