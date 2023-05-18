package com.example.restfulapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //we have to create 3 types of bean

    // postgres, testing (H2 database, also inmemory database)
    // 1. user credentials
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user1 = User.builder().username("jessica").password("12345").roles("USER").build();
        UserDetails user2 = User.withUsername("jane").password("4321").roles("ADMIN").build();
        UserDetails user3 = User.withUsername("jasper").password("jasper").roles("ADMIN").build();
        return (new InMemoryUserDetailsManager(user1, user2, user3));
    }

    // 2. password encoder
    @Bean
    @SuppressWarnings("deprecation")
    public NoOpPasswordEncoder passwordEncoder(){
        return  (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    // 3. security filter-chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // write the code in order to configure the security
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authentication/**", "/api/v1/file/**", "/api/v1/mail/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
