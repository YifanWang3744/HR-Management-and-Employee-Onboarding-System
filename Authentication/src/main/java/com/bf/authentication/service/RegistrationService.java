package com.bf.authentication.service;

import com.bf.authentication.dao.RegistrationTokenDao;
import com.bf.authentication.dao.RoleDao;
import com.bf.authentication.dao.UserDao;
import com.bf.authentication.domain.request.UserRequest;
import com.bf.authentication.entity.RegistrationToken;
import com.bf.authentication.entity.User;
import com.bf.authentication.entity.UserRole;
import com.bf.authentication.exception.NoAuthorizationException;
import com.bf.authentication.exception.UsernameOrEmailExistException;
import com.bf.authentication.security.AuthUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
    private UserDao userDao;
    private RoleDao roleDao;
    private RegistrationTokenDao registrationTokenDao;
    private Long tokenDuration = 3*60*60*1000L;

    @Value("${security.jwt.token.key}")
    private String key;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setRegistrationTokenDao(RegistrationTokenDao registrationTokenDao) {
        this.registrationTokenDao = registrationTokenDao;
    }

    public User register(UserRequest request, String token) throws UsernameOrEmailExistException, NoAuthorizationException {
        Optional<RegistrationToken> tokenOptional = registrationTokenDao.findByToken(token);

        if(!tokenOptional.isPresent()) throw new NoAuthorizationException();
        RegistrationToken registrationToken = tokenOptional.get();
        if(!registrationToken.getEmail().equals(request.getEmail())) throw new NoAuthorizationException();
        if(registrationToken.getExpirationDate().getTime() < System.currentTimeMillis()) throw new CredentialsExpiredException("Registration token has expired");

        if(userDao.findUserByUsername(request.getUsername()).isPresent()
                || userDao.findUserByEmail(request.getEmail()).isPresent()) throw new UsernameOrEmailExistException();

        Date now = new Date(System.currentTimeMillis());

        UserRole role = UserRole.builder()
                .role(roleDao.findById(1L).orElse(null))
                .activeFlag(true)
                .createDate(now)
                .lastModificationDate(now)
                .build();

        Set<UserRole> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .createDate(now)
                .lastModificationDate(now)
                .activeFlag(true)
                .roles(roles)
                .build();

        role.setUser(user);
        userDao.save(user);
        userDao.flush();
        return user;
    }

    public String generateToken(String email, String authorization) throws NoAuthorizationException {
        AuthUserDetail userDetail = resolveToken(authorization).orElseThrow(() -> new NoAuthorizationException());
        String username = userDetail.getUsername();
        User user = userDao.findUserByUsername(username).orElseThrow(()->new NoAuthorizationException());
        user.getRoles().stream().filter(r->r.getActiveFlag() && r.getRole().getRoleName().equals("HR")).findAny().orElseThrow(()->new NoAuthorizationException());
        String token = tokenGenerator();
        RegistrationToken registrationToken = RegistrationToken
                .builder()
                .createdBy(user)
                .email(email)
                .expirationDate(new Date(System.currentTimeMillis()+tokenDuration))
                .token(token)
                .build();
        registrationTokenDao.save(registrationToken);

        return token;
    }

    private String tokenGenerator(){
        return new BCryptPasswordEncoder().encode(Long.toString(System.currentTimeMillis()));
    }

    private Optional<AuthUserDetail> resolveToken(String authorization){
        String token = authorization.substring(7); // remove the prefix "Bearer "
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody(); // decode

        String username = claims.getSubject();
        List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");

        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                .collect(Collectors.toList());

        return Optional.of(AuthUserDetail.builder()
                .username(username)
                .authorities(authorities)
                .build());

    }
}
