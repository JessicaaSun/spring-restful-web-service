package com.example.restfulapi.configuration;

import com.example.restfulapi.service.serviceImpl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailService;
    public SecurityConfiguration(UserDetailsService userDetailService){
        this.userDetailService = userDetailService;
    }

    // 1. Create bean of Authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // password encoder
        // userDetailService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    //we have to create 3 types of bean

    // postgres, testing (H2 database, also inmemory database)
    // 1. user credentials
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user1 = User.builder().username("jessica").password("12345").roles("USER").build();
//        UserDetails user2 = User.withUsername("jane").password("4321").roles("ADMIN").build();
//        UserDetails user3 = User.withUsername("jasper").password("jasper").roles("ADMIN").build();
//        return (new InMemoryUserDetailsManager(user1, user2, user3));
//    }

    // 2. password encoder

//    @SuppressWarnings("deprecation")
//    public NoOpPasswordEncoder passwordEncoder(){
//        return  (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 3. security filter-chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // write the code in order to configure the security
        httpSecurity.csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authentication/**", "/api/v1/file/**", "/api/v1/mail/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }

    // Allow user to login with the information stored in database

    // 1. override method loadByUsername from userDetailService
    // authenticationProvider (DaoAuthenticationProvider)

    // 2. Determine the authentication provider by our own
    //      1. @Bean of AuthenticationManger
    //      2. @Bean of AuthenticationProvider

}
