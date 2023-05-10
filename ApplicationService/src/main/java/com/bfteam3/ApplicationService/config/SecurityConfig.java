package com.bfteam3.ApplicationService.config;

import com.bfteam3.ApplicationService.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/docu/test",
                    "/docu/download/*",
                    "/docu/upload",
                    "/docu/delete/*",
                    "/docu/update/*"
            ).hasAuthority("employee")
            .antMatchers("/docu/test",
                    "/docu/download/*",
                    "/docu/upload",
                    "/docu/delete/*",
                    "/docu/update/*",
                    "/review",
                    "/review/*",
                    "/review/rejected",
                    "/review/pending",
                    "/review/accepted").hasAuthority("HR")
            .anyRequest()
            .authenticated();
    }



}
