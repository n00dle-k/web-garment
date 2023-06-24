package com.garderob.web_garderob.config;

import com.garderob.web_garderob.controllers.GarmentController;
import com.garderob.web_garderob.controllers.LoginController;
import com.garderob.web_garderob.controllers.RegisterController;
import com.garderob.web_garderob.controllers.TagsController;
import com.garderob.web_garderob.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AccountService accountsService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SecurityConfig(AccountService personDetailsService, PasswordEncoder passwordEncoder) {
        this.accountsService = personDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(accountsService).passwordEncoder(passwordEncoder).and().build();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/register").anonymous()
                .requestMatchers("/login").anonymous()
                .requestMatchers("/garments/**").authenticated()
                .requestMatchers("/tags/**").authenticated()
                .requestMatchers("/events/**").authenticated()
                .requestMatchers("/doLogout/**").authenticated()
                .requestMatchers("/favorites/**").authenticated()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/static/**", "/css/**", "/image/**", "/js/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/garments", true)
                .loginProcessingUrl(SecurityConstants.LOGIN_PROCESSING_PATH)
                .failureUrl(SecurityConstants.LOGIN_PAGE_PATH_ERROR)
                .and()
                .logout()
                .logoutUrl(SecurityConstants.LOGOUT_PROCESSING_URL);

        return http.build();
    }
}
