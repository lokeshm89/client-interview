package com.adp.coindispenser.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
 import org.springframework.boot.autoconfigure.security.servlet.PathRequest.H2ConsoleRequestMatcher;

/**
 * @author Lokesh Venktesan
 */
@EnableWebSecurity
@Configuration

public class SecurityConfiguration {

    private static final String[] AUTH_WHITELIST = {"/login", "/logout","/h2-console/**"};


    @Bean
    protected InMemoryUserDetailsManager configureInMemoryUsersForDevelopment() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users.username("user").password("user").roles("USER").authorities("GET_CHANGE").build();
        UserDetails admin = users.username("admin").password("admin").roles("ADMIN")
                .authorities("GET_CHANGE", "UPDATE_INVENTORY").build();
        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                .defaultSuccessUrl("/swagger-ui.html", true)
                .failureUrl("/login.html?error=true");
        return http.build();
    }


}